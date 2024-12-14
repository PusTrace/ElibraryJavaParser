package pustrace.elibraryjavaparser;

import java.util.ArrayList;
import java.util.List;

public class AuthorStatistics {
    private String authorName;
    private int totalArticles;
    private int zeroCitationArticles;
    private int hIndex;
    private List<ArticleDetail> zeroCitationDetails;

    public AuthorStatistics() {
        this.totalArticles = 0;
        this.zeroCitationArticles = 0;
        this.hIndex = 0;
        this.zeroCitationDetails = new ArrayList<>();
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public int getTotalArticles() {
        return totalArticles;
    }

    public void incrementTotalArticles() {
        this.totalArticles++;
    }

    public int getZeroCitationArticles() {
        return zeroCitationArticles;
    }

    public void incrementZeroCitationArticles() {
        this.zeroCitationArticles++;
    }

    public void setHIndex(int hIndex) {
        this.hIndex = hIndex;
    }

    public int getHIndex() {
        return hIndex;
    }

    public void incrementHIndex() {
        this.hIndex++;
    }

    public List<ArticleDetail> getZeroCitationDetails() {
        return zeroCitationDetails;
    }

    public void addZeroCitationDetails(String title, String publication, String place) {
        this.zeroCitationDetails.add(new ArticleDetail(title, publication, place));
    }
}

