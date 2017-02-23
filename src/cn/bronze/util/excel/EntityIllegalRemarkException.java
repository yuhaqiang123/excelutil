package cn.bronze.util.excel;


public class EntityIllegalRemarkException extends Exception {

	private static final long serialVersionUID = 5459901904805036900L;

	public static void main(String[] args) {

	}
	private String field;
	private String remark;
	EntityIllegalRemarkException(String field,String remark){
		this.field = field;
		this.remark = remark;
	}
	@Override
	public String getMessage() {
		
		return "实体类字段"+field+"匹配的备注"+remark+"已经使用，不可重复使用";
	}

}
