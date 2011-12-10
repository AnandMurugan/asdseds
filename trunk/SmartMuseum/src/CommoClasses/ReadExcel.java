package CommoClasses;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ReadExcel {

	/**
	 * creates an {@link HSSFWorkbook} the specified OS filename.
	 */
	private String fileName = "C:/Users/Anand/Desktop/ID2209/Project/ItemList.xls";
	private static final int searchCol = 1;
	private static HSSFWorkbook readFile(String filename) throws IOException {
		return new HSSFWorkbook(new FileInputStream(filename));
	}

	public MuseumItem RetrieveItem(String Id) {
		MuseumItem item = new MuseumItem();
		String name, objectType, subject, material;
		if (fileName!=null) {
			try {
				HSSFWorkbook wb = ReadExcel.readFile(fileName);
				for (int k = 0; k < wb.getNumberOfSheets(); k++) {
					HSSFSheet sheet = wb.getSheetAt(k);
					int rows = sheet.getPhysicalNumberOfRows();
					System.out.println("Sheet " + k + " \"" + wb.getSheetName(k) + "\" has " + rows+ " row(s).");
					for (int r = 0; r < rows; r++) {
						HSSFRow row = sheet.getRow(r);
						if (row == null) {
							continue;
						}
						HSSFCell cell = row.getCell(searchCol);
						if (cell!=null){
							if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
								if(cell.getStringCellValue()!=null && cell.getStringCellValue() == Id){
									item.setId(Id);
									name= row.getCell(searchCol + 1).getStringCellValue();
									if(name!=null){
										item.setName(name);
									}
									subject= row.getCell(searchCol + 2).getStringCellValue();
									if(subject!=null){
										item.setSubject(subject);
									}
									objectType= row.getCell(searchCol + 3).getStringCellValue();
									if(objectType!=null){
										item.setObjectType(objectType);
									}
									material= row.getCell(searchCol + 4).getStringCellValue();
									if(material!=null){
										item.setMaterial(material);
									}
									return item;
								}
							}
						}
					}
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}