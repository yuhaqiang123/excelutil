package cn.bronze.util.excel;

import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;


public class PropUtil {

	public static void main(String[] args) {

	}
	
	
	private static Map<String, Map<String, String>> fieldAndRemarkMap = 
			new LinkedHashMap<String,Map<String, String>>();
	private static Map<String, Map<String, String>> fieldMap = 
			new LinkedHashMap<String,Map<String, String>>();
	private static Map<String, Map<String, String>> remarkMap = 
			new LinkedHashMap<String,Map<String, String>>();
	
	
	/**
	 * 判断一个remark是否合法，即在改class的配置文件中是否存在
	 * @param clazz，相关实体类， 
	 * @param remark 要查询的备注
	 * @return true 存在，false 不存在
	 * @author 于海强
	 * 2016-6-18  上午10:06:21
	 */
	public static boolean isIllegal(Class<?> clazz,String remark){
		try {
			Map<String, String> map = getFieldWithRemark(clazz);
			if(map.containsKey(remark)==true){
				return true;
			}else{
				return false;
			}
			
		} catch (NullPointerException e) {
			throw e;
			
		} catch (EntityRemarkNotFoundException e) {
			
			e.printStackTrace();
		} catch (EntityIllegalRemarkException e) {
			// 
			e.printStackTrace();
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * 获取field为key,remark为value 的map
	 * @param clazz 相关实体类
	 * @return 返回field为key,remark为value 的map
	 * @throws NullPointerException 当参数为空时
	 * @throws EntityRemarkNotFoundException 当相关实体类不存在时
	 * @throws EntityIllegalRemarkException 当相关实体类的文件不合法，存在冲突时
	 * @throws Exception 其他异常信息
	 * 
	 */
	public static Map<String, String> getFields(Class<?> clazz) throws 
	NullPointerException
	, EntityRemarkNotFoundException
	, EntityIllegalRemarkException
	, Exception{
		getFieldWithRemark(clazz);
		return fieldMap.get(clazz);
	}
	
	
	/**
	 * 获取remark为key, field为value的map
	 * 
	 * @return 获取remark为key, field为value的map;
	 * 2016-6-18  上午10:11:30
	 * @author 于海强
	 * 
	 * @throws NullPointerException 当参数为空时
	 * @throws EntityRemarkNotFoundException 当相关实体类不存在时
	 * @throws EntityIllegalRemarkException 当相关实体类的文件不合法，存在冲突时
	 * @throws Exception 其他异常信息
	 */
	public  static Map<String, String> getRemarks(Class<?> clazz) throws 
	NullPointerException
	, EntityRemarkNotFoundException
	, EntityIllegalRemarkException
	, Exception{
		getFieldWithRemark(clazz);
		return remarkMap.get(clazz.getName());
	}
	
	
	/**
	 * * 获取remark为key, field为value的map
	 *   以及remark为value, field为key的map合并后的map
	 * 
	 * @return 获取remark为key, field为value的map
	 *   以及remark为value, field为key的map合并后的map
	 * 2016-6-18  上午10:11:30
	 * @author 于海强
	 * 
	 * @throws NullPointerException 当参数为空时
	 * @throws EntityRemarkNotFoundException 当相关实体类不存在时
	 * @throws EntityIllegalRemarkException 当相关实体类的文件不合法，存在冲突时
	 * @throws Exception 其他异常信息
	 * 
	 */
	public static Map<String, String> getFieldWithRemark(Class<?> clazz) throws 
		NullPointerException,
		EntityRemarkNotFoundException,
		EntityIllegalRemarkException,
		Exception
	{
		
		try {
			if(fieldAndRemarkMap.containsKey(clazz.getName())){
				
				return fieldAndRemarkMap.get(clazz.getName());
			}
			
			PropertiesUtil bundle = 
					PropFactory.getBundle(clazz);
			//bundle.
			
			//clazz.getFields();
			List<Object> keys = bundle.getKeyList();
			Map<String,String> map = new LinkedHashMap<String,String>();
			Map<String,String> fields = new LinkedHashMap<String,String>();
			Map<String,String> remarks = new LinkedHashMap<String,String>();
			for (Object object : keys) {
				String key = object.toString();
				String value = bundle.getProperty(key);
				/*char firstChar = key.charAt(0);
				
				Method setMethod = clazz.getMethod("set"+
						String.valueOf(firstChar).toUpperCase()
						,clazz.getField(key).getType());*/
				try {
					if(map.put(key, value)!=null){
						throw new EntityIllegalRemarkException(key,value);
					}
					if(map.put(value, key)!=null){
						throw new EntityIllegalRemarkException(key,value);
					}
					fields.put(key, value);
					remarks.put(value, key);
					
				} catch (IllegalArgumentException e) {
					
				}
				
			}
			fieldAndRemarkMap.put(clazz.getName(), map);
			fieldMap.put(clazz.getName(), fields);
			remarkMap.put(clazz.getName(), remarks);
			
			return map;
		}
			catch (NullPointerException e) {
					throw e;
				
			} 
		
			catch (MissingResourceException e) {
					throw new EntityRemarkNotFoundException();
			}
			catch (Exception e) {
				
					throw new Exception();
				
			}
		
		
		
	}
}
