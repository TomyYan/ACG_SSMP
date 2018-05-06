package cn.tomy.controller;

import cn.tomy.domain.*;
import cn.tomy.service.ServiceInter;
import cn.tomy.service.impl.UserService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by tomy on 18-1-29.
 */

@Controller
public class UserController {
    @Autowired
    @Qualifier("userService")
    private ServiceInter userService;
    /*
    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object login(
            @RequestParam("userAccount") String userAccount,
            @RequestParam("userPassword") String userPassword) {
        System.out.println("userAccount = [" + userAccount + "], userPassword = [" + userPassword + "]");
        User user = userService.login(userAccount, userPassword);
        //System.out.println(user);
        System.out.println("user = " + user);
        if (user != null) {
            return new JsonResponse<Integer>("1", "login success", 200);
        } else {
            return new JsonResponse<Integer>("404", "oldPassword is not valid", 404);
        }
    }
    */
    @RequestMapping(value="/login", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public String Login(HttpServletRequest request) {
        String requestMsgStr=request.getHeader("requestMsg");
        try {
            requestMsgStr=new String(requestMsgStr.getBytes("iso8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(requestMsgStr);
        JSONObject requestMsg=JSONObject.parseObject(requestMsgStr);
        String userAccount=requestMsg.getString("account");
        String userPassword=requestMsg.getString("keyword");
        User user = userService.login(userAccount, userPassword);
        JSONObject response=new JSONObject();
        if(user!=null){
            //System.out.println("输出账号"+user.getAccount());
            response.put("result","ok");
            response.put("userId",user.getUserId());
        }
        else{
            response.put("result","fail");
            response.put("userId",0);
        }
        System.out.println(response.toString());
        return response.toString();

    }

    @RequestMapping(value="/register", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object register(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为：" + requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        String account=requestMsg.getString("account");
        String password=requestMsg.getString("password");
        String userName=requestMsg.getString("userName");
        String email=requestMsg.getString("email");
        try{
            userService.register(account,password,userName,email);
        }catch (Exception e){
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        return new JsonResponse<String>("1","success","ok");
    }

    @RequestMapping(value="/getUserInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object getUserInfo(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为：" + requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        String userId=requestMsg.getString("userId");
        return userService.getUserInfo(Integer.parseInt(userId));
    }

    @RequestMapping(value="/setUserInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object setUserInfo(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为：" + requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        int userId=Integer.parseInt(requestMsg.getString("userId"));
        String userName=requestMsg.getString("userName");
        String email=requestMsg.getString("email");
        String accountSex=requestMsg.getString("accountSex");
        String accountSign=requestMsg.getString("accountSign");
        String accountImg=requestMsg.getString("accountImg");
        try{
            userService.setUserInfo(userId,userName,email,accountSign,accountSex,accountImg);
        }catch (Exception e){
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        return new JsonResponse<String>("1","success","ok");
    }

    @RequestMapping(value="/deleteArticle", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object deleteArticle(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为：" + requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        int articleId=Integer.parseInt(requestMsg.getString("articleId"));
        try{
            userService.deleteArticle(articleId);
        }catch (Exception e){
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        return new JsonResponse<String>("1","success","ok");
    }

    @RequestMapping(value="/updatePassword", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object updatePassword(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为：" + requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        int userId = Integer.parseInt(requestMsg.getString("userId"));
        String oldPassword=requestMsg.getString("oldPassword");
        String newPassword=requestMsg.getString("newPassword");
        try{
            userService.updatePassword(userId,oldPassword,newPassword);
        }catch (Exception e){
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        return new JsonResponse<String>("1","success","ok");
    }

    @RequestMapping(value="/forgetPassword", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object forgetPassword(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为：" + requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        String account=requestMsg.getString("account");
        String email=requestMsg.getString("email");
        int result=0;
        try{
            result=userService.forgetPassword(account,email);
        }catch (Exception e){
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        if(result==1){
            return new JsonResponse<String>("1","success","ok");
        }
        else {
            return new JsonResponse<String>("404","fail","fail");
        }
    }

    @RequestMapping(value="/publishArticle", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object publishArticle(HttpServletRequest request){
        String requestMsgStr=request.getHeader("requestMsg");
        try {
            requestMsgStr=new String(requestMsgStr.getBytes("iso8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为："+requestMsgStr);
        JSONObject requestMsg=JSONObject.parseObject(requestMsgStr);
        String type=requestMsg.getString("type");
        String userId=requestMsg.getString("userId");
        String article=requestMsg.getString("article");
        System.out.println(article);
        Article aArticle=new Article();
        aArticle.setType(Integer.parseInt(type));
        aArticle.setArticle(article);
        int i=-1;
        /*
        switch (type){
            case "1":
                userService.publishArticleForArticleTable(aArticle);//写入文章表
                i=aArticle.getArticleId();//获取文章Id
                //System.out.println("记录为:"+i);
                userService.initializeThumbsTable(i);//初始化点赞点踩表
                userService.refreshUserArticleTable(i,Integer.parseInt(userId));//录入用户-文章关联表
                break;
            case "2":
                //i=userService.publishArticleForArticleTable("1234",article);
                break;
            case "3":
                //i=userService.publishArticleForArticleTable("1234",article);
                break;
            default:
                break;
        }
        */
        try {
            i=userService.publishArticleForArticleTable(aArticle, Integer.parseInt(userId));//写入文章表
        }catch (Exception e){
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        /*
        i=aArticle.getArticleId();//获取文章Id
        userService.initializeThumbsTable(i);//初始化点赞点踩表
        userService.refreshUserArticleTable(i,Integer.parseInt(userId));
        System.out.println("记录为:"+i);
        */
        if(i==1){
            return new JsonResponse<String>("1","success","ok");
        }else if(i==0){
            return new JsonResponse<String>("1","publicArticleFail","hasSensitivity");
        }else {
            return new JsonResponse<String>("404","fail","fail");
        }
    }

    @RequestMapping(value="/publishComment", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object publishComment(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为：" + requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        String articleComment=requestMsg.getString("articleComment");
        String articleId=requestMsg.getString("articleId");
        String userId=requestMsg.getString("userId");
        Comment comment=new Comment();
        comment.setArticleComment(articleComment);
        int i=-1;
        try{
            i=userService.publishComment(comment,Integer.parseInt(articleId),Integer.parseInt(userId));
        }catch (Exception e){
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        if(i==1){
            return new JsonResponse<String>("1","success","ok");
        }else if(i==0){
            //含敏感词
            return new JsonResponse<String>("1","commentFail","hasSensitivity");
        }else {
            return new JsonResponse<String>("404","fail","fail");
        }
    }

    @RequestMapping(value="/pointThumbsUp", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object pointThumbsUp(HttpServletRequest request){
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为：" + requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        String articleId=requestMsg.getString("articleId");
        String userId=requestMsg.getString("userId");
        int result=-1;
        try{
            result=userService.pointThumbsUp(Integer.parseInt(articleId),Integer.parseInt(userId));
        }catch (Exception e){
            System.out.println("错误"+e.toString());
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        if(result==1){
            return new JsonResponse<String>("1","success","ok");
        }else if(result==0){
            //已点赞
            return new JsonResponse<String>("1","pointUpFail","hadPoint");
        }else {
            return new JsonResponse<String>("404","fail","fail");
        }
    }

    @RequestMapping(value="/pointThumbsDown", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object pointThumbsDown(HttpServletRequest request){
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("请求为：" + requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        String articleId=requestMsg.getString("articleId");
        String userId=requestMsg.getString("userId");
        int result=-1;
        try{
            result=userService.pointThumbsDown(Integer.parseInt(articleId),Integer.parseInt(userId));
        }catch (Exception e){
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        if(result==1){
            return new JsonResponse<String>("1","success","ok");
        }else if(result==0){
            //已点踩
            return new JsonResponse<String>("1","pointDownFail","hadPoint");
        }else {
            return new JsonResponse<String>("404","fail","fail");
        }
    }

    @RequestMapping(value="/getArticle", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object getArticle(HttpServletRequest request){
        String requestMsgStr=request.getHeader("requestMsg");
        try {
            requestMsgStr=new String(requestMsgStr.getBytes("iso8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(requestMsgStr);
        JSONObject requestMsg=JSONObject.parseObject(requestMsgStr);
        int type=Integer.parseInt(requestMsg.getString("type"));
        int start=Integer.parseInt(requestMsg.getString("start"));
        return new JsonResponse("1", "success", userService.getArticle(type, start));
    }

    @RequestMapping(value="/getUserArticle", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object getUserArticle(HttpServletRequest request){
        String requestMsgStr=request.getHeader("requestMsg");
        try {
            requestMsgStr=new String(requestMsgStr.getBytes("iso8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(requestMsgStr);
        JSONObject requestMsg=JSONObject.parseObject(requestMsgStr);
        int userId=Integer.parseInt(requestMsg.getString("userId"));
        int start=Integer.parseInt(requestMsg.getString("start"));
        return new JsonResponse("1", "success", userService.getUserArticle(userId, start));
    }

    @RequestMapping(value="/getComment", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object getComment(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        int articleId=Integer.parseInt(requestMsg.getString("articleId"));
        return new JsonResponse<List<CommentResponse>>("1","success",userService.getComment(articleId));
    }
}
