package com.game.sanguo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.LoggerFactory;

import com.game.sanguo.domain.CityInfo;
import com.game.sanguo.domain.Pair;

public class XlsFileUtil {
	static org.slf4j.Logger logger = LoggerFactory.getLogger(XlsFileUtil.class);

	/**
	 * 返回文件名
	 * 
	 * @return
	 */
	public static void createSearchResultFile(List<Pair<String, List<CityInfo>>> cityInfoListArray, String sortType) {
		try {
			File fileName = generagteFileName();
			HSSFWorkbook wb = new HSSFWorkbook();
			for (Pair<String, List<CityInfo>> cityInfoPair : cityInfoListArray) {
				HSSFSheet sheet = wb.createSheet(cityInfoPair.first);
				generateExcelTitle(sheet);
				int i = 1;
				try {
					geneteSheetData(cityInfoPair, sortType, sheet, i);
				} catch (Exception e) {
					logger.error("生成excel文件异常", e);
				}
			}
			FileOutputStream fileOut = new FileOutputStream(fileName);
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			logger.error("生成excel文件异常", e);
		}
	}

	private static void geneteSheetData(Pair<String, List<CityInfo>> cityInfoPair, String sortType, HSSFSheet sheet, int i) {
		sort(cityInfoPair.second, sortType);
		for (CityInfo cityInfo : cityInfoPair.second) {
			try {
				HSSFRow row = sheet.createRow(i++);
				HSSFCell cityId = row.createCell(0);
				HSSFCell occupierName = row.createCell(1);
				HSSFCell occupierVipLv = row.createCell(2);
				HSSFCell unionName = row.createCell(3);
				HSSFCell occupierTime = row.createCell(4);
				HSSFCell occupierEndTime = row.createCell(5);
				cityId.setCellValue(cityInfo.getId());
				if (cityInfo.getOccupierName() == null || cityInfo.getOccupierName().equals("") || cityInfo.getOccupierName().equals("null")) {
					occupierName.setCellValue("空资源");
					occupierVipLv.setCellValue("");
					unionName.setCellValue("");
					occupierTime.setCellValue("");
					occupierEndTime.setCellValue("");
				} else {
					occupierName.setCellValue(GameUtil.parseUnicode(cityInfo.getOccupierName()));
					occupierVipLv.setCellValue(cityInfo.getOccupierVipLv());
					unionName.setCellValue(GameUtil.parseUnicode(cityInfo.getUnionName()));
					occupierTime.setCellValue(GameUtil.parseDate(new Date(Long.parseLong(cityInfo.getOccupyTime()))));
					occupierEndTime.setCellValue(GameUtil.parseDate(generateEndTime(new Date(Long.parseLong(cityInfo.getOccupyTime())), cityInfo.getOccupierVipLv())));
				}
			} catch (Exception e) {
				logger.error("生成单元格数据异常",e);
			}
		}
	}

	private static void sort(List<CityInfo> second, final String sortType) {
		Comparator<CityInfo> comparator = null;
		if (sortType != null && sortType.equals("byName")) {
			comparator = new Comparator<CityInfo>() {
				public int compare(CityInfo o1, CityInfo o2) {
					if (o1.getOccupierName() == null || o1.getOccupierName().equals("")) {
						return 1;
					}
					if (o2.getOccupierName() == null || o2.getOccupierName().equals("")) {
						return -1;
					}
					return o1.getOccupierName().compareTo(o2.getOccupierName());
				}
			};
		} else {
			comparator = new Comparator<CityInfo>() {
				public int compare(CityInfo o1, CityInfo o2) {
						if (o1.getOccupierName() == null || o1.getOccupierName().equals("")) {
							return -1;
						}
						if (o2.getOccupierName() == null || o2.getOccupierName().equals("")) {
							return 1;
						}
						Date o1End = generateEndTime(new Date(Long.parseLong(o1.getOccupyTime())), o1.getOccupierVipLv());
						Date o2End = generateEndTime(new Date(Long.parseLong(o2.getOccupyTime())), o2.getOccupierVipLv());
						return o1End.compareTo(o2End);
				}
			};
		}
		Collections.sort(second, comparator);
	}

	private static Date generateEndTime(Date date, String alevel) {
		int level = Integer.parseInt(alevel);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		if (level == 9) {
			cal.add(Calendar.DAY_OF_MONTH, 3);
		} else if (level == 8) {
			cal.add(Calendar.DAY_OF_MONTH, 2);
		} else {
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		return cal.getTime();
	}

	private static void generateExcelTitle(HSSFSheet sheet) {
		HSSFRow row = sheet.createRow(0);
		HSSFCell cityId = row.createCell(0);
		HSSFCell occupierName = row.createCell(1);
		HSSFCell occupierVipLv = row.createCell(2);
		HSSFCell unionName = row.createCell(3);
		HSSFCell occupierTime = row.createCell(4);
		HSSFCell occupierEndTime = row.createCell(5);
		cityId.setCellValue("资源ID");
		occupierName.setCellValue("占领者");
		occupierVipLv.setCellValue("占领者VIP级别");
		unionName.setCellValue("所属联盟");
		occupierTime.setCellValue("占领时间");
		occupierEndTime.setCellValue("到期时间");
	}

	private static File generagteFileName() {
		File f = new File("searchResult-" + GameUtil.parseDate(new Date(System.currentTimeMillis())).replaceAll("[:]", "_") + ".xls");
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return f;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long cy = System.currentTimeMillis();
		List<CityInfo> aList = new ArrayList<CityInfo>();
		CityInfo ci1 = new CityInfo();
		ci1.setId("1231");
		ci1.setOccupierName("diguo1");
		ci1.setOccupierVipLv("8");
		ci1.setOccupyTime("" + cy);
		aList.add(ci1);

		ci1 = new CityInfo();
		ci1.setId("1232");
		aList.add(ci1);
		ci1 = new CityInfo();
		
		ci1.setId("1233");
		aList.add(ci1);
		
		ci1.setId("1234");
		aList.add(ci1);
		
		ci1.setId("1235");
		aList.add(ci1);

		List<Pair<String, List<CityInfo>>> a = new ArrayList<Pair<String, List<CityInfo>>>();
		a.add(Pair.makePair("aaa", aList));
		createSearchResultFile(a,"byName");
	}

}
