package com.ruxuanwo.utils;

import java.io.*;

/**
 * 读取文件内容
 *
 * @author 如漩涡
 */
public class ConfigFileUtil {
    private ConfigFileUtil(){

    }

    /**
     * @Title: readFile
     * @Description: 根据文件路径和编码格式, 读取文件内容字符串并返回
     * @param: @param Path
     * @param: @param encoding
     * @param: @return
     * @return: String
     * @throws
     */
    public static String readFile(String path, String encoding) {

        BufferedReader reader = null;

        StringBuffer sb = new StringBuffer("");
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        try {
            fileInputStream = new FileInputStream(path);
            inputStreamReader = new InputStreamReader(fileInputStream, encoding);
            reader = new BufferedReader(inputStreamReader);

            String tempString = null;

            while ((tempString = reader.readLine()) != null) {

                sb.append(tempString);
            }

        } catch (IOException e) {
            new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    new RuntimeException(e);
                }
            }
            if(inputStreamReader != null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    new RuntimeException(e);
                }
            }
            if(fileInputStream != null){
                try{
                    fileInputStream.close();
                }catch (IOException e){
                    new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 返回值参考 {@link String}
     * @Title: readConfigFile
     * @Description: 根据相对路径获取文件内容String, 支持两种模式下的文件读取:jar|war
     * @param relativePath
     * @return
     * String {@link String}
     * @throw
     */
    public static String readConfigFile(String relativePath){

        StringBuffer sb = new StringBuffer("");

        InputStream is = ConfigFileUtil.class.getClassLoader().getResourceAsStream(relativePath);
        BufferedReader reader = null;
        if(is != null){
            reader = new BufferedReader(new InputStreamReader(is));
        }

        String tempString = null;

        try {
            while ((tempString = reader.readLine()) != null) {

                sb.append(tempString);
            }
        } catch (IOException e) {
            new RuntimeException(e);
        }finally{
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    new RuntimeException(e);
                }
            }
            if(is != null){
                try{
                    is.close();
                }catch (IOException e){
                    new RuntimeException(e);
                }
            }
        }

        return sb.toString();
    }
}
