package cn.bronze.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import cn.bronzeware.test.entity.Asset;




/**
 * 资源读写类，目前仅仅支持读写excel
 * @author 于海强
 * 2016-6-18  上午9:12:46
 */
public class ResourceContext<T> {

	private ExcelReader excelReader ;
	private ExcelWriter excelWriter;
	private Class<T> clazz;
	private T t;
	private File file;
	private InputStream fileInputStream;
	private boolean  isClosed = false;
	
	
	
	
	
	/**
	 * @param clazz 与资源对应的实体类的Class类
	 * @exception 当参数为null时，抛出此异常
	 * 2016-6-18  上午9:14:33
	 * @author 于海强
	 */
	public ResourceContext(Class<T> clazz)
			throws ParamCanNotBeNullException, FileNotFoundException{
		if(clazz==null){
			throw new ParamCanNotBeNullException("clazz");
		}
		this.clazz = clazz;
	}
	
	/**
	 * 当前context是否已经关闭，已经关闭后，则不能调用此类的方法，调用
	 * 则抛出 {@link ResourceHasClosedException} 
	 * @return true 为已经关闭，false，为未关闭
	 * 
	 */
	private boolean isClosed(){
		return isClosed;
	}
	 
	
	/**
	 * 
	 * 执行任何与读相关的方法之前，都需要设置要读取的文件
	 * @param file 要读取的文件
	 * @throws @exception {@link FileNotFoundException} 当文件没有找到时，抛出此异常
	 * @throws  @exception {@link ResourceHasClosedException} 当资源已经关闭抛出此异常
	 * @throws  @exception {@link ParamCanNotBeNullException} 当参数为空时，抛出异常
	 */
	public void setReadFile(File file) throws 
	FileNotFoundException
	, ResourceHasClosedException
	, ParamCanNotBeNullException{
		if(isClosed()){
			throw new ResourceHasClosedException();
		}
		if(file==null){
			throw new  ParamCanNotBeNullException("file");
		}
		if(fileInputStream!=null){
			try {
				fileInputStream.close();
			} catch (IOException e) {
				//
				e.printStackTrace();
			}
		}
		
		this.file = file;
		this.fileInputStream = new FileInputStream(file);
		excelReader = new ExcelReader(fileInputStream);
	}
	
	
	/**
	 * 写入操作，目前的写入操作仅仅支持这一种
	 * @param list，即你要传入的实体类的集合
	 * @param writeFile 即要写入的文件
	 * @throws ParamCanNotBeNullException 
	 * 
	 */
	public boolean write(List<?> list,File writeFile) 
			 throws ResourceHasClosedException
			 , ParamCanNotBeNullException{
		return write(list,null,writeFile);
		
	}
	
	public boolean write(List<?> list,List<String> tailList,File writeFile) 
			 throws ResourceHasClosedException
			 , ParamCanNotBeNullException{
		if(isClosed()==true){
			throw new ResourceHasClosedException();
		}
		
		if(writeFile==null||list==null){
			throw new ParamCanNotBeNullException("");
		}
		
		try {
			excelWriter = new ExcelWriter(writeFile);
			List<Map<String, String>> dataList = DataConverter.unload(list, clazz);
			try {
				excelWriter.write(dataList,tailList);
				return true;
			} catch (ParamCanNotBeNullException e) {
				
				e.printStackTrace();
			}
		} catch (Exception e) {
			
		}
		
		return false;
		
	}
	
	
	/**
	 * <h1>判断文件标题是否正确，如果正确返回的map为null。如果有错误
	 * 返回的map中，key为remark，value为相关的错误信息</h1> <br>
	 * 例如返回的map中 key为 资产代码  value: {@link  Constant} 下的{@link REMARK_FILE_NOT_FOUND_THIS_FIELD }
	 *  <br> 代表资产代码在excel中出现了，而在实体类配置文件中没有出现
	 * <br>
	 * <br>
	 * <br>例如返回的map中key为资产名称 value为{@link  Constant} 下的{@link INPUT_FILE_NOT_FOUNT_THIS_FIELD }
	 * <br>代表 资产名称 在实体类配置文件中出现，但是在excel文件中没有出现
	 * <br>
	 * throws EntityRemarkNotFoundException 实体类备注信息没有找到
	 * <br>
	 * throws FileNotFoundException 文件没有找到
	 * throws ResourceHasClosedException 资源已经关闭
	 */
	public Map<String,String> isTitleIllegal() throws 
	EntityRemarkNotFoundException
	, FileNotFoundException
	, ResourceHasClosedException{
		if(isClosed()){
			throw new ResourceHasClosedException();
		}
		
		if(file==null){
			throw new FileNotFoundException("没有输入文件");
		}
		
		
		Map<String, String> result = new HashMap<String, String>();
		String[] titles = null;
		try {
			titles = excelReader.getTitles();
		} catch (ExcelIllegalException e1) {
			result.put("formaterror", e1.getMessage());
			e1.printStackTrace();
		}
		try {
			Map<String, String> map = PropUtil.getRemarks(this.clazz);
			
			//遍历备注文件中的信息，将结果返回在result,中，其中value值为
			//Constant.REMARK_FILE_NOT_FOUND_THIS_FIELD 
			for (int i = 0; i < titles.length; i++) {
				String string = titles[i];
				string=string.trim();
				
				if(!map.containsKey(string)){
					result.put(string, Constant.REMARK_FILE_NOT_FOUND_THIS_FIELD);					
				}
			}
			
			//遍历备注文件中的信息，将结果返回在result,中，其中value值为
			//Constant.INPUT_FILE_NOT_FOUNT_THIS_FIELD 
			for (Entry<String, String> s : map.entrySet()) {
				boolean isHave = false;
				for (String string : titles) {
					
					if(s.getKey().equals(string.trim())){
						
						isHave = true;
						
						break;
					}
				}
				
				if(isHave==false){
					result.put(s.getKey()
							, Constant.INPUT_FILE_NOT_FOUNT_THIS_FIELD);
				}
			}
			if(map.size()==0){
				result = null;
			}
			
		} catch (NullPointerException e) {
			// 
			e.printStackTrace();
		} catch (EntityRemarkNotFoundException e) {
			// 
			e.printStackTrace();
			throw e;
		} catch (EntityIllegalRemarkException e) {
			// 
			e.printStackTrace();
		} catch (Exception e) {
			// 
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * <h1>获取相关类的remark信息,以及value值</h1>
	 * <br>
	 *  例如list中第一项 即代表excel文件中的一行数据<br>
	 *  第一行的map中，key为remark也就是excel文件的标题，例如map中的第一项的key就是第一列的标题
	 *  第一列的value就是相对应列的单元格的内容，内容为String类型，使用时需要转换为java类型
	 * @throws ResourceHasClosedException 资源已经关闭
	 * @throws FileNotFoundException  没有设置要读取的文件
	 */
	public List<Map<String, String>> getRemarkwithValue() 
			throws ResourceHasClosedException, FileNotFoundException{
		if(isClosed()==true){
			throw new ResourceHasClosedException();
		}
		if(file==null){
			throw new FileNotFoundException("没有设置输入文件");
		}
		
		List<Map<String, String>> list = null;
		list = excelReader.getRemarkWithValue(clazz);
		return list;
	}
	
	
	/**
	 * @author 于海强
	 * 2016-6-18  上午10:57:01
	 * 返回 从文件中读取的数据，将其包装成 实体类
	 * @throws FileNotFoundException 没有设置要读取的文件
	 * @throws NullPointerException  文件为空，获取其他异常
	 * @throws EntityRemarkNotFoundException 实体类相关的配置文件没有找到
	 * @throws EntityIllegalRemarkException  实体类相关的配置文件存在错误
	 * 
	 */
	public List<T> getEntityList() throws ResourceHasClosedException,
	NullPointerException
	, EntityRemarkNotFoundException
	, EntityIllegalRemarkException
	, FileNotFoundException{
		if(isClosed()){
			throw new ResourceHasClosedException();
		}
		
		if(file==null){
			throw new FileNotFoundException("没有设置输入文件");
		}
		
		
		List<Map<String, String>> list = 
				excelReader.getRemarkWithValue(clazz);
		List<T> entities = new ArrayList<T>(list.size());
		for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
			Object object = DataConverter.load(map, clazz);
			entities.add((T)object);
		}
		return entities;
	}
	
	
	/**
	 * 主要作用是用来测试，测试标题是否输入正确，如果出错，打印错误信息
	 * 同时打印从输入文件中读取的数据
	 * 2016-6-18  上午11:35:25
	 * @author 于海强
	 * @throws ResourceHasClosedException 
	 */
	@Deprecated
	public void getRemark() throws ResourceHasClosedException{
		if(isClosed()){
			throw new ResourceHasClosedException();
		}
		
		try {
			Map<String, String> resultMap = isTitleIllegal();
			for(Entry<String, String> s:resultMap.entrySet()){
				System.out.println(s.getKey()+" :"+s.getValue());
				
			}
		} catch (EntityRemarkNotFoundException e) {
			// 
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// 
			e.printStackTrace();
		} catch (ResourceHasClosedException e) {
			// 
			e.printStackTrace();
		}
		
		List<Map<String, String>> list = excelReader.getRemarkWithValue(clazz);
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Map<String, String> map = (Map<String, String>) iterator.next();
			for (Entry<String, String> s : map.entrySet()) {
				System.out.print(s.getKey()+":"+s.getValue()+"  ");
			}
			System.out.println();
		}
	}
	
	/**
	 * 关闭文件
	 * @throws ResourceHasClosedException 资源已经关闭
	 */
	public void close() throws ResourceHasClosedException{
		if(isClosed==false){

			try {
				if(fileInputStream!=null){
					this.fileInputStream.close();
				}
				isClosed = true;
			} catch (IOException e) {
				// 
				e.printStackTrace();
			}
		
		}else{
			throw new ResourceHasClosedException();
		}
			
	}
	
	public static void main(String[] args) {
		/**
		 * 要读取的文件
		 */
		File file = new File("E://编程//MyEclipse workspace//taxoffice//资产信息.xls");
		
		try {
			
			//指定与相关的class
			ResourceContext context = new ResourceContext<>(Asset.class);
			//设置要读取的文件
			context.setReadFile(file);
			
			//测试文件
			context.getRemark();
			/**
			 * context.getEntityList 获取转换后的实体类list
			 */
			@SuppressWarnings("unchecked")
			List<Asset> list = (context.getEntityList());
			
			if(list==null){
				System.out.println("读取文件出错");
				return;
			}
			//在这之前最好检查一下list是否为空
			
			/**
			 * 打印list,当然你也可以把list存入数据库，注意这个list是ArrayList，支持随机访问，你可以通过随机访问遍历
			 * 即直接list.get(index),获取，也可以通过iterator循环迭代器迭代循环
			 */
			
			for (Iterator<Asset> iterator = list.iterator(); iterator.hasNext();) {
				Asset asset =  iterator.next();
				System.out.println(asset.toString());
			}
			
			
			/*
			 * 
			 * 这里演示了从list写入file的过程,返回值标记是否正确，一般情况下
			 * 出错自然会抛出异常的，return false的情况少 你需要处理就是出现异常如何捕获
			 * 及如何处理
			 */
			List<String> tailList = new ArrayList<String>(4);
			/*for (int i = 0;i<4;i++) {
				tailList.add("于海强");
				
			}*/
			
			boolean result = context.write(list,tailList, new File("e://资产信息于海强1.xls"));

			
			context.close();
			/**
			 * 关闭完了调用会出错
			 */
			//context.getRemark();
		} catch (FileNotFoundException e) {
			/**
			 *  这个异常时编码异常，即你传入的File是错误的，典型当指定写入的文件出现中文时，可能
			 * 找不到File，报这个错误，你需要看你的代码是不是错了
			 */
			e.printStackTrace();
		} catch (ParamCanNotBeNullException e) {
			// 这个异常也是编码异常，你需要检查你的参数传入的都是否非空
			e.printStackTrace();
		} catch (ResourceHasClosedException e) {
			/**
			 * 当resourcecontext已经调用close后，你在调用，则会出错
			 */
			e.printStackTrace();
		} catch (NullPointerException e) {
			// 空指针也是编码错误
			e.printStackTrace();
		} catch (EntityRemarkNotFoundException e) {
			/*
			 * 
			 *  检查cn.bronze.entities下对应的实体，有没有相关的配置文件
			 *  例如Asset 对应Asset.properties
			 */
			
			e.printStackTrace();
		} catch (EntityIllegalRemarkException e) {
			// 检查相关实体配置文件 是否正确，有没有出现相同的remark
			e.printStackTrace();
		}
	}

}
