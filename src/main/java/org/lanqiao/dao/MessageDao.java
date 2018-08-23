package org.lanqiao.dao;

import org.lanqiao.entity.Message;

import java.util.List;

public class MessageDao extends BaseDao<Message>{

    public List<Message> getAllMessage(){
        return executeQuery("select * from message");
    }

    public int getAllCount(){
        return executeQueryCount("select count(id) from message");
    }

    public List<Message> getNumMessage(int num, int size){
        String sql = "select * from message limit ?,?";
        return executeQuery(sql, new Object[]{num, size});
    }

    public int insertMessage(String name, String msg){
        String sql = "insert into message(name, msg) values (?,?)";
        executeUpdate(sql, new Object[]{name, msg});
        return 1;
    }

}
