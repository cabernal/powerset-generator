package me.cabernal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class representing formatted set within a text file.
 */
public class FileSet {
	/**
	 * Delimeter used to separate set members in a file.
	 * CVS and TSV delimeters supported
	 */
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

		/**
		 * Get the delimeter belonging to the specified type
		 * @param type delimeter type
		 * @return Delimeter delimeter
		 */
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

	/**
	 * Read the entire set in the input file on to a String set
	 *
	 * @return Set<String> String set representing set contents of file
	 */
	public Set<String> readSet() {
		BufferedReader reader = null;
		Set<String> inputSet = new HashSet<String>();

		try {
			reader = Files.newBufferedReader(inFile);
			String line = null;
			while ((line = reader.readLine()) != null) {
				// split line by specified delimeter
				String[] members = line.split(delim.getValue());
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

	/**
	 * Get the {@link PowerSet} of current input file
	 * @return {@link PowerSet}
	 */
	public PowerSet<String> getPowerSet() {
		return new PowerSet<String>(readSet());
	}

	/**
	 * Write power set of current input file to specified output file.
	 */
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
