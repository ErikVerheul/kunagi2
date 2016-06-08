/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>, Artjom Kochtchi
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option)
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package ilarkesto.locale;

import ilarkesto.base.Cache;
import ilarkesto.base.StrExtend;
import static ilarkesto.base.StrExtend.replaceForHtml;
import static ilarkesto.base.StrExtend.replaceUnicodeCharsWithJavaNotation;
import ilarkesto.core.logging.Log;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.IO.appendLine;
import static ilarkesto.io.IO.loadProperties;
import static ilarkesto.swing.Swing.center;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Locale;
import static java.util.Locale.GERMAN;
import java.util.Properties;
import javax.swing.JFrame;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.showInputDialog;

public final class LearningLocalizer extends Localizer {

	private static final Log LOG = Log.get(LearningLocalizer.class);

	private static final String RESOURCE_BUNDLE = "strings";

	private Properties templates;
	private JFrame frame;

	// --- dependencies ---

	private Locale locale;
	private boolean developmentMode = false;

	public void setDevelopmentMode(boolean value) {
		developmentMode = value;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	// --- ---

	private final Cache<String, MessageFormat> formatsCache = new Cache<>(
			new Cache.Factory<String, MessageFormat>() {

				@Override
				public MessageFormat create(String template) {
					return new MessageFormat(template);
				}

			});

	public String string(Object context, String string, Object... parameters) {
		return string(context.getClass().getName() + "." + string, parameters);
	}

	@Override
	public String string(String key, Object... parameters) {
		if (!developmentMode) {
                        locale = GERMAN;
                }

		if (parameters != null) {
			for (int i = 0; i < parameters.length; i++) {
				if (parameters[i] instanceof Throwable) {
                                        parameters[i] = StrExtend.format(parameters[i]);
                                }
			}
		}

		String template = getTemplate(locale, key);
		if (template == null) {
			StringBuilder sb = new StringBuilder();
			sb.append("@@@");
			sb.append(key);
                        for (Object parameter : parameters) {
                                sb.append(", ").append(parameter);
                        }
			return sb.toString();
		}
		return format(template, parameters);
	}

	private String getTemplate(Locale locale, String key) {
		String template = getTemplates(locale).getProperty(key);
		if (template != null) {
                        return template;
                }
		if (developmentMode) {
			template = learnTemplate(key);
			if (template != null) {
                                return template;
                        }
		} else {
			LOG.error("missing " + locale + "-local string: ", key);
		}
		return template;
	}

	private String format(String template, Object... parameters) {
		if (template.startsWith("<html")) {
			for (int i = 0; i < parameters.length; i++) {
				if (parameters[i] instanceof String) {
					parameters[i] = replaceForHtml((String) parameters[i]);
				}
			}
		}
		MessageFormat f = formatsCache.get(template);
		return f.format(parameters);
	}

	private Properties getTemplates(Locale locale) {
		if (templates == null) {
			String localeSuffix = locale.toString();
			if (localeSuffix.length() > 2) {
                                localeSuffix = localeSuffix.substring(0, 2);
                        }
			String resource = RESOURCE_BUNDLE + "_" + localeSuffix + ".properties";
			ClassLoader classLoader = getClass().getClassLoader();
			LOG.debug("Loading localizer data:", resource, classLoader);
			templates = loadProperties(classLoader.getResource(resource), UTF_8);
		}
		return templates;
	}

	private String learnTemplate(String key) {
		if (frame == null) {
                        frame = new JFrame(getClass().getSimpleName());
                }
		center(frame);
		frame.setVisible(true);
		String template = showInputDialog(frame, key, "Lokalisierung", QUESTION_MESSAGE);
		frame.setVisible(false);
		if (template == null) {
                        return null;
                }
		template = template.trim();
		if (template.length() == 0) {
                        template = null;
                }

		if (template != null) {
			templates.put(key, template);
			String localeSuffix = locale.toString();
			if (localeSuffix.length() > 2) {
                                localeSuffix = localeSuffix.substring(0, 2);
                        }
			try {
				appendLine("src/main/java/" + RESOURCE_BUNDLE + "_" + localeSuffix + ".properties",
					key + "=" + replaceUnicodeCharsWithJavaNotation(template));
			} catch (IOException ex1) {
				throw new RuntimeException(ex1);
			}
		}

		return template;
	}

	@Override
	public Locale getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return locale == null ? getClass().getSimpleName() : locale.toString();
	}
}
