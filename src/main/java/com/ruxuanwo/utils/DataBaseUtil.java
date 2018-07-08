package com.ruxuanwo.utils;

import com.ruxuanwo.utils.model.DbConfig;

import java.sql.*;
import java.util.*;

/**
 * 数据库操作类
 *
 * @author 如漩涡
 */
public class DataBaseUtil {
    private DataBaseUtil(){

    }

    /**
     * 获取数据库下的所有表名
     *
     * @param dbConfig 数据库配置对象
     * @return List集合
     */
    public static List<String> getTableNames(DbConfig dbConfig) {
        List<String> data = null;
        Connection connection = null;
        try {
            connection = getConnection(dbConfig);
            ResultSet resultSet = connection.createStatement().executeQuery("SHOW TABLES");
            data = resultSetToList(resultSet);
            System.out.println("查询成功：[" + data + "]");
        } catch (SQLException e) {
            new RuntimeException("获取数据库表名失败[" + e.getMessage() + "]");
        } finally {
            if (connection != null) {
                closeConnection(connection);
                connection = null;
            }
        }
        return data;
    }

    /**
     * 获取数据库下的所有表字段名称
     *
     * @param dbConfig 数据库配置对象
     * @return List集合
     */
    public static List<String> getColumnNames(DbConfig dbConfig) {
        List<String> columnNames = new ArrayList<>();
        Connection connection = getConnection(dbConfig);
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + dbConfig.getTableName();
        try {
            statement = connection.prepareStatement(sql);
            //结果集元数据
            ResultSetMetaData metaData = statement.getMetaData();
            //表列数
            int size = metaData.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnNames.add(metaData.getColumnName(i + 1));
            }
            System.out.println("查询成功：[" + columnNames + "]");
        } catch (SQLException e) {
            new RuntimeException("获取数据库表字段失败[" + e.getMessage() + "]");

        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    if (connection != null) {
                        closeConnection(connection);
                        connection = null;
                    }
                } catch (SQLException e) {
                    new RuntimeException("数据库关闭失败:" + e.getMessage());
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取数据库下的所有表字段类型名称
     *
     * @param dbConfig 数据库配置对象
     * @return List集合
     */
    public static List<String> getColumnTypes(DbConfig dbConfig) {
        List<String> columnTypes = new ArrayList<>();
        Connection connection = getConnection(dbConfig);
        PreparedStatement statement = null;
        String sql = "SELECT * FROM " + dbConfig.getTableName();
        try {
            statement = connection.prepareStatement(sql);
            //结果集元数据
            ResultSetMetaData metaData = statement.getMetaData();
            //表列数
            int size = metaData.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnTypes.add(metaData.getColumnTypeName(i + 1));
            }
            System.out.println("查询成功：[" + columnTypes + "]");
        } catch (SQLException e) {
            new RuntimeException("获取数据库表字段类型失败[" + e.getMessage() + "]");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                    if (connection != null) {
                        closeConnection(connection);
                        connection = null;
                    }
                } catch (SQLException e) {
                    new RuntimeException("数据库关闭失败：[" + e.getMessage() + "]");
                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取数据库下的所有表字段注释
     *
     * @param dbConfig 数据库配置对象
     * @return List集合
     */
    private static List<String> getColumnComments(DbConfig dbConfig) {
        List<String> columnComments = new ArrayList<>();
        Connection connection = getConnection(dbConfig);
        ResultSet resultSet = null;
        try {
            resultSet = connection.createStatement().executeQuery("SHOW FULL COLUMNS FROM " + dbConfig.getTableName());
            while (resultSet.next()) {
                columnComments.add(resultSet.getString("Comment"));
            }
            System.out.println("查询成功：[" + columnComments + "]");
        } catch (SQLException e) {
            new RuntimeException("获取数据库表字段注释失败[" + e.getMessage() + "]");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                    if (connection != null) {
                        closeConnection(connection);
                        connection = null;
                    }
                } catch (SQLException e) {
                    new RuntimeException("数据库关闭失败：[" + e.getMessage() + "]");
                }
            }
        }
        return columnComments;
    }

    /**
     * 往数据库中插入数据
     *
     * @return
     */
    public static void insert(DbConfig dbConfig, HashMap<String, String> hashMap, String state){
        Connection connection = getConnection(dbConfig);
        StringBuilder sql = new StringBuilder();
        Iterator iterator = hashMap.keySet().iterator();
        String key;
        Object object = null;
        sql.append("INSERT INTO ").append(dbConfig.getTableName())
                .append(" (");
        String stringNum = "0";
        if (stringNum.equals(state)) {
            sql.append("id");
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                sql.append(",").append(key);
            }
            sql.append(" )").append("VALUES(").append("REPLACE(UUID(),\"-\",\"\") ");
            iterator = hashMap.keySet().iterator();
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                sql.append(",").append("'").append(hashMap.get(key)).append("'");
            }
            sql.append(")");
        } else {
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                sql.append(key).append(",");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(" )").append("VALUES(");
            iterator = hashMap.keySet().iterator();
            while (iterator.hasNext()) {
                key = (String) iterator.next();
                sql.append("'").append(hashMap.get(key)).append("'").append(",");
            }
            sql.deleteCharAt(sql.lastIndexOf(","));
            sql.append(")");
        }

        try {
            connection.createStatement().executeUpdate(sql.toString());
            System.out.println("成功插入数据");
        } catch (SQLException e) {
            new RuntimeException("插入数据失败：[" + e.getMessage() + "]");
        }finally {
            if (connection != null){
                closeConnection(connection);
                connection = null;
            }
        }
    }

    /**
     * 删除指定表
     *
     * @param dbConfig  数据库配置对象
     */
    public static void deleteTable(DbConfig dbConfig) {
        Connection connection = getConnection(dbConfig);
        String sql = "DROP TABLE " + dbConfig.getTableName();
        try {
            connection.createStatement().executeUpdate(sql);
            System.out.println("成功删除数据");
        } catch (Exception e) {
            new RuntimeException("数据删除失败：[" + e.getMessage() + "]");
        } finally {
            if (connection != null) {
                closeConnection(connection);
                connection = null;
            }
        }
    }

    /**
     * 获取所有不能为null的字段
     * @param dbConfig
     * @return
     */
    public static List<String> getNotNUllField(DbConfig dbConfig){
        Connection connection = getConnection(dbConfig);
        List<String> fields = new ArrayList<>();
        try {
            DatabaseMetaData dbmd=connection.getMetaData();
            ResultSet rs = dbmd.getColumns(null, "%", dbConfig.getTableName(), "%");
            String flag;
            while(rs.next()){
                flag = rs.getString("IS_NULLABLE");
                if("NO".equals(flag)){
                    fields.add(rs.getString("COLUMN_NAME"));
                }
            }
        } catch (SQLException e) {
            new RuntimeException("获取不为空字段失败：[" + e.getMessage() + "]");
        }finally {
            if (connection != null) {
                closeConnection(connection);
                connection = null;
            }
        }
        return fields;
    }

    /**
     * 获取表主键信息
     * @param dbConfig
     * @return
     */
    public static List<Map<String, Object>> getTablePk(DbConfig dbConfig){
        Connection connection = getConnection(dbConfig);
        List<Map<String, Object>> tableConfig = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT a.TABLE_NAME AS tableName,a.column_Name AS columnName,")
                .append("CASE WHEN p.column_Name IS NULL THEN 'false' ELSE 'true' END  AS pkColumn,")
                .append("CASE WHEN a.extra = 'auto_increment' THEN 'true' ELSE 'false' END  AS autoAdd,a.data_type jdbcType,")
                .append("column_COMMENT descr FROM information_schema.COLUMNS a ")
                .append("LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS p ON a.table_schema = p.table_schema ")
                .append("AND a.table_name = p.TABLE_NAME AND a.COLUMN_NAME = p.COLUMN_NAME ")
                .append("AND p.constraint_name = 'PRIMARY' ")
                .append("WHERE a.table_schema = ").append("'" + dbConfig.getDataBase() + "'")
                .append(" AND P.COLUMN_NAME IS NULL = 'true'").append(" ORDER BY a.ordinal_position ");
        try {
            ResultSet rs = connection.createStatement().executeQuery(sql.toString());
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("tableName", rs.getString("tableName"));
                map.put("columnName", rs.getString("columnName"));
                map.put("jdbcType", rs.getString("jdbcType"));
                tableConfig.add(map);
            }
        }catch (SQLException e){
            new RuntimeException("表主键信息：[" + e.getMessage() + "]");
        }finally {
            if (connection != null) {
                closeConnection(connection);
                connection = null;
            }
        }
        return tableConfig;
    }

    /**
     * 数据库连接
     *
     * @param dbConfig 连接配置对象
     * @return Connection对象
     */
    public static Connection getConnection(DbConfig dbConfig) {
        String usingPassword = "using password";
        String unknownDatabase = "Unknown database";
        String linkFailure = "link failure";
        try {
            Class.forName(dbConfig.getDriverClass());
            String url = "jdbc:mysql://" + dbConfig.getUrl() + ":" + dbConfig.getPort() + "/" + dbConfig.getDataBase() + "?connectTimeout=5000&socketTimeout=60000&useUnicode=true&characterEncoding=UTF-8&useSSL=false&rewriteBatchedStatements=true&useServerPrepStmts=false";
            System.out.println("正在连接[" + url + "]...");
            Connection connection = DriverManager.getConnection(url, dbConfig.getUser(), dbConfig.getPassWord());
            return connection;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("MYSQL" + "数据库连接失败[" + e.getMessage() + "]");
            new RuntimeException("连接失败,请检查数据库驱动类型是否正确！");
        } catch (SQLException e) {
            if (e.getMessage().contains(usingPassword)) {
                new RuntimeException("数据连接失败，帐号密码错误");
            }
            if (e.getMessage().contains(unknownDatabase)) {
                new RuntimeException("找不到数据库名" + e.getMessage().substring((e.getMessage().indexOf("'")) + 1, e.getMessage().lastIndexOf("'")));
            }
            if (e.getMessage().contains(linkFailure)) {
                throw new RuntimeException("数据库连接失败，请检查配置是否正确！");
            }
            new RuntimeException("MYSQL" + "数据库连接失败[" + e.getMessage() + "]");
        }
        return null;
    }
    /**
     * 转换元数据
     *
     * @param rs ResultSet对象
     * @return List数据
     * @throws SQLException
     */
    private static List<String> resultSetToList(ResultSet rs) throws SQLException {
        if (rs == null) {
            new RuntimeException("调试" + "转换失败元数据为空");
            return null;
        }
        //获取字段数
        int columnCount = rs.getMetaData().getColumnCount();
        List<String> result = new ArrayList<>();
        while (rs.next()) {
            for (int j = 1; j <= columnCount; j++) {
                result.add(rs.getString(j));
            }
        }
        return result;
    }

    /**
     * 关闭所有连接
     */
    public static void closeConnection(Connection connection) {
        try {
            connection.close();
            if (connection != null) {
                connection = null;
            }
            System.out.println("数据库关闭连接");
        } catch (SQLException e) {
            e.printStackTrace();
            new RuntimeException("关闭出错[" + e.getMessage() + "]");
        }
    }
}
