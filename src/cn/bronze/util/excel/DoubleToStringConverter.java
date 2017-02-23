package cn.bronze.util.excel;
public class DoubleToStringConverter implements Converter<Double, String> {

	@Override
	public String converter(Double value) throws ConverterException {

		return value.toString();
	}

	public static void main(String[] args) {

	}

}
