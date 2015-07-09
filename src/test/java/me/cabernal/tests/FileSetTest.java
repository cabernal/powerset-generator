package me.cabernal.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import me.cabernal.FileSet;
import me.cabernal.FileSet.Delimeter;
import me.cabernal.PowerSet;

import org.junit.*;

import static org.junit.Assert.*;

public class FileSetTest {
	private final String[] testFiles = { "CSVFile", "EmptyFile", "SmallFile" };
	private final String setFileExtension = ".set";
	private final String powersetFileExtension = ".superset"; // TODO: fix extension to .powerset
	private final String tmpFileExtension = ".tmp";
	private final Delimeter delim = Delimeter.CSV;

	@Test
	public void testReadFileSet() throws IOException {
		for (String filenamePrefix : testFiles) {
			Path setFile = Paths.get(getResourcePath(filenamePrefix + setFileExtension));
			Path supersetFile = Paths.get(getResourcePath(filenamePrefix + powersetFileExtension));

			StubFileSet testFileSet = new StubFileSet(setFile, null, delim);
			String resultingPowerset = testFileSet.getPowerSetAsString();
			String expectedPowerset = loadFileAsString(supersetFile);

			assertEquals(resultingPowerset, expectedPowerset);
		}

	}

	@Test
	public void testWriteFileSet() throws IOException {
		for (String filenamePrefix : testFiles) {
			Path setFile = Paths.get(getResourcePath(filenamePrefix + setFileExtension));
			Path powersetFile = Paths.get(getResourcePath(filenamePrefix + powersetFileExtension));
			File tmpFile = File.createTempFile(filenamePrefix, tmpFileExtension);
			Path outFile = Paths.get(tmpFile.getPath());
			
			
			

			StubFileSet testFileSet = new StubFileSet(setFile, outFile, delim);
			testFileSet.writePowerSet();
			String resultingPowerset = loadFileAsString(outFile);
			String expectedPowerset = loadFileAsString(powersetFile);
			tmpFile.delete();

			assertEquals(resultingPowerset, expectedPowerset);
		}
	}
	
	/** Helper methods and stub class **/

	/**
	 * Retrieve resource path.
	 * @param resource  Resource to retrieve
	 * @return String resource Path
	 */
	private String getResourcePath(String resource) {
		return this.getClass().getResource(resource).getPath();
	}

	/**
	 * Load file into String
	 * @param file file to load
	 * @return String loaded with file contents
	 * @throws IOException Throw exceptions during tests
	 */
	private String loadFileAsString(Path file) throws IOException {
		BufferedReader reader = Files.newBufferedReader(file);
		StringBuilder sb = new StringBuilder();

		String line = null;
		while ((line = reader.readLine()) != null) {
			sb.append(line + '\n');
		}
		reader.close();

		return sb.toString().trim();
	}

	/**
	 * Stub FileSet class used to retrieve String representation of PowerSet
	 */
	private class StubFileSet extends FileSet {

		public StubFileSet(Path inFile, Path outFile, Delimeter delim) {
			super(inFile, outFile, delim);
		}

		public String getPowerSetAsString() {
			PowerSet<String> ps = getPowerSet();
			StringBuilder sb = new StringBuilder();
			for (Set<String> strSet : ps) {
				sb.append(String.join(delim.getValue(), strSet));
				sb.append('\n');
			}

			return sb.toString().trim();

		}

	}
}
