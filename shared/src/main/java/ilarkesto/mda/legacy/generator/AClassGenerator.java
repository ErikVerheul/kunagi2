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
package ilarkesto.mda.legacy.generator;

import static ilarkesto.base.StrExtend.uppercaseFirstLetter;
import ilarkesto.core.logging.Log;
import static ilarkesto.io.IO.UTF_8;
import static ilarkesto.io.IO.readFile;
import static ilarkesto.io.IO.writeFile;
import ilarkesto.mda.legacy.model.ParameterModel;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import static java.util.Collections.EMPTY_SET;
import java.util.Set;

public abstract class AClassGenerator {

        private static final Log LOG = Log.get(AClassGenerator.class);

        protected abstract String getName();

        protected abstract String getPackage();

        protected abstract boolean isInterface();

        protected abstract void writeContent();

        private StringWriter stringWriter;
        private PrintWriter out;

        public final void generate() {
                stringWriter = new StringWriter();
                out = new PrintWriter(stringWriter);
                File file = getFile();
                if (file.exists() && !isOverwrite()) {
                        return;
                }
                
                if (isOverwrite()) {
                        for (int i = 0; i < 10; i++) {
                                ln();
                        }
                        ln("// ----------> GENERATED FILE - DON'T TOUCH! <----------");
                        ln();
                        ln("// generator: " + getClass().getName());
                        for (int i = 0; i < 10; i++) {
                                ln();
                        }
                }

                ln("package " + getPackage() + ";");

                ln();
                ln("import java.util.*;");
                for (String im : getImports()) {
                        ln("import " + im + ";");
                }

                ln();
                s("public ");
                if (!isInterface() && isAbstract()) {
                        s("abstract ");
                }
                s(getType() + " " + getName() + getGenericAsString());
                String superclass = getSuperclass();
                if (superclass != null) {
                        ln();
                        s("            extends " + superclass);
                }
                Set<String> superinterfaces = getSuperinterfaces();
                if (superinterfaces != null && superinterfaces.size() > 0) {
                        ln();
                        if (isInterface()) {
                                s("            extends ");
                        } else {
                                s("            implements ");
                        }
                        boolean first = true;
                        for (String superinterface : superinterfaces) {
                                if (first) {
                                        first = false;
                                } else {
                                        s(", ");
                                }
                                s(superinterface);
                        }
                }
                ln(" {");

                for (String declaration : getMethodDeclarations()) {
                        ln();
                        ln("    public abstract " + declaration + ";");
                }

                writeContent();

                ln();
                ln("}");

                out.close();
                String code = stringWriter.toString();
                code = code.trim();
                if (file.exists()) {
                        String previousCode = readFile(file, UTF_8);
                        previousCode = previousCode.trim();
                        if (isSame(code, previousCode)) {
                                // LOG.info("No changes, skipping:", file.getPath());
                                return;
                        }
                }
                LOG.info("Writing:", file.getPath());
                writeFile(file, code, UTF_8);
        }

        private boolean isSame(String a, String b) {
                return a.equals(b);
        }

        public AClassGenerator parameterNames(Collection<ParameterModel> parameters) {
                boolean first = true;
                for (ParameterModel parameter : parameters) {
                        if (first) {
                                first = false;
                        } else {
                                s(", ");
                        }
                        s(parameter.getName());
                }
                return this;
        }

        public AClassGenerator parameterDeclaration(Collection<ParameterModel> parameters) {
                boolean first = true;
                for (ParameterModel parameter : parameters) {
                        if (first) {
                                first = false;
                        } else {
                                s(", ");
                        }
                        s(parameter.getType(), parameter.getName());
                }
                return this;
        }

        public AClassGenerator s(String... ss) {
                boolean first = true;
                for (String s : ss) {
                        if (first) {
                                first = false;
                        } else {
                                out.print(" ");
                        }
                        out.print(s);
                }
                return this;
        }

        public AClassGenerator ln(String... ss) {
                s(ss);
                s("\n");
                return this;
        }

        public AClassGenerator sU(String s) {
                return s(uppercaseFirstLetter(s));
        }

        public void comment(String s) {
                s("    // --- ").s(s).s(" ---").ln();
        }

        public void section(String description) {
                ln();
                ln("    // -----------------------------------------------------------");
                ln("    // - " + description);
                ln("    // -----------------------------------------------------------");
        }

        public void dependency(String type, String name, boolean statik, boolean getter) {
                ln();
                s("    ");
                if (statik) {
                        s("static ");
                }
                s(type).s(" ").s(name).s(";").ln();
                ln();
                ln("    @edu.umd.cs.findbugs.annotations.SuppressWarnings(\"URF_UNREAD_FIELD\")");
                s("    public ");
                if (statik) {
                        s("static final ");
                }
                s("void set").sU(name).s("(").s(type).s(" ").s(name).s(") {").ln();
                s("        ");
                if (statik) {
                        s(getName());
                } else {
                        s("this");
                }
                s(".").s(name).s(" = ").s(name).s(";").ln();
                s("    }").ln();

                if (getter) {
                        ln();
                        s("    public ");
                        if (statik) {
                                s("static final ");
                        }
                        s(type).s(" get").sU(name).s("() {").ln();
                        s("        return ");
                        if (statik) {
                                s(getName());
                        } else {
                                s("this");
                        }
                        s(".").s(name).s(";").ln();
                        s("    }").ln();
                }
        }

        private String getGenericAsString() {
                String generic = getGeneric();
                if (generic == null) {
                        return "";
                }
                return "<" + generic + ">";
        }

        protected String getGeneric() {
                return null;
        }

        protected boolean isOverwrite() {
                return false;
        }

        protected boolean isAbstract() {
                return true;
        }

        protected Set<String> getMethodDeclarations() {
                return EMPTY_SET;
        }

        protected Set<String> getImports() {
                return EMPTY_SET;
        }

        protected Set<String> getSuperinterfaces() {
                return EMPTY_SET;
        }

        protected String getSuperclass() {
                return null;
        }

        protected final String getType() {
                return isInterface() ? "interface" : "class";
        }

        protected final File getFile() {               
                return new File(getSourcePath() + "/" + getPackage().replace('.', '/') + "/" + getName() + ".java");
        }

        protected String getSourcePath() {
                return "src/" + (isOverwrite() ? "generated" : "main") + "/java";
                // return "src/main/java";
        }

}
