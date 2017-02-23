package cn.bronze.util.excel;
public class StringToBooleanConverter implements Converter<String,Boolean>{

	@Override
	public Boolean converter(String value) throws ConverterException {
		
		try {
			if(value.equals(Constant.IS_BEGIN_YES)){
				return true;
			}
			else if(value.equals(Constant.IS_BEGIN_NO)){
				return false;
			}else{
				return null;
			}
		} catch (Exception e) {
			// 
			throw new ConverterException(Boolean.class.getName());
		}
	}

	
}
