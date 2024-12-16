package pustrace.elibraryjavaparser;
/**
 * Класс, представляющий информацию о статье.
 * Хранит название статьи, публикацию и место издания.
 */
public class ArticleDetail {
    private String title;
    private String publication;
    private String place;
    /**
     * Конструктор для инициализации всех полей класса.
     *
     * @param title Название статьи.
     * @param publication Название публикации или издания.
     * @param place Место публикации.
     */
    public ArticleDetail(String title, String publication, String place) {
        this.title = title;
        this.publication = publication;
        this.place = place;
    }

    /**
     * Возвращает название статьи.
     *
     * @return Название статьи.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Возвращает название публикации.
     *
     * @return Название публикации.
     */
    public String getPublication() {
        return publication;
    }
    /**
     * Возвращает место публикации.
     *
     * @return Место публикации.
     */
    public String getPlace() {
        return place;
    }

    /**
     * Устанавливает название статьи.
     *
     * @param title Название статьи.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * Устанавливает название публикации.
     *
     * @param publication Название публикации.
     */
    public void setPublication(String publication) {
        this.publication = publication;
    }
    /**
     * Устанавливает место публикации.
     *
     * @param place Место публикации.
     */
    public void setPlace(String place) {
        this.place = place;
    }
}

