package ilarkesto.gwt.client;

import com.google.gwt.user.client.rpc.IsSerializable;
import ilarkesto.core.base.Str;
import static ilarkesto.core.base.Str.formatException;
import java.io.Serializable;

public class ErrorWrapper implements Serializable, IsSerializable {

	private String name;
	private String message;

	public ErrorWrapper(String name, String message) {
		super();
		this.name = name;
		this.message = message;
	}

	public ErrorWrapper(Throwable ex) {
		this(ex.getClass().getName(), formatException(ex));
	}

	private ErrorWrapper() {
		this("unknown error", null);
	}

	public String getName() {
		return name;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		if (message == null) {
                        return name;
                }
		return name + ": " + message;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ErrorWrapper)) {
                        return false;
                }
		ErrorWrapper other = (ErrorWrapper) obj;
		return (name == null ? (other.name) == null : name.equals(other.name)) && 
                        (message == null ? other.message == null : message.equals(other.message));
	}

        @Override
        public int hashCode() {
                int hash = 7;
                hash = 29 * hash + (this.name != null ? this.name.hashCode() : 0);
                hash = 29 * hash + (this.message != null ? this.message.hashCode() : 0);
                return hash;
        }

}
