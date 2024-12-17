package pustrace.elibraryjavaparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;
/**
 * Класс для обработки и парсинга страницы с данными об авторе.
 */
public class PageParser {
    /**
     * Обрабатывает HTML-страницу, извлекает данные об статьях автора и обновляет статистику.
     *
     * @param doc          Документ, представляющий HTML-страницу.
     * @param statistics  Объект статистики, который будет обновляться на основе данных страницы.
     * @param citationsList Список, в который добавляются количество цитирований каждой статьи.
     */
    public static void processPage(Document doc, AuthorStatistics statistics, List<Integer> citationsList) {
        Element table = doc.selectFirst("table#restab");

        if (table != null) {
            Elements rows = table.select("tr");
            rows.stream()
                    .map(row -> {
                        Element titleCell = row.selectFirst("td:nth-child(2) span a b span");
                        Element authorscell = row.selectFirst("td:nth-child(2) font i");
                        Element citationCell = row.selectFirst("td.select-tr-right");
                        Element placeCell = row.selectFirst("td:nth-child(2) span font:nth-child(5)");

                        if (titleCell == null || authorscell == null || citationCell == null || placeCell == null) return null;

                        String title = titleCell.text();
                        statistics.incrementTotalArticles();

                        String author = authorscell.text();
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
                            statistics.addZeroCitationDetails(title, author, place);
                        }

                        return title;
                    })
                    .filter(title -> title != null)  // Фильтрация null значений
                    .collect(Collectors.toList());
        }
    }
    /**
     * Извлекает имя автора из HTML-документа.
     *
     * @param doc Документ, представляющий HTML-страницу.
     * @return Имя автора, если оно найдено, или null, если имя не найдено.
     */
    public static String getAuthorName(Document doc) {
        Element authorNameElement = doc.selectFirst("#thepage > table > tbody > tr > td > table:nth-child(1) > tbody > tr > td:nth-child(2) > form > table > tbody > tr:nth-child(3) > td:nth-child(1) > table > tbody > tr > td > div:nth-child(1) > font > b");
        if (authorNameElement != null) {
            return authorNameElement.text();
        }
        return null;
    }
}
