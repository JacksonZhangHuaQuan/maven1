package org.lanqiao.servlet;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.lanqiao.dao.MessageDao;
import org.lanqiao.entity.Message;
import org.lanqiao.util.JsonDateValueProcessor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;


@WebServlet("/MessageServlet")
public class MessageServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
        System.out.println("服务");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MessageDao dao = new MessageDao();
        response.setHeader("Access-Control-Allow-Origin", "*");//允许所有，不安全
        request.setCharacterEncoding("UTF-8");
        String numpage = request.getParameter("numpage");
        int num = 1;
        if(numpage!=null && !numpage.equals("")){
            num = Integer.parseInt(numpage);
        }
//        response.setCharacterEncoding("UTF-8");
        System.out.println("服务");
        List<Message> msgList = dao.getNumMessage((num-1)*3, 3);
//        JsonConfig jsonConfig = new JsonConfig();
//        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
//        JSONArray jsonArray = JSONArray.fromObject(msgList,jsonConfig);
        JSONArray listArray = JSONArray.fromObject(msgList);
        listArray.add(dao.getAllCount());
        PrintWriter send = response.getWriter();
        send.print(listArray.toString());
        send.flush();
        send.close();
    }
}
