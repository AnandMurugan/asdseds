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
	private String fileName = "C:/ItemList.xls";
	private static final int searchCol = 0;
	private static HSSFWorkbook readFile(String filename) throws IOException {
		return new HSSFWorkbook(new FileInputStream(filename));
	}

	public MuseumItem RetrieveItem(String Id) {
		MuseumItem item = new MuseumItem();
		try {
			HSSFWorkbook wb = ReadExcel.readFile(fileName);
			for (int k = 0; k < wb.getNumberOfSheets(); k++) {
				HSSFSheet sheet = wb.getSheetAt(k);
				int rows = sheet.getPhysicalNumberOfRows();
				for (int r = 0; r < rows; r++) {
					HSSFRow row = sheet.getRow(r);
					if (row == null) {
						continue;
					}
					HSSFCell cell = row.getCell(searchCol);
					if (cell!=null){
						if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING){
							if(cell.getStringCellValue()!=null && cell.getStringCellValue().equals(Id)){
								HSSFCell name, subject, objectType, material;
								name = row.getCell(searchCol + 1);
								subject = row.getCell(searchCol + 2);
								objectType = row.getCell(searchCol +3);
								material = row.getCell(searchCol + 6);
								item.setId(Id);
								if(name!=null){
									item.setName(name.getStringCellValue());
								}
								if(subject!=null){
									item.setSubject(subject.getStringCellValue());
								}
								if(objectType!=null){
									item.setObjectType(objectType.getStringCellValue());
								}
								if(material!=null){
									item.setMaterial(material.getStringCellValue());
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
		return null;
	}
}