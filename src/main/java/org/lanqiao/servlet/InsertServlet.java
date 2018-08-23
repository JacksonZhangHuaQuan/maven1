package org.lanqiao.servlet;

import org.lanqiao.dao.MessageDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/InsertServlet")
public class InsertServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");//允许所有，不安全
        String name = request.getParameter("name");
        String msg = request.getParameter("msg");
        System.out.println("name:" + name);
        System.out.println("InsertServlet服务");
        int ret = new MessageDao().insertMessage(name,msg);
        PrintWriter out = response.getWriter();
        out.print(ret);
        out.flush();
        out.close();
    }
}
