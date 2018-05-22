package cn.tomy.controller;

import cn.tomy.config.Constant;
import cn.tomy.domain.*;
import cn.tomy.service.ServiceInter;
import cn.tomy.service.impl.UserService;
import cn.tomy.tool.ResponseMgr;
import cn.tomy.tool.TokenMgr;
import cn.tomy.utils.GsonUtil;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
        int i=-1;
        try{
            i=userService.updatePassword(userId,oldPassword,newPassword);
            System.out.println("结果"+i);
        }catch (Exception e){
            return new JsonResponse<String>("404",e.toString(),"fail");
        }
        if(i==1){
            return new JsonResponse<String>("1","success","ok");
        }else{
            return new JsonResponse<String>("404","fail","oldPasswordFail");
        }

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

    @RequestMapping(value="/AutoLogin", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object AutoLogin(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        String token=requestMsg.getString("token");
        if (token == null || token.equals("")) {
            return ResponseMgr.noLogin();
        }

        // 验证JWT的签名，返回CheckResult对象
        CheckResult checkResult = TokenMgr.validateJWT(token);
        if (checkResult.isSuccess()) {
            Claims claims = checkResult.getClaims();
            SubjectModel model = GsonUtil.jsonStrToObject(claims.getSubject(), SubjectModel.class);
            return new JsonResponse<SubjectModel>("1001","ok",model);
        } else {
            switch (checkResult.getErrCode()) {
                // 签名过期，返回过期提示码
                case Constant.JWT_ERRCODE_EXPIRE:
                    return ResponseMgr.loginExpire();
                // 签名验证不通过
                case Constant.JWT_ERRCODE_FAIL:
                    return ResponseMgr.noAuth();
                default:
                    break;
            }
        }
        return new JsonResponse<String>("1002","fail","fail");
    }

    @RequestMapping(value="/getToken", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object getToken(HttpServletRequest request) {
        String requestMsgStr = request.getHeader("requestMsg");
        try {
            requestMsgStr = new String(requestMsgStr.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(requestMsgStr);
        JSONObject requestMsg = JSONObject.parseObject(requestMsgStr);
        int userId=Integer.parseInt(requestMsg.getString("userId"));
        String userAccount=requestMsg.getString("userAccount");
        String jwt="fail";
        try {
            SubjectModel sub = new SubjectModel(userId, userAccount);
            jwt = TokenMgr.createJWT(Constant.JWT_ID, TokenMgr.generalSubject(sub), Constant.JWT_TTL);
        }catch (Exception e){
            return new JsonResponse<String>("404","fail",jwt);
        }
        return new JsonResponse<String>("1","success",jwt);
    }

    @RequestMapping(value="/getCommentArticle", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object getCommentArticle(HttpServletRequest request){
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
        return new JsonResponse("1", "success", userService.getCommentArticle(userId, start));
    }

    @RequestMapping(value="/uploadPhoto", produces = "multipart/form-data;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadPhoto(HttpServletRequest request,
                              @RequestParam(value = "description",required = false) String description,
                              @RequestParam("file") MultipartFile file)throws Exception{
        System.out.println("获取数据为:"+request.getServletContext().getRealPath("/images/"));
        System.out.println(description);
        if(!file.isEmpty()){
            String path=request.getServletContext().getRealPath("/images/");
            String fileName=file.getOriginalFilename();
            File filePath=new File(path,fileName);
            if(!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
            }
            try {
                file.transferTo((new File(path+File.separator+fileName)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "success";
        }else{
            return "error";
        }

    }

    @RequestMapping(value="/upload", produces = "multipart/form-data;charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            System.out.println("IP:" + request.getRemoteAddr());
            // 1、创建工厂类：DiskFileItemFactory
            DiskFileItemFactory facotry = new DiskFileItemFactory();
            String tempDir = request.getServletContext().getRealPath("/images/");
            System.out.println("address"+tempDir);
            facotry.setRepository(new File(tempDir));//设置临时文件存放目录
            // 2、创建核心解析类：ServletFileUpload
            ServletFileUpload upload = new ServletFileUpload(facotry);
            upload.setHeaderEncoding("UTF-8");// 解决上传的文件名乱码
            upload.setFileSizeMax(1024 * 1024 * 1024);// 单个文件上传最大值是1M
            upload.setSizeMax(2048 * 1024 * 1024);//文件上传的总大小限制

            // 3、判断用户的表单提交方式是不是multipart/form-data
            boolean bb = upload.isMultipartContent(request);
            if (!bb) {
                return;
            }
            // 4、是：解析request对象的正文内容List<FileItem>
            List<FileItem> items = upload.parseRequest(request);
            String storePath = request.getServletContext().getRealPath("/images/");
            for (FileItem item : items) {
                if (item.isFormField()) {
                    // 5、判断是否是普通表单：打印看看
                    String fieldName = item.getFieldName();// 请求参数名
                    String fieldValue = item.getString("UTF-8");// 请求参数值
                    System.out.println(fieldName + "=" + fieldValue);
                } else {
                    // 6、上传表单：得到输入流，处理上传：保存到服务器的某个目录中，保存时的文件名是啥？
                    String fileName = item.getName();// 得到上传文件的名称 C:\Documents
                    // and
                    // Settings\shc\桌面\a.txt
                    // a.txt
                    //解决用户没有选择文件上传的情况
                    if(fileName==null||fileName.trim().equals("")){
                        continue;
                    }
                    fileName = fileName
                            .substring(fileName.lastIndexOf("\\") + 1);
                    String newFileName =  fileName;
                    System.out.println("上传的文件名是：" + fileName);
                    InputStream in = item.getInputStream();
                    String savePath = new UploadServlet().makeDir(storePath, fileName) + "_"
                            + newFileName;
                    OutputStream out = new FileOutputStream(savePath);
                    byte b[] = new byte[1024];
                    int len = -1;
                    while ((len = in.read(b)) != -1) {
                        out.write(b, 0, len);
                    }
                    in.close();
                    out.close();
                    item.delete();//删除临时文件
                }
            }
        }catch(FileUploadBase.FileSizeLimitExceededException e){
            request.setAttribute("message", "单个文件大小不能超出5M");
            request.getRequestDispatcher("/message.jsp").forward(request,
                    response);
        }catch(FileUploadBase.SizeLimitExceededException e){
            request.setAttribute("message", "总文件大小不能超出7M");
            request.getRequestDispatcher("/message.jsp").forward(request,
                    response);
        }catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "上传失败");
            request.getRequestDispatcher("/message.jsp").forward(request,
                    response);
        }
    }
}
