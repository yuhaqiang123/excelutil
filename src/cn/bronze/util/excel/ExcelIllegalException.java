package cn.bronze.util.excel;
public class ExcelIllegalException extends Exception{

	private static final long serialVersionUID = 3927505963761490030L;
	public static void main(String[] args) {

	}

	public ExcelIllegalException(String message){
		this.message = message;
	}
	
	private String message = "";
	@Override
	public String getMessage() {
		
		return "Excel格式有误:"+message;
		
	}

}
