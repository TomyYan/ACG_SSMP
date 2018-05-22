package cn.tomy.tool;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by tomy on 18-5-21.
 */
public class UploadServlet extends HttpServlet {


    // WEB-INF/upload/1/3 打散存储目录
    public String makeDir(String storePath, String fileName) {
        int hashCode = fileName.hashCode();// 得到文件名的hashcode码
        int dir1 = hashCode & 0xf;// 取hashCode的低4位 0~15
        int dir2 = (hashCode & 0xf0) >> 4;// 取hashCode的高4位 0~15
        String path = storePath  + "photo";
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        System.out.println("存储路径是"+path);
        return path;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
