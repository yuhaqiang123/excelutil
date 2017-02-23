package cn.bronze.util.excel;
public class ConverterException extends Exception{

	private static final long serialVersionUID = 6077599386467389828L;

	private String type;
	public ConverterException(String typeName){
		this.type = typeName;
	}

	@Override
	public String getMessage() {
		
		return "由输入文件数据类型向Java类型"+type+"转换时出错";
	}

}
