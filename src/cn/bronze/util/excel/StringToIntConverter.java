package cn.bronze.util.excel;
public class StringToIntConverter implements Converter<String,Integer>{

	
	@Override
	public Integer converter(String value) throws ConverterException {
		
		
		try {
			if(value==null||value.equals("")){
				return null;
			}
			return Integer.valueOf(value);
		} catch (NumberFormatException e) {
			
			throw new ConverterException(Integer.class.getName());
		}
	}

}
