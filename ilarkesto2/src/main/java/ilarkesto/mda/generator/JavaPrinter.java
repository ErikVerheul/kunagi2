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
package ilarkesto.mda.generator;

import static ilarkesto.base.StrExtend.uppercaseFirstLetter;
import static ilarkesto.core.base.Str.concat;
import static ilarkesto.core.logging.ClientLog.DEBUG;
import static ilarkesto.core.logging.ClientLog.INFO;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.IO.writeFileIfChanged;
import ilarkesto.logging.Log;
import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 *
 *
 */
public class JavaPrinter {

	private static final Log LOG = Log.get(JavaPrinter.class);

	private String packageName;
	private String className;

	private final String charset = UTF_8;
	private final StringBuilder sb = new StringBuilder();
	private int depth;
	private boolean lineStart = true;

    /**
     *
     */
    public void commentGenerated() {
		comment("// ----------> GENERATED FILE - DON'T TOUCH! <----------");
		ln();
	}

    /**
     *
     * @param text
     */
    public void comment(String text) {
		ln("// " + text);
	}

    /**
     *
     * @param params
     */
    public void logDebug(String params) {
		DEBUG(params);
	}

    /**
     *
     * @param params
     */
    public void logInfo(String params) {
		INFO(params);
	}

    /**
     *
     * @param var
     * @param value
     */
    public void assignment(String var, String value) {
		statement(var + " = " + value);
	}

    /**
     *
     * @param statement
     */
    public void returnStatement(String statement) {
		statement("return " + statement);
	}

    /**
     *
     * @param statement
     */
    public void statement(String statement) {
		ln(statement + ";");
	}

    /**
     *
     * @param type
     * @param name
     */
    public void protectedField(String type, String name) {
		protectedField(type, name, null);
	}

    /**
     *
     * @param type
     * @param name
     * @param value
     */
    public void protectedField(String type, String name, String value) {
		field("protected", type, name, value);
	}

    /**
     *
     * @param modifiers
     * @param type
     * @param name
     * @param value
     */
    public void field(String modifiers, String type, String name, String value) {
		s(modifiers + " " + type + " " + name);
		if (value != null) {
                        s(" = " + value);
                }
		ln(";");
		ln();
	}

    /**
     *
     * @param type
     * @param name
     */
    public void getter(String type, String name) {
		beginMethod(false, type, "get" + uppercaseFirstLetter(name), null);
		returnStatement(name);
		endMethod();
	}

    /**
     *
     */
    public void annotationOverride() {
		annotation("Override");
	}

    /**
     *
     * @param name
     */
    public void annotation(String name) {
		s("@");
		ln(name);
	}

    /**
     *
     * @param returnStatement
     */
    public void toStringMethod(String returnStatement) {
		annotationOverride();
		beginToStringMethod();
		returnStatement(returnStatement);
		endMethod();
	}

    /**
     *
     * @param name
     * @param parameters
     */
    public void beginProcedure(String name, List<String> parameters) {
		beginMethod(true, "void", name, parameters);
	}

    /**
     *
     */
    public void beginToStringMethod() {
		beginMethod(false, "String", "toString", null);
	}

    /**
     *
     * @param override
     * @param returnType
     * @param name
     * @param parameters
     */
    public void beginMethod(boolean override, String returnType, String name, List<String> parameters) {
                if (override) {
                    ln("@Override");
        }
		s("public " + returnType + " " + name + "(");
		if (parameters != null && !parameters.isEmpty()) {
                        s(concat(parameters, ", "));
                }
		ln(") {");
		in();
	}

    /**
     *
     * @param returnType
     * @param name
     * @param parameters
     */
    public void abstractMethod(String returnType, String name, List<String> parameters) {
		if (returnType == null) {
                        returnType = "void";
                }
		s("public abstract " + returnType + " " + name + "(");
		if (parameters != null && !parameters.isEmpty()) {
                        s(concat(parameters, ", "));
                }
		ln(");");
		ln();
	}

    /**
     *
     * @param returnType
     * @param name
     * @param parameters
     */
    public void interfaceMethod(String returnType, String name, List<String> parameters) {
		if (returnType == null) {
                        returnType = "void";
                }
		s(returnType + " " + name + "(");
		if (parameters != null && !parameters.isEmpty()) {
                        s(concat(parameters, ", "));
                }
		ln(");");
		ln();
	}

    /**
     *
     */
    public void endProcedure() {
		endMethod();
	}

    /**
     *
     */
    public void endMethod() {
		out();
		ln("}");
		ln();
	}

    /**
     *
     * @param name
     * @param interfaces
     */
    public void beginInterface(String name, Collection<String> interfaces) {
		if (className == null) {
                        className = name;
                }
		s("public interface " + name);
		if (interfaces != null && !interfaces.isEmpty()) {
			s(" extends ");
			boolean first = true;
			for (String iface : interfaces) {
				if (first) {
					first = false;
				} else {
					s(", ");
				}
				s(iface);
			}
		}
		ln(" {");
		in();
		ln();
	}

    /**
     *
     * @param condition
     */
    public void beginIf(String condition) {
		ln("if (" + condition + ") {");
		in();
	}

    /**
     *
     */
    public void endIf() {
		out();
		ln("}");
	}

    /**
     *
     * @param lock
     */
    public void beginSynchronized(String lock) {
		ln("synchronized (" + lock + ") {");
		in();
	}

    /**
     *
     */
    public void endSynchronized() {
		out();
		ln("}");
	}

    /**
     *
     */
    public void beginTry() {
		ln("try {");
		in();
	}

    /**
     *
     */
    public void beginCatchRuntimeException() {
		beginCatch("RuntimeException");
	}
        
    /**
     *
     */
    public void catchConversationDoesNotExist() {
		beginCatch("GwtConversationDoesNotExist");
	}

    /**
     *
     * @param exceptionType
     */
    public void beginCatch(String exceptionType) {
		out();
		ln("} catch (" + exceptionType + " ex) {");
		in();
	}

    /**
     *
     */
    public void endCatch() {
		out();
		ln("}");
	}

    /**
     *
     * @param name
     * @param superclassName
     * @param interfaces
     */
    public void beginClass(String name, String superclassName, Collection<String> interfaces) {
		beginClass(false, name, superclassName, interfaces);
	}

    /**
     *
     * @param abstract_
     * @param name
     * @param superclassName
     * @param interfaces
     */
    public void beginClass(boolean abstract_, String name, String superclassName, Collection<String> interfaces) {
		if (className == null) {
                        className = name;
                }
		s("public");
		if (abstract_) {
                        s(" abstract");
                }
		s(" class " + name);
		if (superclassName != null) {
                        s(" extends " + superclassName);
                }
		if (interfaces != null && !interfaces.isEmpty()) {
                        s(" implements " + concat(interfaces, ", "));
                }
		ln(" {");
		in();
		ln();
	}

    /**
     *
     */
    public void endInterface() {
		endClass();
	}

    /**
     *
     */
    public void endClass() {
		out();
		ln("}");
		ln();
	}

    /**
     *
     * @param parameters
     */
    public void beginConstructor(List<String> parameters) {
		beginMethod(false, "", className, parameters);
	}

    /**
     *
     */
    public void endConstructor() {
		out();
		ln("}");
		ln();
	}

    /**
     *
     * @param name
     */
    public void package_(String name) {
		if (packageName == null) {
                        packageName = name;
                }
		ln("package " + name + ";");
		ln();
	}

    /**
     *
     * @param imports
     */
    public void imports(Collection<String> imports) {
		for (String imp : imports) {
			ln("import " + imp + ";");
		}
		ln();
	}

    /**
     *
     * @param s
     */
    public void ln(String s) {
		s(s);
		ln();
	}

    /**
     *
     * @param s
     */
    public void s(String s) {
		if (lineStart) {
			indentation();
			lineStart = false;
		}
		sb.append(s);
	}

    /**
     *
     */
    public void in() {
		if (!lineStart) {
                        throw new IllegalStateException("lineStart == false");
                }
		depth++;
	}

    /**
     *
     */
    public void out() {
		if (depth == 0) {
                        throw new IllegalStateException("depth == 0");
                }
		depth--;
	}

    /**
     *
     */
    public void ln() {
		sb.append("\n");
		lineStart = true;
	}

	private void indentation() {
		for (int i = 0; i < depth; i++) {
			sb.append("    ");
		}
	}

    /**
     *
     * @param basePath
     * @param overwrite
     */
    public void writeToFile(String basePath, boolean overwrite) {
		if (packageName == null) {
                        throw new IllegalStateException("packageName == null");
                }
		if (className == null) {
                        throw new IllegalStateException("className == null");
                }
		String path = basePath + "/" + packageName.replace('.', '/') + "/" + className + ".java";
		File file = new File(path);
		writeToFile(file, overwrite);
	}

    /**
     *
     * @param file
     * @param overwrite
     */
    public void writeToFile(File file, boolean overwrite) {
		if (file.exists()) {
			if (!overwrite) {
				LOG.debug("File already exists:", file.getPath());
				return;
			}
		}

		if (writeFileIfChanged(file, toString(), charset)) {
			LOG.info("File written:", file.getPath());
		} else {
			LOG.debug("File is up to date:", file.getPath());
		}
	}

	@Override
	public String toString() {
		return sb.toString();
	}

}
