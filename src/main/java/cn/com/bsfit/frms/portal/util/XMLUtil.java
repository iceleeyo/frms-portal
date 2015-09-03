package cn.com.bsfit.frms.portal.util;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * POJO和XML之间相互转换
 * 
 * @author hjp
 * 
 * @since 1.0.0
 *
 */
public class XMLUtil {

	/**
	 * XML转化为POJO
	 * 
	 * @param xmlStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T XMLStrToPojo(String xmlStr, Class<T> cla) {
		try {
			JAXBContext context = JAXBContext.newInstance(cla);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.unmarshal(new StringReader(xmlStr));
			return (T) unmarshaller.unmarshal(new StringReader(xmlStr));
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * POJO转化为XML
	 * 
	 * @param obj
	 * @return
	 */
	public static String pojoToXML(Class<?> cla) {
		try {
			JAXBContext context = JAXBContext.newInstance(cla.getClass());
			Marshaller marshaller = context.createMarshaller();
			// 编码格式
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			// 是否格式化生成的XML串
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			// 是否省略XML头声明信息
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, false);
            StringWriter writer = new StringWriter();
            marshaller.marshal(cla, writer);
            return writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 格式化XML
	 * 
	 * @param xml
	 * @param cla
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromXML(String xml, Class<T> cla) {
		try {
			JAXBContext context = JAXBContext.newInstance(cla);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (JAXBException e) {
			e.printStackTrace();
			return null;
		}
	}
}