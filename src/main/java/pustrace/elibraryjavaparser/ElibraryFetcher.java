package pustrace.elibraryjavaparser;

import com.google.gson.Gson;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

// логгер
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ElibraryFetcher {
    private static final Logger logger = LogManager.getLogger(ElibraryFetcher.class);
    private Map<String, String> cookieMap = new HashMap<>();
    private Set<String> processedTitles = new HashSet<>();

    public void init() {
        logger.info("Инициализация WebDriver...");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://elibrary.ru/defaultx.asp");
            logger.debug("Открыт сайт eLibrary");
            Thread.sleep(50000); // Увеличено для капчи
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            Set<Cookie> cookies = driver.manage().getCookies();
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie.getValue());
            }

            logger.info("Куки успешно получены: {}", cookieMap);
        } catch (Exception e) {
            logger.error("Ошибка при инициализации WebDriver", e);
        } finally {
            driver.quit();
        }
    }

    public AuthorStatistics fetchAuthorStatistics(String authorId) {
        AuthorStatistics statistics = new AuthorStatistics();
        List<Integer> citationsList = new ArrayList<>();
        int pageNum = 1;
        boolean hasMorePages = true;

        while (hasMorePages) {
            String url = "https://elibrary.ru/author_items.asp?authorid=" + authorId + "&pubrole=100&show_refs=1&show_option=0&pagenum=" + pageNum;

            try {
                Response response = RestAssured.given()
                        .cookies(cookieMap)
                        .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                        .get(url);

                Document doc = Jsoup.parse(response.getBody().asString());

                if (statistics.getAuthorName() == null) {
                    Element authorNameElement = doc.selectFirst("#thepage > table > tbody > tr > td > table:nth-child(1) > tbody > tr > td:nth-child(2) > form > table > tbody > tr:nth-child(3) > td:nth-child(1) > table > tbody > tr > td > div:nth-child(1) > font > b");
                    if (authorNameElement != null) {
                        statistics.setAuthorName(authorNameElement.text());
                    }
                }

                try {
                    processPage(doc, statistics, citationsList);
                } catch (Exception e) {
                    logger.error("Ошибка обработки данных на странице {}: {}", pageNum, e.getMessage());
                }

                hasMorePages = doc.select("a[title=Следующая страница]").size() > 0;
                pageNum++;
                //Thread.sleep(5000); // Увеличение задержки между страницами

            } catch (Exception e) {
                logger.error("Ошибка обработки страницы {}: {}", pageNum, e.getMessage());
                break;
            }
        }

        calculateHIndex(statistics, citationsList);
        return statistics;
    }

    private void processPage(Document doc, AuthorStatistics statistics, List<Integer> citationsList) {
        Element table = doc.selectFirst("table#restab");

        if (table != null) {
            Elements rows = table.select("tr");
            for (Element row : rows) {
                Element titleCell = row.selectFirst("td:nth-child(2) span a b span");
                Element publicationCell = row.selectFirst("td:nth-child(2) font i");
                Element citationCell = row.selectFirst("td.select-tr-right");
                Element placeCell = row.selectFirst("td:nth-child(2) span font:nth-child(5)");

                if (titleCell == null || publicationCell == null || citationCell == null || placeCell == null) continue;

                String title = titleCell.text();
                if (processedTitles.contains(title)) continue; // Пропуск дубликатов

                processedTitles.add(title);
                statistics.incrementTotalArticles();

                String publication = publicationCell.text();
                String place = placeCell.text();
                int citations;

                try {
                    citations = Integer.parseInt(citationCell.text().trim());
                } catch (NumberFormatException e) {
                    citations = 0;
                }

                citationsList.add(citations);

                if (citations == 0) {
                    statistics.incrementZeroCitationArticles();
                    statistics.addZeroCitationDetails(title, publication, place);
                }
            }
        }
    }

    private void calculateHIndex(AuthorStatistics statistics, List<Integer> citationsList) {
        citationsList.sort(Collections.reverseOrder());
        int hIndex = 0;
        for (int i = 0; i < citationsList.size(); i++) {
            if (citationsList.get(i) >= i + 1) {
                hIndex = i + 1;
            } else {
                break;
            }
        }
        statistics.setHIndex(hIndex);
    }
}
