package org.lanqiao.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ShowUsersServlet")
//@WebServlet(name = "ShowUsersServlet", value = "/ShowUsersServlet")
//@javax.servlet.annotation.WebServlet(name = "ShowUsersServlet")
public class ShowUsersServlet extends HttpServlet {

    public ShowUsersServlet() {
        System.out.println("实例化");
    }

    public void init() {
        System.out.println("初始化");
    }

    public void destroy() {
        System.out.println("销毁");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("服务");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        System.out.println("POST服务");
        response.getWriter().print("你好");
    }
}
