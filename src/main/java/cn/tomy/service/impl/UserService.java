package cn.tomy.service.impl;

import cn.tomy.domain.*;
import cn.tomy.mapper.UserMapper;
import cn.tomy.service.ServiceInter;
import cn.tomy.tool.SensitivityDiscern;
import cn.tomy.tool.mail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static cn.tomy.tool.SensitivityDiscern.getSensitivitySet;

/**
 * Created by tomy on 18-1-28.
 */

@Transactional(propagation= Propagation.REQUIRED,isolation= Isolation.DEFAULT)
@Service("userService")
public class UserService implements ServiceInter{

    @Autowired//自动注入UserMapper
    private UserMapper userMapper;

    @Override
    public User login(String account, String password) {
        //System.out.print(userMapper);
        return userMapper.loginIn(account,password);
    }

    @Override
    public int register(String account, String password,String userName,String email) {
        //System.out.print(userMapper);
        return userMapper.register(account,password,userName,email);
    }

    @Override
    public User getUserInfo(int userId) {
        return userMapper.getUserInfo(userId);
    }

    @Override
    public int setUserInfo(int userId,String userName, String email, String accountSign, String accountSex, String accountImg) {
        return userMapper.setUserInfo(userId,userName,email,accountSign,accountSex,accountImg);
    }

    @Override
    public int updatePassword(int userId, String oldPassword, String newPassword) {
        return userMapper.updatePassword(userId,oldPassword,newPassword);
    }

    @Override
    @Transactional
    public int publishArticleForArticleTable(Article article,int userId) {
        //初始化敏感词数组
        if(SensitivityDiscern.sensitiveWordMap==null){
            SensitivityDiscern.init(getSensitivitySet("sensitivity.txt"));
        }
        //判断是否含有敏感词
        if(SensitivityDiscern.contains(article.getArticle())){
            return 0;
        }
        int articleId;
        userMapper.publishArticleForArticleTable(article);
        articleId=article.getArticleId();
        userMapper.initializeThumbsTable(articleId);
        userMapper.initializeArticleImg(articleId,article.getArticleImgAddress());
        userMapper.refreshUserArticleTable(articleId,userId);
        return 1;
    }

    @Override
    @Transactional
    public int publishComment(Comment comment, int articleId, int userId) {
        //初始化敏感词数组
        if(SensitivityDiscern.sensitiveWordMap==null){
            SensitivityDiscern.init(getSensitivitySet("sensitivity.txt"));
        }
        //判断是否含有敏感词
        if(SensitivityDiscern.contains(comment.getArticleComment())){
            return 0;
        }
        int commentId;
        userMapper.refreshComment(comment);
        commentId=comment.getCommentId();
        userMapper.refreshArticleAndComment(articleId,commentId);
        userMapper.refreshUserComment(commentId,userId);
        return 1;
    }

    @Override
    @Transactional
    public int pointThumbsUp(int articleId, int userId) {
        ThumbsUp thumbsUp=userMapper.findIfThumbsUp(articleId,userId);
        if(thumbsUp!=null){
            return 0;
        }
        userMapper.refreshThumbsUp(articleId,userId);
        userMapper.refreshThumbsUpOrDownUp(articleId);
        return 1;
    }

    @Override
    @Transactional
    public int pointThumbsDown(int articleId, int userId) {
        ThumbsDown thumbsDown=userMapper.findIfThumbsDown(articleId,userId);
        if(thumbsDown!=null){
            return 0;
        }
        userMapper.refreshThumbsDown(articleId,userId);
        userMapper.refreshThumbsUpOrDownDown(articleId);
        return 1;
    }

    @Override
    public int forgetPassword(String account, String email) {
        //邮箱对比，发送邮件，进行处理
        User user=userMapper.ForgetPassword(account);
        if(email.equals(user.getEmail())){
            //发送邮件
            try {
                System.out.println(user.getEmail()+user.getPassword());
                Thread thread=new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mail.sendEmail(user.getEmail(),user.getPassword());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();

            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }
            return 1;
        }
        else
            return 0;
    }

    @Override
    public List<ArticleResponse> getArticle(int type, int start) {
        return userMapper.getArticle(type,start);
    }

    @Override
    public List<ArticleResponse> getUserArticle(int userId, int start) {
        return userMapper.getUserArticle(userId,start);
    }

    @Override
    public List<CommentResponse> getComment(int articleId) {
        return userMapper.getComment(articleId);
    }

    @Override
    public int deleteArticle(int articleId) {
        return userMapper.deleteArticle(articleId);
    }


}
