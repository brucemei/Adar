package free.adar.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;
import java.util.logging.Logger;

public class SerializeUtils {
	
	private static final Logger logger = Logger.getLogger(SerializeUtils.class.getName());
	
    public static byte[] serialize(Object object) {
    	Objects.requireNonNull(object);
    	
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
    
    public static <T> T unserialize(byte[] bytes, Class<T> type) {
    	Objects.requireNonNull(bytes);
    	Objects.requireNonNull(type);
    	
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
