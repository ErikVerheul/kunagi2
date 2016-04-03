package ilarkesto.integration.oauth;

import ilarkesto.auth.LoginData;
import ilarkesto.auth.LoginDataProvider;
import ilarkesto.core.logging.Log;
import ilarkesto.json.JsonObject;
import static java.lang.System.in;
import static java.lang.System.out;
import static java.nio.charset.Charset.defaultCharset;
import java.util.Scanner;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.Api;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import static org.scribe.model.Verb.GET;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

public class OAuth {

	private static final Log log = Log.get(OAuth.class);

	public static JsonObject loadUrlAsJson(OAuthService service, LoginDataProvider accessToken, String url) {
		String json = loadUrlAsString(service, accessToken, url);
		return new JsonObject(json);
	}

	public static String loadUrlAsString(OAuthService service, LoginDataProvider accessToken, String url) {
		Response response = loadUrl(service, accessToken, url);
		return response.getBody();
	}

	public static Response loadUrl(OAuthService service, LoginDataProvider accessToken, String url) {
		log.info("Requesting", url);
		LoginData loginData = accessToken.getLoginData();
		Token token = new Token(loginData.getLogin(), loginData.getPassword());
		OAuthRequest request = new OAuthRequest(GET, url);
		service.signRequest(token, request);
		Response response = request.send();
		int code = response.getCode();
		if (code != 200) {
                        throw new RuntimeException("Loading OAuth URL failed: " + url + "\n" + response.getBody());
                }
		return response;
	}

	public static OAuthService createService(Class<? extends Api> api, LoginDataProvider apiKey, String callbackUri) {
		LoginData loginData = apiKey.getLoginData();
		return createService(api, loginData.getLogin(), loginData.getPassword(), callbackUri);
	}

	public static OAuthService createService(Class<? extends Api> api, String apiKey, String apiSecret,
			String callbackUri) {
		ServiceBuilder builder = new ServiceBuilder();
		builder.provider(api);
		builder.apiKey(apiKey).apiSecret(apiSecret);
		if (callbackUri != null) {
                        builder.callback(callbackUri);
                }
		OAuthService service = builder.build();
		return service;
	}

	public static Token createRequestToken(OAuthService service) {
		return service.getRequestToken();
	}

	public static String getAuthorizationUrl(OAuthService service, Token requestToken) {
		return service.getAuthorizationUrl(requestToken);
	}

	public static LoginData createAccessToken(OAuthService service, Token requestToken, String pin) {
		Verifier verifier = new Verifier(pin);
		Token accessToken = service.getAccessToken(requestToken, verifier);
		return new LoginData(accessToken.getToken(), accessToken.getSecret());
	}

	public static LoginData createAccessTokenByPinFromStdIn(OAuthService service) {
		Token requestToken = createRequestToken(service);
		String url = getAuthorizationUrl(service, requestToken);
		out.println("\n    Authenticate here: " + url);
		out.print("    Input PIN: ");
		String pin = new Scanner(in, defaultCharset().toString()).nextLine();
		out.println();
		return createAccessToken(service, requestToken, pin);
	}
}
