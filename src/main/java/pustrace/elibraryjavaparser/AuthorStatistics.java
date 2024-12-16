package pustrace.elibraryjavaparser;

import java.util.ArrayList;
import java.util.List;
/**
 * Класс для хранения статистики по автору.
 * Содержит информацию о количестве статей, H-индексе и статьях без цитирований.
 */
public class AuthorStatistics {
    private String authorName;
    private int totalArticles;
    private int zeroCitationArticles;
    private int hIndex;
    private List<ArticleDetail> zeroCitationDetails;
    /**
     * Конструктор по умолчанию для инициализации статистики.
     */
    public AuthorStatistics() {
        this.totalArticles = 0;
        this.zeroCitationArticles = 0;
        this.hIndex = 0;
        this.zeroCitationDetails = new ArrayList<>();
    }
    /**
     * Возвращает имя автора.
     *
     * @return Имя автора.
     */
    public String getAuthorName() {
        return authorName;
    }
    /**
     * Устанавливает имя автора.
     *
     * @param authorName Имя автора.
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    /**
     * Возвращает общее количество статей автора.
     *
     * @return Общее количество статей.
     */
    public int getTotalArticles() {
        return totalArticles;
    }
    /**
     * Увеличивает общее количество статей на 1.
     */
    public void incrementTotalArticles() {
        this.totalArticles++;
    }
    /**
     * Возвращает количество статей без цитирований.
     *
     * @return Количество статей без цитирований.
     */
    public int getZeroCitationArticles() {
        return zeroCitationArticles;
    }
    /**
     * Увеличивает количество статей без цитирований на 1.
     */
    public void incrementZeroCitationArticles() {
        this.zeroCitationArticles++;
    }
    /**
     * Устанавливает значение H-индекса.
     *
     * @param hIndex Значение H-индекса.
     */
    public void setHIndex(int hIndex) {
        this.hIndex = hIndex;
    }
    /**
     * Возвращает значение H-индекса.
     *
     * @return Значение H-индекса.
     */
    public int getHIndex() {
        return hIndex;
    }
    /**
     * Увеличивает значение H-индекса на 1.
     */
    public void incrementHIndex() {
        this.hIndex++;
    }
    /**
     * Возвращает список статей без цитирований.
     *
     * @return Список подробностей статей без цитирований.
     */
    public List<ArticleDetail> getZeroCitationDetails() {
        return zeroCitationDetails;
    }
    /**
     * Добавляет статью без цитирования в список.
     *
     * @param title Название статьи.
     * @param publication Название публикации.
     * @param place Место публикации.
     */
    public void addZeroCitationDetails(String title, String publication, String place) {
        this.zeroCitationDetails.add(new ArticleDetail(title, publication, place));
    }
}

