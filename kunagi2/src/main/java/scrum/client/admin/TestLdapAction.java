
package scrum.client.admin;

import ilarkesto.core.base.Str;

/**
 *
 * @author erik
 */
public class TestLdapAction extends GTestLdapAction {

	@Override
	public String getLabel() {
		return "Test LDAP";
	}

	@Override
	public boolean isExecutable() {
		SystemConfig config = getDao().getSystemConfig();
		return !Str.isBlank(config.getLdapUrl());
	}

	@Override
	protected void onExecute() {
		new TestLdapServiceCall().execute();
	}

}