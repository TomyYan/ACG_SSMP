package cn.tomy.service;

import cn.tomy.domain.*;

import java.util.List;

/**
 * Created by tomy on 18-1-28.
 */
public interface ServiceInter {
    //登录
    User login(String account,String password);
    //注册
    int register(String account,String password,String userName,String email);
    //获取个人信息
    User getUserInfo(int userId);
    //修改个人信息
    int setUserInfo(int userId,String userName,String email,String accountSign,String accountSex,String accountImg);
    //修改密码
    int updatePassword(int userId,String oldPassword,String newPassword);
    //发布文章修改文章数据表
    //int publishArticleForArticleTable(String articleLabel,String article);
    int publishArticleForArticleTable(Article article,int userId);
    //通过文章标签找到文章ID
    //int findArticleId(String typeTable,String articleLabel);
    //初始化点赞点踩数记录表
    //int initializeThumbsTable(int articleId);
    //更新用户-文章对应关联表
    //int refreshUserArticleTable(int articleId,int userId);
    //发布评论
    int publishComment(Comment comment, int articleId, int userId);
    //点赞
    int pointThumbsUp(int articleId,int userId);
    //点踩
    int pointThumbsDown(int articleId,int userId);
    //忘记密码处理
    int forgetPassword(String account,String email);
    //获取文章
    List<ArticleResponse> getArticle(int type,int start);
    //获取个人文章
    List<ArticleResponse> getUserArticle(int userId,int start);
    //获取评论
    List<CommentResponse> getComment(int articleId);
    //删除文章
    int deleteArticle(int articleId);
}
