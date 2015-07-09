package me.cabernal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

//TODO: Use generics?
public class FileSet {
	public enum Delimeter {
		CSV(","), TSV("\t"), NEW_LINE("\n");

		private final String delimeter;

		private Delimeter(String delimeter) {
			this.delimeter = delimeter;
		}

		public String getDelimeter() {
			return delimeter;
		}
	}

	private Path inFile = null;
	private Path outFile = null;
	private Delimeter delim = null;

	public FileSet(Path inFile, Path outFile, Delimeter delim) {
		this.inFile = inFile;
		this.outFile = outFile;
		this.delim = delim;
	}

	public Set<String> readSet() {
		BufferedReader reader = null;
		Set<String> inputSet = new HashSet<String>();

		try {
			reader = Files.newBufferedReader(inFile);
			String line = null;
			while ((line = reader.readLine()) != null) {
				// split line by specified delimeter
				String[] members = line.split(delim.getDelimeter());
				inputSet.addAll(Arrays.asList(members));
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
		}
		return inputSet;
	}

	public PowerSet<String> getPowerSet() {
		return new PowerSet<String>(readSet());
	}

	public void writePowerSet() {
		Charset charset = Charset.forName("US-ASCII");
		BufferedWriter writer = null;
		try {
			writer = Files.newBufferedWriter(outFile, charset);

			for (Set<String> subset : getPowerSet()) {
				// write subset to file
				String[] subsetArray = new String[subset.size()];
				subset.toArray(subsetArray);
				String subsetString = String.join(",", subsetArray);
				writer.write(subsetString);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				System.err.format("IOException: %s%n", e);
			}
		}
	}

}
