package cn.bronze.util.excel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToStringConverter implements Converter<Date, String> {

	public static void main(String[] args) {

	}

	@Override
	public String converter(Date value) throws ConverterException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
		Date date= value;
		String str=sdf.format(date);  
		return str;
	}

}
