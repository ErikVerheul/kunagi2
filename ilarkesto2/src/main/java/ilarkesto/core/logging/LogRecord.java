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
package ilarkesto.core.logging;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;
import ilarkesto.core.base.Str;
import static ilarkesto.core.base.Str.cutLeft;
import static ilarkesto.core.base.Str.fillUpRight;
import static ilarkesto.core.base.Str.format;
import static ilarkesto.core.base.Str.getStackTrace;
import ilarkesto.core.base.Utl;
import ilarkesto.core.logging.Log.Level;
import static ilarkesto.core.logging.Log.Level.DEBUG;
import static ilarkesto.core.logging.Log.Level.INFO;
import java.util.Date;

public class LogRecord {

	// private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public final long time;
	public final String name;
	public final Level level;
	public final Object[] parameters;
	public String context;

        @SuppressWarnings(value = "EI_EXPOSE_REP2", justification = "Compromise security or other important properties not an issue here")
	public LogRecord(long time, String name, Level level, Object... parameters) {
		super();
		this.time = time;
		this.name = name;
		this.level = level;
		this.parameters = parameters;
	}

	@Override
	public String toString() {
		String nameFormated = cutLeft(name, 20);
		nameFormated = fillUpRight(nameFormated, " ", 20);

		StringBuilder sb = new StringBuilder();

		// time
		sb.append(new Date(time)).append(" ");

		// level
		if ((level != DEBUG) && (level != INFO)) {
                        sb.append("\n    ");
                }
		if (level != DEBUG) {
                        sb.append(level);
                }

		// logger
		sb.append(" ").append(nameFormated);

		// text
		sb.append(fillUpRight(getParametersAsString(), " ", 100));

		// context
		if (context != null) {
                        sb.append(" | ").append(context);
                }

		// extra line for high prio logs
		if ((level != DEBUG) && (level != INFO)) {
                        sb.append('\n');
                }

		return sb.toString();
	}

	public String getParametersAsString() {
		StringBuilder textSb = new StringBuilder();
		if (parameters == null) {
			textSb.append(" <null>");
		} else {
			for (Object parameter : parameters) {
				textSb.append(' ');
				if (parameter instanceof Throwable) {
					textSb.append("\n").append(getStackTrace((Throwable) parameter));
				} else {
					textSb.append(format(parameter));
				}
			}
		}
		String text = textSb.toString();
		return text;
	}

	private transient int hashcode;

	@Override
	public int hashCode() {
		if (hashcode == 0) {
                        hashcode = Utl.hashCode(level, name, parameters);
                }
		return hashcode;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LogRecord)) {
                        return false;
                }
		LogRecord other = (LogRecord) obj;
		if (!level.equals(other.level)) {
                        return false;
                }
		if (name == null ? other.name != null : !name.equals(other.name)) {
                        return false;
                }
		return Utl.equals(parameters, other.parameters);
	}

}
