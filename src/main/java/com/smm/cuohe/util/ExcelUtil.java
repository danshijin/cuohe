package com.smm.cuohe.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ExcelUtil {

	public void parseExcel(Map<String, String> directorymap, InputStream stream)
			throws Exception {
		if (isExcel2003(stream)) {
			System.out.println("--3-:"
					+ parseExcel2003(stream, directorymap).size() + "====:"
					+ parseExcel2003(stream, directorymap));
		} else if (isExcel2007(stream)) {
			System.out.println("---:"
					+ parseExcel2007(stream, directorymap).size() + "====:"
					+ parseExcel2007(stream, directorymap));
		}
	}

	// 读取2007
	public List<Map<String, String>> parseExcel2007(InputStream stream,
			Map<String, String> directorymap) throws Exception {
		List<Map<String, String>> list = new ArrayList<>();
		XSSFWorkbook xsworkbook = new XSSFWorkbook(stream);
		/*
		 * for(Iterator<XSSFSheet> iterator = xsworkbook.iterator();
		 * iterator.hasNext();){ XSSFSheet xssfSheet = iterator.next();
		 */
		XSSFSheet xssfSheet = xsworkbook.getSheetAt(0);
		String CValue = "";
		String[] directory;
		directory = new String[xssfSheet.getRow(0).getLastCellNum()];
		for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			Map<String, String> map = new HashMap<>();
			XSSFRow row = xssfSheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			if (rowNum == 0) {
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					XSSFCell cell = row.getCell(cellNum);
					if ("".equals(cell) || cell == null) {
						CValue = "";
					} /*
					 * else if (cell.getCellType() ==
					 * XSSFCell.CELL_TYPE_BOOLEAN) { CValue =
					 * String.valueOf(cell.getBooleanCellValue()); } else if
					 * (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					 * CValue = String.valueOf(cell.getNumericCellValue()); }
					 * else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
					 * { CValue = cell.getStringCellValue(); }
					 */
					else {
						CValue = getCellFormatValue(cell);
					}
					directory[cellNum] = CValue;
				}
			} else {
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					XSSFCell cell = row.getCell(cellNum);
					if ("".equals(cell) || cell == null) {
						CValue = "";
					} /*
					 * else if (cell.getCellType() ==
					 * XSSFCell.CELL_TYPE_BOOLEAN) { CValue =
					 * String.valueOf(cell.getBooleanCellValue()); } else if
					 * (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					 * CValue = String.valueOf(cell.getNumericCellValue()); }
					 * else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
					 * { CValue = cell.getStringCellValue(); }
					 */
					else {
						CValue = getCellFormatValue(cell);
					}
					map.put(directorymap.get(directory[cellNum]), CValue);
					if (cellNum == (directory.length - 1)) {
						break;
					}
				}
				// }
			}
			if (rowNum != 0) {
				list.add(map);
			}
		}

		return list;
	}

	/*
	 * 采用表头作为map的key
	 */
	public List<Map<String, String>> parseExcel2007(InputStream stream)
			throws Exception {
		List<Map<String, String>> list = new ArrayList<>();
		XSSFWorkbook xsworkbook = new XSSFWorkbook(stream);
		/*
		 * for(Iterator<XSSFSheet> iterator = xsworkbook.iterator();
		 * iterator.hasNext();){ XSSFSheet xssfSheet = iterator.next();
		 */
		XSSFSheet xssfSheet = xsworkbook.getSheetAt(0);
		String CValue = "";
		String[] directory;
		directory = new String[xssfSheet.getRow(0).getLastCellNum()];
		for (int rowNum = 0; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
			Map<String, String> map = new HashMap<>();
			XSSFRow row = xssfSheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			if (rowNum == 0) {
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					XSSFCell cell = row.getCell(cellNum);
					if ("".equals(cell) || cell == null) {
						CValue = "";
					} else {
						CValue = getCellFormatValue(cell);
					}
					Boolean isDirExist = false;
					for (int dNum = 0; dNum < directory.length; dNum++) {
						if (directory[dNum] == null) {
							continue;
						}
						if (directory[dNum].equals(CValue)) {
							isDirExist = true;
						}
					}
					if (!isDirExist) {
						directory[cellNum] = CValue;
					} else {
						directory[cellNum] = "第" + cellNum + "列表头重复";
					}
				}
			} else {
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					XSSFCell cell = row.getCell(cellNum);
					if ("".equals(cell) || cell == null) {
						CValue = "";
					} else {
						CValue = getCellFormatValue(cell);
					}
					map.put(directory[cellNum], CValue);
					if (cellNum == (directory.length - 1)) {
						break;
					}
				}
				// }
			}
			if (rowNum != 0) {
				list.add(map);
			}
		}

		return list;
	}

	// 读取2003
	public List<Map<String, String>> parseExcel2003(InputStream stream,
			Map<String, String> directorymap) throws Exception {

		List<Map<String, String>> list = new ArrayList<>();

		HSSFWorkbook hsworkbook = new HSSFWorkbook(stream);
		HSSFSheet hssfSheet = hsworkbook.getSheetAt(0);

		String CValue = "";
		String[] directory;
		directory = new String[hssfSheet.getRow(0).getLastCellNum()];
		for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			Map<String, String> map = new HashMap<>();
			HSSFRow row = hssfSheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			if (rowNum == 0) {
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					HSSFCell cell = row.getCell(cellNum);
					if ("".equals(cell) || cell == null) {
						CValue = "";
					} /*
					 * else if (cell.getCellType() ==
					 * XSSFCell.CELL_TYPE_BOOLEAN) { CValue =
					 * String.valueOf(cell.getBooleanCellValue()); } else if
					 * (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					 * CValue = String.valueOf(cell.getNumericCellValue()); }
					 * else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
					 * { CValue = cell.getStringCellValue(); }
					 */
					else {
						CValue = getCellFormatValue(cell);
					}
					directory[cellNum] = CValue;
				}
			} else {
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					HSSFCell cell = row.getCell(cellNum);
					if ("".equals(cell) || cell == null) {
						CValue = "";
					} /*
					 * else if (cell.getCellType() ==
					 * XSSFCell.CELL_TYPE_BOOLEAN) { CValue =
					 * String.valueOf(cell.getBooleanCellValue()); } else if
					 * (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
					 * CValue = String.valueOf(cell.getNumericCellValue()); }
					 * else if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
					 * { CValue = cell.getStringCellValue(); }
					 */
					else {
						CValue = getCellFormatValue(cell);
					}
					map.put(directorymap.get(directory[cellNum]), CValue);
					if (cellNum == (directory.length - 1)) {
						break;
					}
				}
				// }
			}
			if (rowNum != 0) {
				list.add(map);
			}
		}

		return list;

	}

	/*
	 * 采用表头作为map的key
	 */
	public List<Map<String, String>> parseExcel2003(InputStream stream)
			throws Exception {

		List<Map<String, String>> list = new ArrayList<>();

		HSSFWorkbook hsworkbook = new HSSFWorkbook(stream);
		HSSFSheet hssfSheet = hsworkbook.getSheetAt(0);

		String CValue = "";
		String[] directory;
		directory = new String[hssfSheet.getRow(0).getLastCellNum()];
		for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
			Map<String, String> map = new HashMap<>();
			HSSFRow row = hssfSheet.getRow(rowNum);
			if (row == null) {
				continue;
			}

			if (rowNum == 0) {
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					HSSFCell cell = row.getCell(cellNum);
					if ("".equals(cell) || cell == null) {
						CValue = "";
					} else {
						CValue = getCellFormatValue(cell);
					}
					Boolean isDirExist = false;
					for (int dNum = 0; dNum < directory.length; dNum++) {
						if (directory[dNum] == null) {
							continue;
						}
						if (directory[dNum].equals(CValue)) {
							isDirExist = true;
						}
					}
					if (!isDirExist) {
						directory[cellNum] = CValue;
					} else {
						directory[cellNum] = "第" + cellNum + "列表头重复";
					}
				}
			} else {
				for (int cellNum = 0; cellNum < row.getLastCellNum(); cellNum++) {
					HSSFCell cell = row.getCell(cellNum);
					if ("".equals(cell) || cell == null) {
						CValue = "";
					} else {
						CValue = getCellFormatValue(cell);
					}
					map.put(directory[cellNum], CValue);
					if (cellNum == (directory.length - 1)) {
						break;
					}
				}
				// }
			}
			if (rowNum != 0) {
				list.add(map);
			}
		}

		return list;

	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(HSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(XSSFCell cell) {
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell.equals("") || strCell == null) {
			return "";
		}
		if (cell == null) {
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getDateCellValue(HSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getDateCellValue(XSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	/**
	 * 根据XSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(XSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					cellvalue = String.valueOf(cell.getNumericCellValue());
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}

	// 判断文件的版本
	public boolean isExcel2003(InputStream is) {
		try {
			new HSSFWorkbook(is);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public boolean isExcel2007(InputStream is) {
		try {
			new XSSFWorkbook(is);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public Map<String, String> parseXml_Refactoring() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(this.getClass().getClassLoader()
				.getResourceAsStream("excel.xml"));
		Element element = (Element) document.getRootElement();
		for (Iterator rootItr = element.elementIterator(); rootItr.hasNext();) {
			Element el = (Element) rootItr.next();
			if ("column".equals(el.getName())) {
				map.put(el.attributeValue("name"), el.getText());
			}
		}

		return map;
	}
}
