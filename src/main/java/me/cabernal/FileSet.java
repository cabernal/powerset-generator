package me.cabernal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileSet {
	public enum Delimeter {
		CSV(",", "csv"), TSV("\t", "tsv");

		private final String value;
		private final String type;

		private Delimeter(String value, String type) {
			this.value = value;
			this.type = type;
		}

		public String getValue() {
			return value;
		}

		public String getType() {
			return type;
		}

		public static Delimeter getDelimeter(String type) {
			for (Delimeter s : values()) {
				if (s.type.equals(type.toLowerCase()))
					return s;
			}
			return CSV;
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
				String[] members = line.split(delim.getValue());
				// TODO: use loop instead?
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
		BufferedWriter writer = null;
		try {
			writer = Files.newBufferedWriter(outFile);

			for (Set<String> subset : getPowerSet()) {
				// build formatted subset string
				String[] subsetArray = new String[subset.size()];
				subset.toArray(subsetArray);
				String subsetString = String.join(delim.getValue(), subsetArray);

				// write out string and separate subsets with new line
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
