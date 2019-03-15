package domain.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * <p>接口管理基类:</p>
 * <p></p>
 * @author yucheng
 * @moduleId com.yucheng.cmis.retailloan.interfacemanagement
 * @createDate 2014-8-25 11:08:45
 */
public class JAXBUtils  {
	
	/**
	 * JavaBean转换成xml
	 * 默认编码UTF-8
	 * @param obj
	 * @param writer
	 * @return 
	 */
	public static String convertToXml(Object obj) {
		return convertToXml(obj, "UTF-8");
	}

	/**
	 * JavaBean转换成xml
	 * @param obj
	 * @param encoding 
	 * @return 
	 */
	public static String convertToXml(Object obj, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			//Marshaller.JAXB_FORMATTED_OUTPUT 决定是否在转换成xml时同时进行格式化（即按标签自动换行，否则即是一行的xml）
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//Marshaller.JAXB_ENCODING xml的编码方式
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * xml转换成JavaBean
	 * @param xml
	 * @param c
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(String xml, Class<T> c) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}
	
	
}

/**
 * 由模板standard.java.vm自动生成
 */