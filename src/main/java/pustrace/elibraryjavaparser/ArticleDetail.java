package pustrace.elibraryjavaparser;

public class ArticleDetail {
    private String title;
    private String publication;
    private String place;

    public ArticleDetail(String title, String publication, String place) {
        this.title = title;
        this.publication = publication;
        this.place = place;
    }

    public String getTitle() {
        return title;
    }

    public String getPublication() {
        return publication;
    }

    public String getPlace() {return place;}
}

