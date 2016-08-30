package ilarkesto.auth;

import java.util.Properties;

/**
 *
 *
 */
public class PropertiesLoginDataProvider implements LoginDataProvider {

	private Properties properties;
	private String prefix;

    /**
     *
     * @param properties
     * @param keysPrefix
     */
    public PropertiesLoginDataProvider(Properties properties, String keysPrefix) {
		super();
		this.properties = properties;
		this.prefix = keysPrefix;
	}

    /**
     *
     * @param properties
     */
    public PropertiesLoginDataProvider(Properties properties) {
		this(properties, "");
	}

    /**
     *
     * @return
     */
    @Override
	public LoginData getLoginData() {
		return new LoginData(properties.getProperty(prefix + "login"), properties.getProperty(prefix + "password"));
	}

    /**
     *
     * @param ld
     */
    public void setLoginData(LoginData ld) {
		properties.setProperty(prefix + "login", ld.getLogin());
		properties.setProperty(prefix + "password", ld.getPassword());
	}

}
