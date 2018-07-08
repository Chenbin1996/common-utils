package com.ruxuanwo.utils.model;

public class DbConfig {
    private String url;
    private String port;
    private String user;
    private String passWord;
    private String dataBase;
    private String driverClass;
    private String tableName;

    public DbConfig() {
    }

    public DbConfig(String url, String port, String user, String passWord, String dataBase, String driverClass, String tableName) {
        this.url = url;
        this.port = port;
        this.user = user;
        this.passWord = passWord;
        this.dataBase = dataBase;
        this.driverClass = driverClass;
        this.tableName = tableName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
