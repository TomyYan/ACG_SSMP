package cn.tomy.controller;

import cn.tomy.tool.mail;

/**
 * Created by tomy on 18-4-13.
 */
public class Main {
    public static void main(String[] args) {
        try {
            mail.sendEmail("285032959@qq.com","1234");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
