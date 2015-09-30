
package free.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;


/**
 * @ClassName SerializeUtil
 * @Description 序列化工具类
 * @author guowei.wang
 * @date 2015-8-3
 */
public class SerializeUtil {
	
	private static final Logger logger = Logger.getLogger(SerializeUtil.class.getName());
	
	/**
	 * 序列化
	 * @Title: serialize 
	 * @Description: 将对象序列化成字节数组
	 * @param object
	 * @return
	 * @Date 2015-8-3
	 */
    public static byte[] serialize(Object object) {
    	byte[] bytes = null;
    	
        try {
        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	ObjectOutputStream oos = new ObjectOutputStream(baos);
        	
            oos.writeObject(object);
            bytes = baos.toByteArray();
        } catch (NotSerializableException e) {
        	throw new RuntimeException(object.getClass() + " 未实现可序列化接口");
        } catch (Exception e) {
        	logger.warning("对象序列化失败");
        }
        
        return bytes;
    }
    
    /**
     * 反序列化
     * @Title: unserialize 
     * @Description: 将字节数组反序列化成对象
     * @param bytes
     * @param type
     * @return
     * @Date 2015-8-3
     */
    public static <T> T unserialize(byte[] bytes, Class<T> type) {
    	T instance = null;
        try {
        	ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            
            instance = type.cast(ois.readObject());
        } catch (NotSerializableException e) {
        	throw new RuntimeException(type + " 未实现可序列化接口");
        } catch (Exception e) {
        	logger.warning("对象反序列化失败");
        }
        
        return instance;
    }
}
