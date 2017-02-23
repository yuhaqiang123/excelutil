package cn.bronze.util.excel;
public class ResourceHasClosedException extends Exception{

	private static final long serialVersionUID = -8603869543766707543L;

	public static void main(String[] args) {

	}

	@Override
	public String getMessage() {
		
		return "资源已经关闭";
	}

}
