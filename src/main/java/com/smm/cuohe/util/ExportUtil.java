package com.smm.cuohe.util;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

public class ExportUtil<T> {

	/**
	 * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
	 * 
	 * @param title
	 *            表格标题名
	 * @param headers
	 *            表格属性列名数组
	 * @param dataset
	 *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
	 *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
	 * @param out
	 *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
	 * @param pattern
	 *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
	 */
	public void export(String title, String[] headers, String[] headerNames,
			String[] comments, Collection<T> dataset, OutputStream out,
			String pattern1) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		CellStyle headStyle = ExcelExportConf.getHeadStyle(workbook);
		CellStyle dataStyle = ExcelExportConf.getDataStyle(workbook);
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(ExcelExportConf.columnHeight);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(headStyle);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text);
			if (null != comments && null != comments[i]) {
				// 设置注释内容
				comment.setString(new HSSFRichTextString(comments[i]));
				cell.setCellComment(comment);
			}
		}

		// 遍历集合数据，产生数据行
		Iterator<T> it = dataset.iterator();
		int index = 0;
		while (it.hasNext()) {
			index++;
			row = sheet.createRow(index);
			row.setHeightInPoints(ExcelExportConf.columnHeight);
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			// Field[] fields = t.getClass().getDeclaredFields();
			for (int i = 0; i < headerNames.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(dataStyle);
				String fieldName = headerNames[i];
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);

				try {
					Class<? extends Object> tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					// 判断值的类型后进行强制类型转换
					HSSFRichTextString textValue = null;
					if (value instanceof Integer) {
						cell.setCellValue((Integer) value);
					} else if (value instanceof Float) {
						textValue = new HSSFRichTextString(String.valueOf((Float) value));
						cell.setCellValue(textValue);
					} else if (value instanceof Double) {
						textValue = new HSSFRichTextString(String.valueOf((Double) value));
						cell.setCellValue(textValue);
					} else if (value instanceof Long) {
						long longValue = (Long) value;
						cell.setCellValue(longValue);
					} else if (value instanceof Date) {
						cell.setCellValue(DateUtil.doFormatDate((Date) value, pattern1));
					} else if (value instanceof String) {
						cell.setCellValue((String) value);
					} else if (value instanceof Character) {
						cell.setCellValue(value + "");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		ExcelExportConf.setAutoColumn(sheet, headers.length);
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void export(String title, List<String> listHeader,
			List<LinkedHashMap<String, String>> listData, OutputStream out) {
		// 声明一个工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet(title);
		CellStyle headStyle = ExcelExportConf.getHeadStyle(workbook);
		CellStyle dataStyle = ExcelExportConf.getDataStyle(workbook);
		/*
		// 声明一个画图的顶级管理器
		HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		 */
		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		row.setHeightInPoints(ExcelExportConf.columnHeight);
		for(int i=0;i<listHeader.size();i++){
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(headStyle);
			HSSFRichTextString text = new HSSFRichTextString(listHeader.get(i));
			cell.setCellValue(text);
		}
		
		// 遍历集合数据，产生数据行
		int index = 0;
		for (LinkedHashMap<String, String> item : listData) {
			index++;
			row = sheet.createRow(index);// 创建数据行
			row.setHeightInPoints(ExcelExportConf.columnHeight);
			for(int i=0;i<listHeader.size();i++){
				HSSFCell cell = row.createCell(i);
				cell.setCellStyle(dataStyle);
				String mapText="";
				if (item.containsKey(listHeader.get(i))) {
					mapText = item.get(listHeader.get(i));
				}
				HSSFRichTextString text = new HSSFRichTextString(mapText);
				cell.setCellValue(text);
			}
		}
		ExcelExportConf.setAutoColumn(sheet, listHeader.size());
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
