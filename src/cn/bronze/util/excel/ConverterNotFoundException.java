package cn.bronze.util.excel;
public class ConverterNotFoundException extends ConverterException{

	private static final long serialVersionUID = -3370088554937300641L;
	private String name;
	public ConverterNotFoundException(String name){
		super(name);
		this.name = name;
		
	}
	
	@Override
	public String getMessage() {
		
		return "没有与"+name+"找到相关的转换器";
	}

	

}
