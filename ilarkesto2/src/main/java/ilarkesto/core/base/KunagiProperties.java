package ilarkesto.core.base;


import static ilarkesto.core.logging.ClientLog.WARN;
import com.google.gwt.user.client.rpc.IsSerializable;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author erik
 */
public class KunagiProperties implements Serializable, IsSerializable {

    private static final long serialVersionUID = 1L;

    java.util.Map<String, Object> properties = new HashMap<>();

    public void Properties() {
    }

    public String getId() {
        return (String) properties.get("id");
    }
    
    public String getType() {
        return (String) properties.get("@type");
    }
    
    public Object getValue(String id) {
        return properties.get(id);
    }
    
    public Set<Map.Entry<String, Object>> getEntrySet() {
        return properties.entrySet();
    }

    public java.util.Map<String, Object> get() {
        return properties;
    }
    
    public void putValue(String key, Object value) {
        if (null == key) {
            throw new RuntimeException("Cannot put value, the key == null");
        }
        if (null == value) {
            WARN("Putting a value of null for key " + key);
        }
        properties.put(key, value);
    }
    
    public boolean containsKey(String key) {
        return properties.containsKey(key);
    }
    
    public void remove(String key) {
        properties.remove(key);
    }
}
