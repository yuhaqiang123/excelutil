package cn.bronze.util.excel;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;


public class PropFactory {

	
	private static Map<String,PropertiesUtil> map = 
			new HashMap<String,PropertiesUtil>();
	
	private static boolean addBundle(String bundle) throws MissingResourceException{
		try {
			
			if(!map.containsKey(bundle)){
				bundle = PropFactory.class.getResource("").getPath()+bundle+".properties";
				PropertiesUtil resourceBundle = 
						new PropertiesUtil(URLDecoder.decode(bundle,"UTF-8"));
				
				if(resourceBundle != null){
					map.put(bundle,resourceBundle);
					return true;
				}
				
			}
			
			
		} catch (MissingResourceException e) {
			throw e;
			
		} catch (UnsupportedEncodingException e) {
			// 
			e.printStackTrace();
		}
		return true;
	}
	
	private static boolean addBundle(Class bundle) throws MissingResourceException{
		try {
			
			if(!map.containsKey(bundle.getName())){
				String bundleString = bundle.getResource("").getPath()+bundle.getSimpleName()+".properties";
				PropertiesUtil resourceBundle = 
						new PropertiesUtil(URLDecoder.decode(bundleString,"UTF-8"));
				
				if(resourceBundle != null){
					map.put(bundle.getName(),resourceBundle);
					return true;
				}
				else{
					return false;
				}
			}
			
			
		} catch (MissingResourceException e) {
			throw e;
			
		} catch (UnsupportedEncodingException e) {
			// 
			e.printStackTrace();
		}
		return true;
	}
	
	public static PropertiesUtil getBundle(Class<?> bundle){
		if(bundle==null){
			throw new NullPointerException();
		}
	
		
		try {
			if(addBundle(bundle)==true){
				return map.get(bundle.getName());
			}else{
				return null;
			}
		} catch (MissingResourceException e) {
			// 
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	public static PropertiesUtil getBundle(String bundle){
		if(bundle==null){
			throw new NullPointerException();
		}
	
		
	
		try {
			if(addBundle(bundle)==true){
				return map.get(bundle);
			}else{
				return null;
			}
		} catch (MissingResourceException e) {
			// 
			e.printStackTrace();
			throw e;
		}
		
		
	}
	

	public static void main(String[] args) {

	}

}
