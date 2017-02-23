package cn.bronze.util.excel;


/**
 * 当没找到实体类的备注信息时抛出此异常
 * @author 于海强
 * 2016-6-16  上午10:40:00
 */
public class EntityRemarkNotFoundException extends Exception {

	private static final long serialVersionUID = 7915967109022646021L;

	@Override
	public String getMessage() {
		
		return "没有找到相关实体类的备注信息";
	}
	
	public static void main(String[] args) {
			new  EntityRemarkNotFoundException().printStackTrace();
	}

}
