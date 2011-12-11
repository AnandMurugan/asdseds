package CommonClasses;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import Profiler.profile.MuseumItem;

public class ReadExcel {

	/**
	 * creates an {@link HSSFWorkbook} the specified OS filename.
	 */
	private String fileName = "ItemList.xls";
	private static final int searchCol = 0;
	private static final int ListSize = 15;
	private int nbrOfItems=0;
	private ArrayList<String> itemIdLst;
	private static HSSFWorkbook readFile(String filename) throws IOException {
		return new HSSFWorkbook(new FileInputStream(filename));
	}
	
	private void countItems() {
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
							if(cell.getStringCellValue()!=null){
								itemIdLst.add(cell.getStringCellValue());
								nbrOfItems++;
							}
						}
					}
				}
							
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public ArrayList<String> getRandomItems(){
		int i, maxIteration=1000;
		Random randomIndex = new Random((new Date()).getTime());
		int min=0;
		int randomNum;
		ArrayList<String> itemLst = new ArrayList<String>();
		if(itemIdLst==null){
			countItems();
		}
		for(i=0;i<ListSize;i++){
			randomNum= randomIndex.nextInt(nbrOfItems - min + 1) + min;
			int j=0;
			while(true){
				if((itemIdLst.get(randomNum))!= null){
					itemLst.add(itemIdLst.get(randomNum));
					break;
				}else if(j==maxIteration){
					break;
				}
				randomNum= randomIndex.nextInt(nbrOfItems - min + 1) + min;
				j++;
			}
		}
		return itemLst;
	}

	public int getNbrOfItems() {
		return nbrOfItems;
	}

	public void setNbrOfItems(int nbrOfItems) {
		this.nbrOfItems = nbrOfItems;
	}

	public ArrayList<String> getItemIdLst() {
		return itemIdLst;
	}

}