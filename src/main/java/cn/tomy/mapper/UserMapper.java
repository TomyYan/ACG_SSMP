package cn.tomy.mapper;

import cn.tomy.domain.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by tomy on 18-1-28.
 */
@Service
public interface UserMapper {
    @Select("select * from user_table where account=#{account} and password=#{password}")
    User loginIn(@Param("account") String account,
                 @Param("password") String password);

    @Insert("insert into user_table(account,password,userName,email) values (#{account},#{password},#{userName},#{email})")
    int register(@Param("account") String account,
                 @Param("password") String password,
                 @Param("userName") String userName,
                 @Param("email") String email);

    @Select("select * from user_table left join admin on user_table.account=admin.adminAccount where userId=#{userId}")
    User getUserInfo(@Param("userId")int userId);

    @Update("update user_table set userName=#{userName},email=#{email},accountSign=#{accountSign},accountSex=#{accountSex},accountImg=#{accountImg} where userId=#{userId}")
    int setUserInfo(@Param("userId") int userId,
                    @Param("userName") String userName,
                    @Param("email") String email,
                    @Param("accountSign") String accountSign,
                    @Param("accountSex") String accountSex,
                    @Param("accountImg") String accountImg);

    @Update("update user_table set password=#{newPassword} where userId=#{userId} and password=#{oldPassword}")
    int updatePassword(@Param("userId")int userId,
                       @Param("oldPassword")String oldPassword,
                       @Param("newPassword")String newPassword);

    @Insert("insert into Article (type,article) values (#{type},#{article})")
    @Options(useGeneratedKeys = true,keyProperty ="articleId",keyColumn = "articleId")
    int publishArticleForArticleTable(Article article);//更新文章表

    //@Insert("insert into aArticle (article) values (#{article})")
    /*
    @InsertProvider(type = publishArticle.class,method = "publishArticleForArticleTable")
    @Options(useGeneratedKeys = true,keyProperty ="articleId",keyColumn = "articleId")
    int publishArticleForArticleTable(Article article);
    class publishArticle{
        public String publishArticleForArticleTable(Article article){
            String sql="Insert into ";
            if(article.getType()==1){
                sql+="aArticle";
            }
            else if(article.getType()==2){
                sql+="cArticle";
            }
            else if(article.getType()==3){
                sql+="gArticle";
            }
            sql+="(article) values (#{article})";
            return sql;
        }
    }
    */


    @Insert("insert into thumbsUpOrDown (articleId,thumbsUpNum,thumbsDownNum) values (#{articleId},0,0)")
    int initializeThumbsTable(@Param("articleId") int articleId);//初始化点赞点踩表

    @Insert("insert into articleImg (articleId,articleImgAddress) values (#{articleId},#{articleImgAddress})")
    int initializeArticleImg(@Param("articleId") int articleId,
                             @Param("articleImgAddress") String articleImgAddress);

    @Insert("insert into userArticle(articleId,userId) values (#{articleId},#{userId})")
    int refreshUserArticleTable(@Param("articleId") int articleId,
                                @Param("userId") int userId);//更新用户文章表

    @Insert("insert into articleComment(articleComment) values (#{articleComment})")
    @Options(useGeneratedKeys = true,keyProperty = "commentId", keyColumn = "commentId")
    int refreshComment(Comment comment);//更新评论表

    @Insert("insert into articleAndComment (articleId,commentId) values (#{articleId},#{commentId})")
    int refreshArticleAndComment(@Param("articleId") int articleId,
                                 @Param("commentId") int commentId);//更新文章评论表

    @Insert("insert into userComment (commentId,userId) values (#{commentId},#{userId})")
    int refreshUserComment(@Param("commentId") int commentId,
                           @Param("userId") int userId);//更新用户评论表

    @Update("update thumbsUpOrDown set thumbsUpNum=thumbsUpNum+1 where articleId=#{articleId}")
    int refreshThumbsUpOrDownUp(@Param("articleId") int articleId);//更新点赞点踩数量记录表点赞数量

    @Select("select * from thumbsUp where articleId=#{articleId} and userId=#{userId}")
    ThumbsUp findIfThumbsUp(@Param("articleId")int articleId,
                            @Param("userId")int userId);

    @Insert("Insert into thumbsUp(articleId,userId) values (#{articleId},#{userId})")
    int refreshThumbsUp(@Param("articleId") int articleId,
                        @Param("userId") int userId);//更新点赞表

    @Update("update thumbsUpOrDown set thumbsDownNum=thumbsDownNum+1 where articleId=#{articleId}")
    int refreshThumbsUpOrDownDown(@Param("articleId") int articleId);//更新点赞点踩数量记录表点踩数量

    @Select("select * from thumbsDown where articleId=#{articleId} and userId=#{userId}")
    ThumbsDown findIfThumbsDown(@Param("articleId")int articleId,
                                @Param("userId")int userId);

    @Insert("Insert into thumbsDown(articleId,userId) values (#{articleId},#{userId})")
    int refreshThumbsDown(@Param("articleId") int articleId,
                        @Param("userId") int userId);//更新点踩表

    @Select("select * from user_table where account=#{account}")
    User ForgetPassword(@Param("account") String account);

    @Select("select * from user_table left join (select Article.articleId,type,article,userId,thumbsUpNum,thumbsDownNum,articleImgAddress from Article left join userArticle on Article.articleId=userArticle.articleId left join thumbsUpOrDown on Article.articleId=thumbsUpOrDown.articleId left join articleImg on Article.articleId=articleImg.articleId) as articleResponse on articleResponse.userId=user_table.userId where articleResponse.type=#{type} order by articleResponse.articleId desc limit #{start},10")
    List<ArticleResponse> getArticle(@Param("type") int type,
                                     @Param("start") int start);

    @Select("select * from user_table left join (select Article.articleId,type,article,userId,thumbsUpNum,thumbsDownNum,articleImgAddress from Article left join userArticle on Article.articleId=userArticle.articleId left join thumbsUpOrDown on Article.articleId=thumbsUpOrDown.articleId left join articleImg on Article.articleId=articleImg.articleId) as articleResponse on articleResponse.userId=user_table.userId where articleResponse.userId=#{userId} order by articleResponse.articleId desc limit #{start},10")
    List<ArticleResponse> getUserArticle(@Param("userId") int userId,
                                     @Param("start") int start);

    @Select("select * from user_table left join (select userId,articleComment,articleId from articleAndComment left join articleComment on articleAndComment.commentId=articleComment.commentId left join userComment on articleAndComment.commentId=userComment.commentId) as CommentResponse on CommentResponse.userId=user_table.userId where articleId=#{articleId}")
    List<CommentResponse> getComment(@Param("articleId") int articleId);

    @Delete("delete from Article where articleId=#{articleId}")
    int deleteArticle(@Param("articleId") int articleId);
}
