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
package scrum.server.css;

import ilarkesto.core.logging.Log;
import ilarkesto.io.DynamicClassLoader;
import ilarkesto.ui.web.CssRenderer;
import ilarkesto.webapp.RequestWrapper;

import java.io.IOException;

import scrum.server.ScrumWebApplication;
import scrum.server.WebSession;
import scrum.server.common.AKunagiServlet;

public class CssServlet extends AKunagiServlet {

	private static final Log LOG = Log.get(CssServlet.class);
	private static final long serialVersionUID = 1;

	private transient final ScreenCssBuilder screenCssBuilder = new ScreenCssBuilder();

	@Override
	protected void onRequest(RequestWrapper<WebSession> req) throws IOException {
		req.setContentTypeCss();
		CssRenderer css = new CssRenderer(req.getWriter());
		CssBuilder builder = getCssBuilder();
		builder.buildCss(css);
		css.flush();
		// LOG.debug(builder);
	}

	private CssBuilder getCssBuilder() {
		if (ScrumWebApplication.get().isDevelopmentMode()) {
			ClassLoader loader = new DynamicClassLoader(getClass().getClassLoader(), ScreenCssBuilder.class.getName());
			Class<? extends CssBuilder> type;
			try {
				type = (Class<? extends CssBuilder>) loader.loadClass(ScreenCssBuilder.class.getName());
				return type.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
				LOG.fatal(ex);
				throw new RuntimeException(ex);
			}
		} else {
			return screenCssBuilder;
		}
	}

}
