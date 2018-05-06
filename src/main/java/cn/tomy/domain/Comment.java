package cn.tomy.domain;

/**
 * Created by tomy on 18-4-13.
 */
public class Comment {
    public int getCommentId() {
        return CommentId;
    }

    public void setCommentId(int commentId) {
        CommentId = commentId;
    }

    public String getArticleComment() {
        return articleComment;
    }

    public void setArticleComment(String articleComment) {
        this.articleComment = articleComment;
    }

    private int CommentId;
    private String articleComment;

}
