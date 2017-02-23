package cn.bronze.util.excel;

import java.util.List;

public class Test {

	public static void main(String[] args) {

		PropertiesUtil boudle = new PropertiesUtil(cn.bronzeware.test.entity.Asset.class);
		System.out.println(cn.bronzeware.test.entity.Asset.class.getName());
		List<Object> keys = boudle.getKeyList();
		for (Object object : keys) {
			System.out.println(object);
		}
	}

}
