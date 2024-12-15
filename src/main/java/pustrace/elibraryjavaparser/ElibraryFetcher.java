package pustrace.elibraryjavaparser;

import io.restassured.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ElibraryFetcher {
    private CookieFetcher cookieFetcher;
    private DataFetcher dataFetcher;
    private PageParser pageParser;

    private Map<String, String> cookieMap;
    private int captchaDelay;
    private int pageDelay;

    // Обновленный конструктор, принимающий два параметра
    public ElibraryFetcher(int captchaDelay, int pageDelay) {
        this.captchaDelay = captchaDelay;
        this.pageDelay = pageDelay;
        cookieFetcher = new CookieFetcher(captchaDelay);
        dataFetcher = new DataFetcher();
        pageParser = new PageParser();
    }

    public void init() {
        this.cookieMap = cookieFetcher.fetchCookies();
    }

    public AuthorStatistics fetchAuthorStatistics(String authorId) {
        AuthorStatistics statistics = new AuthorStatistics();
        List<Integer> citationsList = new ArrayList<>();
        int pageNum = 1;
        boolean hasMorePages = true;

        while (hasMorePages) {
            String url = "https://elibrary.ru/author_items.asp?authorid=" + authorId + "&pubrole=100&show_refs=1&show_option=0&pagenum=" + pageNum;

            try {
                Response response = dataFetcher.fetchPageData(url, cookieMap);
                Document doc = Jsoup.parse(response.getBody().asString());

                if (statistics.getAuthorName() == null) {
                    String authorName = pageParser.getAuthorName(doc);
                    if (authorName != null) {
                        statistics.setAuthorName(authorName);
                    }
                }

                pageParser.processPage(doc, statistics, citationsList);

                hasMorePages = doc.select("a[title=Следующая страница]").size() > 0;
                pageNum++;
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

        calculateHIndex(statistics, citationsList);
        return statistics;
    }

    private void calculateHIndex(AuthorStatistics statistics, List<Integer> citationsList) {
        citationsList.sort(Collections.reverseOrder());
        int hIndex = (int) IntStream.range(0, citationsList.size())
                .filter(i -> citationsList.get(i) >= i + 1)
                .count();
        statistics.setHIndex(hIndex);
    }
}

