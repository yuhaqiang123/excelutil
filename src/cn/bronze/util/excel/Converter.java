package cn.bronze.util.excel;


/**
 * 当
 */
interface Converter<FROM,TO> {
	public TO converter(FROM value)
			throws ConverterException;
}
