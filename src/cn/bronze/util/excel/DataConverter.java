package cn.bronze.util.excel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 将文件解析的数据注入实体类中
 * @author 于海强
 * 2016-6-16  下午7:45:54
 * @version 1.0
 */
public class DataConverter {

	
	/**
	 * 将map中的数据注入到clazz类型的一个对象中，一个map即为一行数据，这个map必须满足，
	 * key为备注信息，value的相应位置的值.所以一个map返回一个对象
	 * @author 于海强
	 * 2016-6-16  下午7:47:59
	* @throws EntityRemarkNotFoundException 
	 * 
	 * 当实体类相关的配置文件，即.properties文件不存在时，抛出此类
	 * @throws EntityIllegalRemarkException 
	 * 
	 * 当实体类的配置文件存在冲突时，例如两个备注相同，抛出此异常
	 * @throws NullPointerException 参数为空抛出
	 */
	public static Object load(Map<String, String> map,Class<?> clazz) 
			throws EntityRemarkNotFoundException
			, EntityIllegalRemarkException 
			,NullPointerException
			{
		try {
			
			Map<String, String> remarkMap = 
					PropUtil.getRemarks(clazz);
			Object object = clazz.newInstance();
			for(Entry<String, String> s:map.entrySet()){
				String remark = s.getKey();
				String value = s.getValue();
				if(remarkMap.containsKey(remark.trim())){
					
					Field field = null;
					try{
						 field = clazz.getDeclaredField(remarkMap.get(remark.trim()));
					}catch(NoSuchFieldException e)
					{
						if(field==null){
							field = clazz.getSuperclass()
									.getDeclaredField(remarkMap.get(remark.trim()));
						}
					}
					
					field.setAccessible(true);
					
					Object valueObject = ConverterFactory.converter(value, field);
					field.set(object, valueObject);
				}
				
			}
			return object;
		} catch (NullPointerException e) {
			// 
			e.printStackTrace();
			throw e;
		} catch (EntityRemarkNotFoundException e) {
			
			e.printStackTrace();
			throw e;
		} catch (EntityIllegalRemarkException e) {
			
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			// 
			e.printStackTrace();
			
		}
		//return null;
		return null;
		
	}
	
	
	/**
	 * 将一个list集合里的实体类全部转化成Map<String,String>形式
	 * 其中key为与field的备注，value为对应的值
	 * 
	 * @author 于海强
	 * 
	 * 2016-6-16  下午8:47:06
	 * 
	 * @throws EntityRemarkNotFoundException 
	 * 
	 * 当实体类相关的配置文件，即.properties文件不存在时，抛出此类
	 * @throws EntityIllegalRemarkException 
	 * 
	 * 当实体类的配置文件存在冲突时，例如两个备注相同，抛出此异常
	 */
	public static List<Map<String, String>> unload(List<?> list,Class<?> clazz) throws 
	EntityRemarkNotFoundException, EntityIllegalRemarkException{
			List<Map<String, String>> resultList = 
					new ArrayList<Map<String, String>>(list.size());
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object object = (Object) iterator.next();
				try {
					
					Map<String,String> resultMap = 
							new LinkedHashMap<String,String>();
					Map<String,String> map = PropUtil.getRemarks(clazz);
					for(Entry<String, String> entry :map.entrySet()){
						//System.out.println(entry.getKey());
						String fieldString =entry.getValue();
						Field field = null;
						try{
							 field = clazz.getDeclaredField(fieldString);
						}catch(NoSuchFieldException e)
						{
							if(field==null){
								field = clazz.getSuperclass()
										.getDeclaredField(fieldString);
							}
						}
						
						
						field.setAccessible(true);
						Object value = field.get(object);
						String valueString = null;
						if(value!=null){
							valueString = ConverterFactory.converter(value, field);
						}
						
						resultMap.put(entry.getKey(), valueString);
						
					}
					resultList.add(resultMap);
				} catch (NullPointerException e) {
					// 
					e.printStackTrace();
				} catch (EntityRemarkNotFoundException e) {
					
					 throw e;
				} catch (EntityIllegalRemarkException e) {
					
					e.printStackTrace();
					throw e;
				} catch (Exception e) {
					// 
					e.printStackTrace();
				}
			}
			return resultList;
		}

}
