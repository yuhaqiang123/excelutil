package cn.bronze.util.excel;


/**
 * 默认转换器，目前对要转换的字符串不作处理】
 * @author 于海强
 * 2016-6-16  下午7:49:47
 * @version 1.0
 */
public class DefaultConverter implements Converter<String,String>{

	@Override
	public String converter(String value) throws ConverterException {
		
		return value;
	}

	

}
