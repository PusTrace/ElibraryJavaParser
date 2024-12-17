package pustrace.elibraryjavaparser;
/**
 * Класс, представляющий информацию о статье.
 * Хранит название статьи, публикацию и место издания.
 */
public class ArticleDetail {
    private String title;
    private String author;
    private String place;
    /**
     * Конструктор для инициализации всех полей класса.
     *
     * @param title Название статьи.
     * @param author Название публикации или издания.
     * @param place Место публикации.
     */
    public ArticleDetail(String title, String author, String place) {
        this.title = title;
        this.author = author;
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
     * Возвращает автора публикации.
     *
     * @return автора публикации.
     */
    public String getauthor() {
        return author;
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
     * Устанавливает авторов публикации.
     *
     * @param author автор публикации.
     */
    public void setauthor(String author) {
        this.author = author;
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

