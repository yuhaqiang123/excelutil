/**
* 
* <p>Description: </p>
* <p>Company: 燕山大学</p> 
* @author   于海强
* @date       2015-12-18
*/
package cn.bronze.util.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



/** * @author  作者 E-mail: 
 * @date 创建时间：2015-12-18 上午9:56:17 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return  
 */
/**    
 * 操作Excel表格的功能类    
 * @author：    
 * @version 1.0    
 */     
public class ExcelReader {      
    private POIFSFileSystem fs;      
    private XSSFWorkbook wb;      
    private XSSFSheet sheet;      
    private XSSFRow row;      
    private InputStream is;
    public ExcelReader(InputStream is){
    	this.is = is;
    }
    
    /**    
     * 读取Excel表格表头的内容    
     * @param InputStream    
     * @return String 表头内容的数组    
     *     
     */     
    public String[] readExcelTitle() {      
        try {      
            /*fs = new POIFSFileSystem(is);*/
            wb = new XSSFWorkbook(is);      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        sheet = wb.getSheetAt(0);      
        row = sheet.getRow(0);      
        //标题总列数      
        int colNum = row.getPhysicalNumberOfCells();      
        String[] title = new String[colNum];    
        for (int i=0; i<colNum; i++) {      
            title[i] = getStringCellValue(row.getCell((short) i));      
        }      
        /*try {
			is.close();
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}*/
        return title;      
    }      
          
    /**    
     * 读取Excel数据内容    
     * @param InputStream    
     * @return Map 包含单元格数据内容的Map对象    
     */     
    public Map<Integer,String> readExcelContent() {      
        Map<Integer,String> content = new HashMap<Integer,String>();      
        String str = "";      
        try {
            
            wb = new XSSFWorkbook(is);      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        sheet = wb.getSheetAt(0);      
        //得到总行数      
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();      
        //正文内容应该从第二行开始,第一行为表头的标题  
        for (int i = 1; i <= rowNum; i++) {      
            row = sheet.getRow(i);
            int j = 0;      
            while (j<colNum) {      
        //每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据      
        //也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
            	//User user = new User();
                str += getStringCellValue(row.getCell((short) j)).trim() + "-";
                
                j ++;
            }      
            content.put(i, str);      
            str = "";      
        }      
        
        return content;      
    }
    
    private XSSFSheet getSheet(){

    	if(sheet==null){
    		try {
                /*fs = new POIFSFileSystem(is);*/
                wb = new XSSFWorkbook(is); 
            } catch (IOException e) {
                e.printStackTrace();
            }
            sheet = wb.getSheetAt(0);
    	}
        return sheet;
    }
    
    public String[] getTitles() throws ExcelIllegalException{
    
    	getSheet();
    	XSSFRow row = sheet.getRow(0);
    	if(row==null){
    		throw new ExcelIllegalException("Excel第一行应该为标题");
    	}
    	int colNum = row.getPhysicalNumberOfCells();
    	String title[] = new String[colNum];
    	for (short i = 0; i < colNum; i++) {
			title[i] = getStringCellValue(row.getCell(i)).trim();
			
		}
    	return title;
    }
    
    public List<Map<String, String>> getRemarkWithValue(Class clazz) {
    	getSheet();
    	int rowNum = sheet.getLastRowNum();
    	List<Map<String, String>> list = 
    			new ArrayList<Map<String,String>>(rowNum);
    	String[] titles = null;
		try {
			titles = getTitles();
		} catch (ExcelIllegalException e) {
			// 
			e.printStackTrace();
		}
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();      
        //正文内容应该从第二行开始,第一行为表头的标题      
        
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            Map<String, String> map = new LinkedHashMap<String,String>();
            while (j<colNum) {
            	
            	map.put(titles[j]
            			,getStringCellValue(row.getCell((short)j)));

            	
            	j++;
            }
            list.add(map);
        }      
        return list;
    }
    
   
    
    
  /*  public Map<Integer,User> SetUser(InputStream is,String[] title,String[] attention) {      
        Map<Integer,User> content = new HashMap<Integer,User>();      
        String str = "";      
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);      
        } catch (IOException e) {      
            e.printStackTrace();      
        }      
        sheet = wb.getSheetAt(0);      
        //得到总行数      
        int rowNum = sheet.getLastRowNum();      
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();      
        //正文内容应该从第二行开始,第一行为表头的标题      
        Random random = new Random();
        
        for (int i = 1; i <= rowNum; i++) {      
            row = sheet.getRow(i);
            int j = 0;
            User user = new User();
            int num = random.nextInt();
        	if(num<0)
        	{
        		num = 0-num;
        	}
            int atten = num%(attention.length);
        	user.setAttention(attention[atten]);
        	user.setRegister_time(new java.sql.Date(System.currentTimeMillis()));
            while (j<colNum) {
        //每个单元格的数据内容用"-"分割开，以后需要时用String类的replace()方法还原数据      
        //也可以将每个单元格的数据设置到一个javabean的属性中，此时需要新建一个javabean
            	
            	
            	
            	j++;
            }
            content.put(i, user);
        }      
        return content;      
    }      */
    
    
    /**    
     * 获取单元格数据内容为字符串类型的数据    
     * @param cell Excel单元格    
     * @return String 单元格数据内容    
     */     
    private String getStringCellValue(XSSFCell cell) {      
        String strCell = "";      
        switch (cell.getCellType()) {      
        case XSSFCell.CELL_TYPE_STRING:      
            strCell = cell.getStringCellValue();      
            break;      
        case XSSFCell.CELL_TYPE_NUMERIC:      
            strCell = String.valueOf(cell.getNumericCellValue());      
            break;      
        case XSSFCell.CELL_TYPE_BOOLEAN:      
            strCell = String.valueOf(cell.getBooleanCellValue());      
            break;      
        case XSSFCell.CELL_TYPE_BLANK:      
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
    
//    public  void SetValue(User user,String key,String value)
//    {
//    	//System.out.println("key:"+key+"value:"+value);
//    	if(key.equals("准考证"))
//    	{
//    		user.setPassword(value);
//    	}
//    	
//    	if(key.equals("姓名"))
//    	{
//    		user.setUsername(value);
//    	}
//    	
//    	if(key.equals("性别"))
//    	{
//    		user.setSex(value);
//    	}
//    	
//    	if(key.equals("学号"))
//    	{
//    		user.setStudent_id(value);
//    	}
//    	
//    	
//    	
//    	
//    	
//    	
//    	
//    }
//    
    
    /**    
     * 获取单元格数据内容为日期类型的数据    
     * @param cell Excel单元格    
     * @return String 单元格数据内容    
     */     
    private String getDateCellValue(XSSFCell cell) {      
        String result = "";      
        try {      
            int cellType = cell.getCellType();      
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {      
                Date date = cell.getDateCellValue();      
                result = (date.getYear()) + "-" + (date.getMonth() + 1)       
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
          
    public static void main(String[] args) {      
       
    }      
}   

 