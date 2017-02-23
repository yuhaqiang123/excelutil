package cn.bronze.util.excel;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class StringToDateConverter implements Converter<String,Date>{

	

	@Override
	public Date converter(String value) throws ConverterException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			if(value==null||value.equals("")){
				return null;
			}
			return simpleDateFormat.parse(value);
		} catch (ParseException e) {
			throw new ConverterException(Date.class.getName());
		}
		
	}

}
