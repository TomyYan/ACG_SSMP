package cn.tomy.domain;

/**
 * Created by tomy on 18-4-10.
 */
public class Article {
    private int articleId;
    private String article;
    private int type;
    private String articleImgAddress;

    public String getArticleImgAddress() {
        return articleImgAddress;
    }

    public void setArticleImgAddress(String articleImgAddress) {
        this.articleImgAddress = articleImgAddress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }
}
