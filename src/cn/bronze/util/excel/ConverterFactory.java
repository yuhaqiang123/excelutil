package cn.bronze.util.excel;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ConverterFactory {

	private static Map<String, Converter<?,?>> map = 
			new HashMap<String, Converter<?,?>>();
	private static DefaultConverter defaultConverter = 
			new DefaultConverter();

	private static Map<String, Converter<?, ?>> toStringMap = 
			new HashMap<String,Converter<?,?>>();
	
	static{
		toStringMap.put(Boolean.class.getName(),new BooleanToStringConverter());
		toStringMap.put(Integer.class.getName(), new IntegerToStringConverter());
		toStringMap.put(Date.class.getName(), new DateToStringConverter());
		toStringMap.put(Double.class.getName(), new DoubleToStringConverter());
		
	}
	
	
	static{
		map.put(Boolean.class.getName(),new  StringToBooleanConverter());
		map.put(Integer.class.getName(), new StringToIntConverter());
		map.put(Date.class.getName(), new StringToDateConverter());
		map.put(Double.class.getName(), new StringToDoubleConverter());
		
	}
	
	public static String converter(Object value,Field field) throws 
	ConverterException
	, ParamCanNotBeNullException{
		if(value==null||field==null){
			throw new  ParamCanNotBeNullException("value","field");
		}
		
		if(toStringMap.containsKey(field.getType().getName())){
			Converter<Object, String> converter = (Converter<Object, String>)
					toStringMap.get(field.getType().getName());
			return converter.converter(value);
		}else{
			return defaultConverter.converter(value.toString());
		}
	}
	
	
	public static Object converter(String value,Field field) throws ConverterException{
		if(map.containsKey(field.getType().getName()))
			{
			Converter<String,?> converter = (Converter<String,?>)map.get(field.getType().getName());
				
				return converter.converter(value);
			}
		else
		{
			/*new ConverterNotFoundException(field.getType().getName())
			.printStackTrace();
			*/
			return defaultConverter.converter(value);
			
		}
	}

}
