package cn.bronze.util.excel;
public class StringToDoubleConverter implements Converter<String,Double>{


	@Override
	public Double converter(String value) throws ConverterException {

		
		try {
			if(value==null||value.equals("")){
				value="0";
			}
			value=value.replace(",","");
			return Double.valueOf(value);
		} catch (NumberFormatException e) {
			// 
			throw new ConverterException(Double.class.getName());
		}
	}

}
