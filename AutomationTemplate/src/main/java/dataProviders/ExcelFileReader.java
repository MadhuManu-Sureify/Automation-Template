package dataProviders;

import java.io.FileInputStream;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * @author madhubabu
 * @date 12-Mar-2020
 * @desc TODO
 */
public class ExcelFileReader 
{
	static HashMap<String, String> exelReadHash;
	public static HashMap<String, String> readExcel(String xlFilePath) throws Exception
	{
		Workbook book = null;
		try {
			exelReadHash = new HashMap<String, String>();
			FileInputStream file = new FileInputStream(xlFilePath);
			book = WorkbookFactory.create(file);
			Sheet sheet = book.getSheet("Customer");
			System.out.println("Rowcount = " + sheet.getLastRowNum() + 1);
			
			//do Task
			
//			// System.out.println(rowcount);
//			for (int i = 0; i < rowcount; i++) {
//				Row firstRow = sheet.getRow(i);
//				String key = "" + firstRow.getCell(0).toString();
//				String value = "" + firstRow.getCell(1).getStringCellValue();
//				exelReadHash.put(key, value);
//			}
		}
		catch(Exception e)
		{}

		book.close();
		
		return exelReadHash;
	}

}
