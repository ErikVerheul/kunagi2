/*
 * Copyright 2011 Witoslaw Koczewsi <wi@koczewski.de>
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero
 * General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package ilarkesto.tools;

import ilarkesto.core.logging.Log;
import static ilarkesto.io.IO.listFiles;
import static ilarkesto.io.IO.readFile;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class JavascriptRequireMerger {

	private static final Log log = Log.get(JavascriptRequireMerger.class);

	private final List<File> files = new ArrayList<>();

	public void merge(PrintWriter out) {
		beforeFiles(out);
		for (File file : files) {
			if (file.isDirectory()) {
				for (File f : listFiles(file)) {
					processFile(out, f, "");
				}
				continue;
			}
			processFile(out, file, "");
		}
	}

	public void processFile(PrintWriter out, File file, String prefix) {
		if (file.isDirectory()) {
			prefix += file.getName() + "/";
			for (File f : listFiles(file)) {
				processFile(out, f, prefix);
			}
			return;
		}
		log.debug("Including", file);
		String content = readFile(file);
		beforeFile(out, prefix + file.getName());
		out.println(content);
		afterFile(out);
	}

	private void beforeFile(PrintWriter out, String filepath) {
		String modulename = filepath.substring(0, filepath.indexOf(".js"));
		out.print("\n");
		out.print("//\n");
		out.print("// ----- " + modulename + " -----\n");
		out.print("//\n");
		out.print("\n");
		out.print("var module = {};\n");
		out.print("browsermodules[\"" + modulename + "\"] = module;\n");
		out.print("module.init = function(exports) {\n");
		out.print("    function require(modulepath) { return importmodule(\"" + modulename + "\", modulepath); }\n");
		out.print("\n");
	}

	private void afterFile(PrintWriter out) {
		out.print("    this.exports = exports;\n");
		out.print("}\n");
	}

	private void beforeFiles(PrintWriter out) {
		out.print("// generated file\n");
		out.print("var browsermodules = {};\n");
		out.print("\n");
		out.print("function importmodule(frommodule, modulepath) {\n");
		out.print("    var modulename = modulepathToName(frommodule, modulepath);\n");
		out.print("    var module = browsermodules[modulename];\n");
		out.print("    if (!module) throw 'unknown module required from ' + frommodule + ': ' + modulename;\n");
		out.print("    if (!module.exports) {\n");
		out.print("        if (console) console.log(\"initializing \" + modulename);\n");
		out.print("        module.init({});\n");
		out.print("    }\n");
		out.print("    return module.exports;\n");
		out.print("}\n");
		out.print("\n");
		out.print("function require(modulepath) {\n");
		out.print("    return importmodule('-', modulepath);\n");
		out.print("}\n");
		out.print("\n");
		out.print("function modulepathToName(frommodule, modulepath) {\n");
		out.print("    var path = '';\n");
		out.print("    var modulename = modulepath;\n");
		out.print("    var idx = frommodule.indexOf('/');\n");
		out.print("    if (idx>=0) path = frommodule.substring(0, idx);\n");
		out.print("    idx = modulename.indexOf('/');\n");
		out.print("    while (idx>=0) {\n");
		out.print("        var dir = modulename.substring(0,idx)\n");
		out.print("        if (dir==='.') {\n");
		out.print("        } else if (dir==='..') {\n");
		out.print("            var lastIndex = path.lastIndexOf('/');\n");
		out.print("            path = lastIndex < 0 ? '' : path.substring(0, lastIndex);\n");
		out.print("        } else {\n");
		out.print("            path += '/' + dir;\n");
		out.print("        }\n");
		out.print("        modulename = modulename.substring(idx+1);\n");
		out.print("        idx = modulename.indexOf('/');\n");
		out.print("    }\n");
		out.print("    return path==='' ? modulename : path + '/' + modulename;\n");
		out.print("}\n");
		out.print("\n");
	}

	public void add(File file) {
		files.add(file);
	}

}
