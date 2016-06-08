/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.integration.ldap;

import ilarkesto.auth.AuthenticationFailedException;
import ilarkesto.auth.WrongPasswordException;
import static ilarkesto.core.base.Str.isBlank;
import ilarkesto.core.logging.Log;
import static java.lang.System.out;
import java.util.Hashtable;
import javax.naming.AuthenticationException;
import static javax.naming.Context.INITIAL_CONTEXT_FACTORY;
import static javax.naming.Context.PROVIDER_URL;
import static javax.naming.Context.SECURITY_AUTHENTICATION;
import static javax.naming.Context.SECURITY_CREDENTIALS;
import static javax.naming.Context.SECURITY_PRINCIPAL;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import static javax.naming.directory.SearchControls.SUBTREE_SCOPE;
import javax.naming.directory.SearchResult;

public class Ldap {

	private static final Log log = Log.get(Ldap.class);

	public static void main(String[] args) {
		out.println(authenticateUserGetEmail("ldap://adcsv10:389/", "bind user", "bind password", "base dn",
			"user filter", "user", "password"));
	}

	public static String authenticateUserGetEmail(String url, String bindUser, String bindPassword, String baseDn,
			String userFilterRegex, String user, String password) throws AuthenticationFailedException {
		log.info("LDAP authentication for ", user, "on", url);
		NamingEnumeration<SearchResult> searchResultEnum;
		try {
			DirContext ctx = createDirContext(url, bindUser, bindPassword);
			String filter = userFilterRegex == null ? user : userFilterRegex.replaceAll("%u", user);
			SearchControls cons = new SearchControls(SUBTREE_SCOPE, 0, 0, new String[] { "mail" }, true,
					false);
			searchResultEnum = ctx.search(baseDn, filter, cons);
		} catch (NamingException ex) {
			throw new RuntimeException(ex);
		}
		SearchResult searchResult = searchResultEnum.nextElement();
		if (searchResult == null) {
                        throw new AuthenticationFailedException("User does not exist.");
                }

		try {
			createDirContext(url, searchResult.getName() + "," + baseDn, password);
		} catch (AuthenticationException ex) {
			throw new WrongPasswordException();
		}

		try {
			Attribute mailAttribute = searchResult.getAttributes().get("mail");
			if (mailAttribute != null) {
                                return mailAttribute.get().toString();
                        }
		} catch (NamingException ex) {
			log.info(ex);
			// mail field not handled by ldap tree. can happen on some ldap server or with specific structures
		}
		return null;
	}

	public static DirContext createDirContext(String url, String user, String password) throws AuthenticationException {
		Hashtable <String, String> env = new Hashtable<>();

		env.put(INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		// TODO depends on sun-jdk?

		env.put(PROVIDER_URL, url);

		if (user != null) {
			env.put(SECURITY_AUTHENTICATION, "simple");
			env.put(SECURITY_PRINCIPAL, user);
			env.put(SECURITY_CREDENTIALS, password);
		}

		log.debug("Connecting LDAP:", url);
		try {
			return new InitialDirContext(env);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (NamingException ex) {
			StringBuilder sb = new StringBuilder();
			sb.append("Creating InitialDirContext failed.");
			sb.append("\n    URL: ").append(url);
			String explanation = ex.getExplanation();
			if (!isBlank(explanation)) {
                                sb.append("\n    explanation: ").append(explanation);
                        }
			Name remainingName = ex.getRemainingName();
			if (remainingName != null) {
                                sb.append("\n    remaining name: ").append(remainingName.toString());
                        }
			Name resolvedName = ex.getResolvedName();
			if (resolvedName != null) {
                                sb.append("\n    resolved name: ").append(resolvedName.toString());
                        }
			Object resolvedObj = ex.getResolvedObj();
			if (resolvedObj != null) {
                                sb.append("\n    resolved object: ").append(resolvedObj.toString());
                        }
			sb.append("\n  Exception:");
			throw new RuntimeException(sb.toString(), ex);
		}
	}
}
