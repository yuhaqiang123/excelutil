package cn.bronze.util.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

	private File file ;
    public ExcelWriter(File file){
    	this.file  = file;
    }
    
    private XSSFCellStyle getCellStyle(XSSFWorkbook wb){
    	XSSFCellStyle style = wb.createCellStyle();
        XSSFFont font = wb.createFont();
        font.setFontHeightInPoints((short) 9);
        style.setFont(font);
        style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN); //下边框
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);//右边框
        style.setWrapText(true);
        return style;
    }
    
    private void addTail(List<String> list,XSSFSheet sheet,int lastRow,int column){
    	
    	if(list==null){
    		return ;
    	}
    	for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
	    	
			 CellRangeAddress cra=new CellRangeAddress(
					 lastRow,lastRow , 0, column-1);        
		     System.out.println(lastRow+"  "+column);
		        //在sheet里增加合并单元格  
		     sheet.addMergedRegion(cra);
		     Row row = sheet.createRow(lastRow);
	         
		     Cell cell_1 = row.createCell(0); 
		      
		     cell_1.setCellValue(string);
		     lastRow++;
		}
       
    }
    
    public boolean write(List<Map<String,String>> list,List<String> tailList) 
    		throws ParamCanNotBeNullException{
    	try {
    		if(list==null||list.size()==0){
    			throw new ParamCanNotBeNullException("list");
    		}
			FileOutputStream output = new FileOutputStream(file);
			
			XSSFWorkbook wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet(String.valueOf(1));
			
			/**
			 * 设置style
			 */
	        XSSFCellStyle style = getCellStyle(wb);
	         
	         //设置sheet的标题
	         wb.setSheetName(0, file.getName());
	         XSSFRow row = sheet.createRow(0);
	         int j = 0;
	         
	         /**
	          * 设置标题栏，即第一行
	          */
	         for(Entry<String,String> e:list.get(0).entrySet()){
	       	  	 XSSFCell cell = row.createCell(j);        
	             cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式  
	             cell.setCellValue(e.getKey());//写入内容 
	             sheet.setColumnWidth(j, 2300);//设置列宽
	       	  	 cell.setCellStyle(style);
	             j++;
	         }

	         int column = j;
	            for(int i=1;i<=list.size();i++){
	                XSSFRow row1 = sheet.createRow(i);
	                row1.setHeight((short)1000);//设置行宽
	                Map<String,String> map =  list.get(i-1);
	                int z = 0;
		              for(Entry<String,String> e:map.entrySet()){
		            	  XSSFCell cell = row1.createCell(z);
		            	  sheet.setColumnWidth(z, 2300);//设置列宽
		            	  cell.setCellStyle(style);
		                  cell.setCellType(XSSFCell.CELL_TYPE_STRING);//文本格式  
		                  cell.setCellValue(e.getValue());//写入内容  
		                  z++;
		              }
	            }
	             addTail(tailList, sheet,sheet.getLastRowNum()+1,column);
	            
	         
	        wb.write(output); 
	        output.close();   
	        return true;
		} catch (FileNotFoundException e) {
			// 
			e.printStackTrace();
		} catch (IOException e1) {
			// 
			e1.printStackTrace();
		}
    	return false;

    }
    
    public boolean write(List<Map<String,String>> list) 
    		throws ParamCanNotBeNullException{
    	return write(list,null);
    
    }
    
   

}
