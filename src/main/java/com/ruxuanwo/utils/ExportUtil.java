package com.ruxuanwo.utils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Excel读写工具类
 *
 * @author: 如漩涡
 */
public class ExportUtil {
    private ExportUtil() {

    }

    /**
     * 生成excel模板
     *
     * @param list map集合，一个map对应一列，key是列头，value是列值
     * @return excel
     */
    public static Workbook export(List<HashMap<String, Object>> list) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        cellStyle.setDataFormat(format.getFormat("@"));
        for (int i = 0; i < list.size(); i++) {
            sheet.setDefaultColumnWidth((short) 15);
            sheet.setDefaultColumnStyle(i, cellStyle);
            Cell cell = row.createCell(i);
            cell.setCellValue(list.get(i).get("name").toString());
            isRed(workbook, cell, (Boolean) list.get(i).get("bool"));
        }
        return workbook;
    }

    /**
     * 字段飞空则字体颜色为红色
     *
     * @param workbook 当前excel
     * @param cell     单元格
     * @param flag     是否非空校验
     */
    private static void isRed(Workbook workbook, Cell cell, boolean flag) {
        if (flag) {
            setFont(workbook, cell, null, IndexedColors.RED.getIndex());
        } else {
            setFont(workbook, cell);
        }
    }

    /**
     * 设置字体格式和颜色 默认微软雅黑 默认颜色黑色
     *
     * @param workbook 当前excel
     * @param cell     单元格
     */
    private static void setFont(Workbook workbook, Cell cell) {
        setFont(workbook, cell, null, null);
    }

    /**
     * 设置字体格式和颜色
     *
     * @param workbook 当前excel
     * @param cell     单元格
     * @param fontName 字体格式 默认微软雅黑
     * @param color    字体颜色    默认黑色
     */
    private static void setFont(Workbook workbook, Cell cell, String fontName, Short color) {
        fontName = fontName == null ? "微软雅黑" : fontName;
        color = color == null ? IndexedColors.BLACK.getIndex() : color;
        CellStyle style = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        style.setDataFormat(format.getFormat("@"));
        style.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        style.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setFontName(fontName);
        font.setFontHeightInPoints((short) 12);
        font.setColor(color);
        style.setFont(font);
        cell.setCellStyle(style);
    }

    /**
     * 数据导入
     *
     * @param stream 文件流
     * @param path   文件全称
     * @return 数据集合
     */
    public static List<List<String>> importExcel(InputStream stream, String path, int size) {
        Workbook workbook = getWorkBook(stream, path);
        List<Sheet> sheets = getSheets(workbook);
        return readExcel(sheets, size);
    }

    /**
     * 判断获取不同版本的Workbook实现类
     *
     * @param stream 文件流
     * @param path   文件全称
     * @return Workbook对象
     */
    private static Workbook getWorkBook(InputStream stream, String path) {
        Workbook workbook = null;
        try {
            workbook = path.endsWith(".xls") ? (new HSSFWorkbook(stream))
                    : (path.endsWith(".xlsx") ? (new XSSFWorkbook(stream)) : (null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    /**
     * 获取每一个sheet，以防有多sheet情况
     *
     * @param book Workbook对象
     * @return sheet集合
     */
    private static List<Sheet> getSheets(Workbook book) {
        int numberOfSheets = book.getNumberOfSheets();
        List<Sheet> sheets = new ArrayList<>();
        for (int i = 0; i < numberOfSheets; i++) {
            sheets.add(book.getSheetAt(i));
        }
        return sheets;
    }

    /**
     * 解析导入的文件数据
     *
     * @param sheets sheet集合
     * @param size   单元格大小
     * @return 解析后的数据
     */
    private static List<List<String>> readExcel(List<Sheet> sheets, int size) {
        List<List<String>> dataList = new ArrayList<>();
        List<String> data;
        Row row;
        Cell cell;
        for (int i = 0; i < sheets.size(); i++) {
            Sheet sheet = sheets.get(i);
            if (sheet.getLastRowNum() >= 1) {
                System.out.println("=======" + sheet.getSheetName() + "=======");
            }

            Iterator<Row> sheetIterator = sheet.rowIterator();
            while (sheetIterator.hasNext()) {
                row = sheetIterator.next();
                if (row == null) {
                    continue;
                }
                data = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    cell = row.getCell(j);
                    if (cell != null) {
                        cell.setCellType(CellType.STRING);
                        data.add(cell.getStringCellValue());
                    } else {
                        data.add("");
                    }
                }
                dataList.add(data);
            }
        }
        return dataList;
    }
}
