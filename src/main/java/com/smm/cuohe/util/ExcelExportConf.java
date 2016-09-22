package com.smm.cuohe.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * @author  zhaoyutao
 * @version 2015年10月14日 下午7:47:04
 * 类说明
 */

public class ExcelExportConf {
	
	private static CellStyle headStyle;
	private static CellStyle dataStyle;
	private static String fontFamily = "微软雅黑";
	private static short fontSize = (short) 12;
	private static short borderWidth = CellStyle.BORDER_THIN;
	
	public static float columnHeight = 22.5f;
	
	/**
	 * @param workbook
	 * @return
	 */
	public static CellStyle getHeadStyle(Workbook workbook) {
		
		headStyle = workbook.createCellStyle();
		//设置边框  上右下左
		headStyle.setBorderTop(borderWidth);
		headStyle.setBorderRight(borderWidth);
		headStyle.setBorderBottom(borderWidth);
		headStyle.setBorderLeft(borderWidth);
		//设置水平垂直都居中
		headStyle.setAlignment(CellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		//设置字体 12px 加粗 微软雅黑
		Font font = workbook.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName(fontFamily);
		headStyle.setFont(font);
		
		return headStyle;
	}
	/**
	 * @param workbook
	 * @return
	 */
	public static CellStyle getDataStyle(Workbook workbook) {
		
		dataStyle = workbook.createCellStyle();
		//设置边框  上右下左
		dataStyle.setBorderBottom(borderWidth);
		dataStyle.setBorderLeft(borderWidth);
		dataStyle.setBorderRight(borderWidth);
		dataStyle.setBorderTop(borderWidth);
		//设置水平垂直都居中
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		//设置字体 12px 微软雅黑
		Font font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		font.setFontHeightInPoints(fontSize);
		font.setFontName(fontFamily);
		dataStyle.setFont(font);
		
		return dataStyle;
	}
	
	/** 设置宽度自适应,需要在excel内容设置完成后调用才有效
	 * 如果是合并单元格的情况，请用
	 * sheet.setColumnWidth(0, text.getBytes().length*2*256);
	 * @param sheet
	 * @param columns
	 */
	public static void setAutoColumn(Sheet sheet, int columns){
		for(int i = 0; i < columns; i++){
			sheet.autoSizeColumn(i);
		}
	}
}
