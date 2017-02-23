package cn.bronze.util.excel;
public class BooleanToStringConverter implements Converter<Boolean, String> {

	@Override
	public String converter(Boolean value) throws ConverterException {

		if(value){
			return Constant.IS_BEGIN_YES;
		}else{
			return Constant.IS_BEGIN_NO;
		}
	}

	public static void main(String[] args) {

	}

}
