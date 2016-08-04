package ilarkesto.core.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author erik
 */
public class KunagiProperties implements Serializable {

    private static final long serialVersionUID = 1L;
    //Do not make properties final as it will not be serialized.
    private java.util.HashMap<String, Object> props = new HashMap<>();
    // dummys required for gwt-serialization
    private int dummyI;
    private Integer dummyInteger;
    private float dummyF;
    private Float dummyFloat;

    public void KunagiProperties() {

    }

    public String getId() {
        return (String) props.get("id");
    }

    public String getType() {
        return (String) props.get("@type");
    }

    public Object getValue(String id) {
        return props.get(id);
    }

    public Set<HashMap.Entry<String, Object>> getEntrySet() {
        return props.entrySet();
    }

    public void putValue(String key, Object value) {
        if (null == key) {
            throw new RuntimeException("Cannot put value, the key == null");
        }
        props.put(key, value);
    }

    public boolean containsKey(String key) {
        return props.containsKey(key);
    }

    public void remove(String key) {
        props.remove(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("KunagiProperties Id=");
        sb.append(getId());
        sb.append(",Type=");
        sb.append(getType());
        sb.append("Number of entities=");
        sb.append(props.size());
        sb.append("\n");
        for (HashMap.Entry<String, Object> entry : props.entrySet()) {
            sb.append("Key=");
            sb.append(entry.getKey());
            sb.append(" ; Value=");
            sb.append(entry.getValue());
            sb.append("\n");
        }
        return sb.toString();
    }

}
