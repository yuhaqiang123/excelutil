package cn.bronze.util.excel;
public class IntegerToStringConverter implements Converter<Integer, String> {

	@Override
	public String converter(Integer value) throws ConverterException {

		return value.toString();
	}

	public static void main(String[] args) {

	}

}
