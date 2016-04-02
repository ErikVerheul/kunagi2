package ilarkesto.integration.avconv;

import ilarkesto.base.Proc;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class AvconvCmd {

	private Proc proc = new Proc("avconv");
	private int returnCode;
	private String output;

	public void execute() {
		if (proc == null) {
                        throw new IllegalStateException("avconv already executed");
                }
		proc.start();
		proc.waitFor();
		returnCode = proc.getReturnCode();
		output = proc.getOutput();
		proc = null;
	}

	public List<String> getStreamInfos() {
		List<String> ret = new ArrayList<>(3);
		StringTokenizer tokenizer = new StringTokenizer(getOutput(), "\r\n");
		while (tokenizer.hasMoreTokens()) {
			String line = tokenizer.nextToken();
			line = line.trim();
			if (line.startsWith("Stream #")) {
                                ret.add(line);
                        }
		}
		return ret;
	}

	public String getOutput() {
		if (proc != null) {
                        throw new IllegalStateException("avconv not executed yet");
                }
		return output;
	}

	public void addInputFile(File file) {
		proc.addParameters("-i", file.getAbsolutePath());
	}

	public int getReturnCode() {
		if (proc != null) {
                        throw new IllegalStateException("avconv not executed yet");
                }
		return returnCode;
	}

	public static List<String> getStreamInfos(File file) {
		AvconvCmd avconv = new AvconvCmd();
		avconv.addInputFile(file);
		avconv.execute();
		return avconv.getStreamInfos();
	}

}
