package CommonClasses;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

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

	public ArrayList<String> getRandomItems(ArrayList<String> list, int size, int nbrOfItemsToRetrieve, Set<String> visitedItemIdSet){
		int i, maxIteration=1000;
		Random randomIndex = new Random((new Date()).getTime());
		int min=0;
		int randomNum;
		ArrayList<String> itemLst = new ArrayList<String>();

		for(i=0;i<nbrOfItemsToRetrieve;i++){
			randomNum= randomIndex.nextInt(size - min + 1) + min;
			int j=0;
			while(true){
				if((list.get(randomNum))!= null){
					if(!(visitedItemIdSet.contains(list.get(randomNum)))){
						itemLst.add(list.get(randomNum));
						break;
					}
				}else if(j==maxIteration){
					break;
				}
				randomNum= randomIndex.nextInt(size - min + 1) + min;
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

	public ArrayList<String> getRandomTour(Set<String> visitedItemIdSet) {
		if(itemIdLst==null){
			itemIdLst = new ArrayList<String>();
			countItems();
		}
		return getRandomItems(itemIdLst, nbrOfItems,ListSize, visitedItemIdSet);
	}

	public ArrayList<String> getTourByInterest(List<String> interests, Set<String> visitedItemIdSet) {
		ArrayList<String> interestedItems = new ArrayList<String>();
		if(interests.size() > 0){
			interestedItems = getInterestedItems(interests);
		}
		if(interestedItems.size() > 0){
			return getRandomItems(interestedItems, interestedItems.size(), ListSize, visitedItemIdSet);
		}
		return interestedItems;
	}

	private ArrayList<String> getInterestedItems(List<String> interests) {
		ArrayList<String> interestLst = new ArrayList<String>();
		HSSFCell subject;
		String Id;
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
								Id = cell.getStringCellValue();
								subject = row.getCell(searchCol + 2);
								if(subject!=null && subject.getStringCellValue()!=null){
									for(int i=0; i < interests.size(); i++){
										if(subject.getStringCellValue().startsWith(interests.get(i))){
											interestLst.add(Id);
											break;
										}
									}

								}
							}
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return interestLst;
	}

	public ArrayList<String> getTourByRating(Map<String, Integer> p3, Set<String> visitedItemIdSet) {
		List<String> list = new ArrayList<String>();
		ArrayList<String> t3 = new ArrayList<String>();
		//sort the map based on the ranking in a decreasing order
		ValueComparator bvc =  new ValueComparator(p3);
		TreeMap<String,Integer> sorted_map = new TreeMap<String, Integer>(bvc);
		sorted_map.putAll(p3);
		//get the interests list with high interest on the top
		for(Map.Entry<String,Integer> entry : sorted_map.entrySet()) {
			String id = entry.getKey();
			Integer value = entry.getValue();
			if(value>3){
				MuseumItem item = RetrieveItem(id);
				if(item!=null){
					list.add(item.getSubject());
				}  
			}
		}
		t3 = getTourByInterest(list, visitedItemIdSet);
		return t3;
	}
}
