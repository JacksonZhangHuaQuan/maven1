package org.lanqiao.dao;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDao<T> {
//    public final String dirver = "com.mysql.jdbc.Driver";
//    public final String url = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF8&useUnicode=true";
//    public final String name = "root";
//    public final String pass = "jackson";
    private static ComboPooledDataSource dataSource;
    private Class<T> clazz;

    static {
        try{
            //加载配置文件，导入一个核心类
            dataSource = new ComboPooledDataSource();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    //反射获得clazz
    @SuppressWarnings("unchecked")
    public BaseDao() {
        clazz = (Class<T>) ((ParameterizedType) this.getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }

    // 创建连接
//    public Connection createConn() {
//        Connection conn = null;
//        try {
//            // 1、加载驱动
//            Class.forName(dirver);
//            // 2、创建连接connection
//            conn = DriverManager.getConnection(url, name, pass);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return conn;
//    }

    //提供获得数据源
    public static ComboPooledDataSource getDataSource(){
        return dataSource;
    }

    //提供获得链接
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    // 关闭资源
    public void closeAll(ResultSet rs, Statement stat, Connection conn) {
        // 6、关闭一切
        try {
            if (rs != null)
                rs.close();
            if (stat != null)
                stat.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 关闭一切
    public void closeAll(Statement stat, Connection conn) {
        // 6、关闭一切
        try {
            if (stat != null)
                stat.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // DML
    public int executeUpdate(String sql, Object[] obj) {
        Connection conn = null;
        PreparedStatement stat = null;
        int ret = 0;
        try {
            conn = getConnection();
            // 3、创建传输对象statmemnt
            stat = conn.prepareStatement(sql);// ？不确定:类型、数量
            // 3+、绑定替换数据
            for (int i = 0; i < obj.length; i++) {
                stat.setObject(i + 1, obj[i]);
            }
            // 4、发送sql语句，并且接收返回结果 : DML -> executeUpdate ; DQL -> executeQuery
            ret = stat.executeUpdate();
            // 5、如果返回rs类型的数据，需要将数据转换成list
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(stat, conn);
        }
        return ret;
    }
    // DQL
    public List<T> executeQuery(String sql, Object[] obj) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();
        try {
            conn = getConnection();
            // 3、创建传输对象statmemnt
            stat = conn.prepareStatement(sql);// ？不确定:类型、数量
            // 3+、绑定替换数据
            for (int i = 0; i < obj.length; i++) {
                stat.setObject(i + 1, obj[i]);
            }
            // 4、发送sql语句，并且接收返回结果 : DML -> executeUpdate ; DQL -> executeQuery
            rs = stat.executeQuery();
            // 5、如果返回rs类型的数据，需要将数据转换成list
            ResultSetMetaData rsmd = rs.getMetaData();//列名信息
            int columuCount = rsmd.getColumnCount();//列的数量
            while (rs.next()) {
                T t = (T) clazz.newInstance();//创建对象
                for (int i = 0; i < columuCount; i++) {//封装数据
                    Field f = clazz.getDeclaredField(rsmd.getColumnName(i + 1));//列名->属性名->属性对象
                    f.setAccessible(true);
                    f.set(t, rs.getObject(i + 1));//将rs列中的值赋给属性
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs,stat, conn);
        }
        return list;
    }
    // DQL
    public List<T> executeQuery(String sql) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<T>();
        try {
            conn = getConnection();
            // 3、创建传输对象statmemnt
            stat = conn.prepareStatement(sql);// ？不确定:类型、数量
            // 3+、绑定替换数据
            // 4、发送sql语句，并且接收返回结果 : DML -> executeUpdate ; DQL -> executeQuery
            rs = stat.executeQuery();
            // 5、如果返回rs类型的数据，需要将数据转换成list
            ResultSetMetaData rsmd = rs.getMetaData();//列名信息
            int columuCount = rsmd.getColumnCount();//列的数量
            while (rs.next()) {
                T t = (T) clazz.newInstance();//创建对象
                for (int i = 0; i < columuCount; i++) {//封装数据
                    Field f = clazz.getDeclaredField(rsmd.getColumnName(i + 1));//列名->属性名->属性对象
                    f.setAccessible(true);
                    f.set(t, rs.getObject(i + 1));//将rs列中的值赋给属性
                }
                list.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs,stat, conn);
        }
        return list;
    }


    public int executeQueryCount(String sql) {
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet rs = null;
        int t = 0;
        try {
            conn = getConnection();
            // 3、创建传输对象statmemnt
            stat = conn.prepareStatement(sql);// ？不确定:类型、数量
            // 3+、绑定替换数据
            // 4、发送sql语句，并且接收返回结果 : DML -> executeUpdate ; DQL -> executeQuery
            rs = stat.executeQuery();
            rs.next();
            t = rs.getInt(1);
            System.out.println(t);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeAll(rs,stat, conn);
        }
        return t;
    }
}

