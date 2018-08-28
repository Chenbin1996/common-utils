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
    private DataBaseUtil() {

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
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection(dbConfig);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SHOW TABLES");
            data = resultSetToList(resultSet);
            System.out.println("查询成功：[" + data + "]");
        } catch (SQLException e) {
            new RuntimeException("获取数据库表名失败[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, resultSet, statement);
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
            closeConnection(connection, null, statement);
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
            closeConnection(connection, null, statement);
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
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SHOW FULL COLUMNS FROM " + dbConfig.getTableName());
            while (resultSet.next()) {
                columnComments.add(resultSet.getString("Comment"));
            }
            System.out.println("查询成功：[" + columnComments + "]");
        } catch (SQLException e) {
            new RuntimeException("获取数据库表字段注释失败[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, resultSet, statement);
        }
        return columnComments;
    }

    /**
     * 新建数据表
     *
     * @param dbConfig
     * @param list     存放要添加的字段名称
     */
    public static void createTable(DbConfig dbConfig, List<String> list) {
        Connection connection = getConnection(dbConfig);
        Statement statement = null;
        ResultSet resultSet = null;
        StringBuilder sql = new StringBuilder();
        sql.append("CREATE TABLE IF NOT EXISTS ").append(dbConfig.getTableName())
                .append(" (").append("id VARCHAR(32) NOT NULL,");

        if (list != null) {
            for (String s : list) {
                sql.append(s).append(" VARCHAR(255),");
            }
        }
        sql.append(" PRIMARY KEY (id)").append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql.toString());
        } catch (SQLException e) {
            throw new RuntimeException("创建表失败：" + e.getMessage());
        } finally {
            closeConnection(connection, resultSet, statement);
        }
    }


    /**
     * 数据库插入
     *
     * @param dbConfig 数据库配置对象
     * @param hashMap  key存放着要插入的字段，value存放着插入字段的值
     * @param idType   如果是UUID就输入uuid
     * @return 返回主键
     */
    public static Object insert(DbConfig dbConfig, HashMap<String, String> hashMap, String idType) {
        Connection connection = getConnection(dbConfig);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StringBuilder sql = new StringBuilder();
        Iterator iterator = hashMap.keySet().iterator();
        String key;
        Object object = null;
        sql.append("INSERT INTO ").append(dbConfig.getTableName())
                .append(" (");
        String type = "uuid";
        if (type.equalsIgnoreCase(idType)) {
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
            statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                object = resultSet.getObject(1);
            }
            System.out.println("成功插入数据");
        } catch (SQLException e) {
            new RuntimeException("插入数据失败：[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, null, statement);
        }
        return object;
    }

    /**
     * 数据库查询
     *
     * @param sql         自定义SQL语句，比如多表联查等，不需要时传null
     * @param config      数据库配置对象
     * @param keyAndValue 键值对，当sql为null时，key=查询的字段名，value=查询的值
     * @param columnName  查询返回的字段列名，如果传null返回的是全部(*)，不然就是返回对应的字段列
     * @return
     */
    public static List<Map<String, Object>> query(DbConfig config, String sql, Map<String, String> keyAndValue, List<String> columnName) {
        if (keyAndValue.isEmpty()) {
            return null;
        }
        Connection connection = getConnection(config);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ResultSetMetaData metaData;
        StringBuilder builderSql = new StringBuilder();
        List<Map<String, Object>> maps = new ArrayList<>();
        builderSql.append("SELECT ");
        if (columnName == null) {
            builderSql.append("* ");
        } else {
            for (String s : columnName) {
                builderSql.append(s).append(",");
            }
        }
        builderSql.deleteCharAt(builderSql.length() - 1);
        builderSql.append(" FROM ").append(config.getTableName()).append(" WHERE ");
        for (String key : keyAndValue.keySet()) {
            builderSql.append(key).append(" = ").append("'").append(keyAndValue.get(key)).append("'").append(" AND ");
        }
        String executeSql;
        if (sql != null && !"".equals(sql)) {
            executeSql = sql;
        } else {
            executeSql = builderSql.substring(0, builderSql.lastIndexOf("AND"));
        }
        try {
            statement = connection.prepareStatement(executeSql);
            resultSet = statement.executeQuery();
            metaData = resultSet.getMetaData();
            System.out.println(metaData.getColumnCount());
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<>(16);
                for (int i = 0; i < metaData.getColumnCount(); i++) {
                    map.put(metaData.getColumnName(i + 1), resultSet.getObject(i + 1));
                }
                maps.add(map);
            }
        } catch (SQLException e) {
            throw new RuntimeException("数据查询失败：[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, resultSet, statement);
        }
        return maps;

    }

    /**
     * 删除指定表
     *
     * @param dbConfig 数据库配置对象
     */
    public static void deleteTable(DbConfig dbConfig) {
        Connection connection = getConnection(dbConfig);
        Statement statement = null;
        String sql = "DROP TABLE " + dbConfig.getTableName();
        try {
            connection.createStatement().executeUpdate(sql);
            System.out.println("成功删除数据");
        } catch (Exception e) {
            new RuntimeException("数据删除失败：[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, null, statement);
        }
    }

    /**
     * 获取所有不能为null的字段
     *
     * @param dbConfig
     * @return
     */
    public static List<String> getNotNUllField(DbConfig dbConfig) {
        Connection connection = getConnection(dbConfig);
        List<String> fields = new ArrayList<>();
        ResultSet rs = null;
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            rs = dbmd.getColumns(null, "%", dbConfig.getTableName(), "%");
            String flag;
            while (rs.next()) {
                flag = rs.getString("IS_NULLABLE");
                if ("NO".equals(flag)) {
                    fields.add(rs.getString("COLUMN_NAME"));
                }
            }
        } catch (SQLException e) {
            new RuntimeException("获取不为空字段失败：[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, rs, null);
        }
        return fields;
    }

    /**
     * 获取表主键信息
     *
     * @param dbConfig
     * @return
     */
    public static List<Map<String, Object>> getTablePk(DbConfig dbConfig) {
        Connection connection = getConnection(dbConfig);
        Statement statement = null;
        ResultSet rs = null;
        List<Map<String, Object>> tableConfig = new ArrayList<>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT p.TABLE_NAME AS tableName,p.COLUMN_NAME AS columnName,")
                .append("a.COLUMN_TYPE AS dataType,")
                .append("CASE WHEN p.column_Name IS NULL THEN 'false' ELSE 'true' END  AS pkColumn,")
                .append("CASE WHEN a.extra = 'auto_increment' THEN 'true' ELSE 'false' END  AS autoAdd ")
                .append("FROM ").append("( ")
                .append("SELECT TABLE_NAME,COLUMN_NAME ").append("FROM ")
                .append("INFORMATION_SCHEMA.KEY_COLUMN_USAGE ")
                .append("WHERE ").append("constraint_name = 'PRIMARY' ")
                .append("AND TABLE_SCHEMA = ").append("'" + dbConfig.getDataBase() + "'")
                .append(" GROUP BY TABLE_NAME")
                .append(" ) p").append(" INNER JOIN information_schema.COLUMNS a ")
                .append("ON (").append(" a.TABLE_SCHEMA = ").append("'" + dbConfig.getDataBase() + "'")
                .append(" AND a.TABLE_NAME = p.TABLE_NAME ")
                .append("AND p.COLUMN_NAME = a.COLUMN_NAME ").append(")");
        try {
            statement = connection.createStatement();
            statement.setQueryTimeout(60000);
            rs = statement.executeQuery(sql.toString());
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("tableName", rs.getString("tableName"));
                map.put("columnName", rs.getString("columnName"));
                map.put("jdbcType", rs.getString("jdbcType"));
                tableConfig.add(map);
            }
        } catch (SQLException e) {
            new RuntimeException("表主键信息：[" + e.getMessage() + "]");
        } finally {
            closeConnection(connection, rs, statement);
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
            throw new RuntimeException("连接失败,请检查数据库驱动类型是否正确");
        } catch (SQLException e) {
            if (e.getMessage().contains(usingPassword)) {
                throw new RuntimeException("数据连接失败，帐号密码错误");
            }
            if (e.getMessage().contains(unknownDatabase)) {
                throw new RuntimeException("找不到数据库名" + e.getMessage().substring((e.getMessage().indexOf("'")) + 1, e.getMessage().lastIndexOf("'")));
            }
            if (e.getMessage().contains(linkFailure)) {
                throw new RuntimeException("数据库连接失败，请检查配置是否正确！");
            }
            throw new RuntimeException("MYSQL" + "数据库连接失败[" + e.getMessage() + "]");
        }
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
    public static void closeConnection(Connection connection, ResultSet resultSet, Statement statement) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException("resultSet关闭出错：" + e.getMessage());
            }
            resultSet = null;
        }

        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new RuntimeException("statement关闭出错：" + e.getMessage());
            }
            statement = null;
        }

        if (connection != null) {
            try {
                connection.close();
                System.out.println("数据库关闭连接");
            } catch (SQLException e) {
                throw new RuntimeException("connection关闭出错：" + e.getMessage());
            }
            connection = null;
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>(16);
        map.put("user_name", "默认用户");
        DbConfig config = new DbConfig();
        config.setUrl("127.0.0.1");
        config.setUser("root");
        config.setPassWord("root");
        config.setPort("3306");
        config.setDataBase("coon");
        config.setTableName("user");
        config.setDriverClass("com.mysql.jdbc.Driver");
        List<String> column = new ArrayList<>();
        column.add("user_name");
        List<Map<String, Object>> maps = query(config, null, map, column);
        for (Map<String, Object> stringObjectMap : maps) {
            System.out.println(stringObjectMap);
        }
    }
}
