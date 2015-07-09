package me.cabernal;

import java.nio.file.Path;
import java.nio.file.Paths;

import me.cabernal.FileSet.Delimeter;

public class PowerSetApp {
	public static final String usageMsg =
			"usage: <program> <input-file> <output-file> [delimeter: csv]";

	public static void main(String[] args) {
		// use CSV as default file format
		Delimeter delim = Delimeter.CSV;
		if (args.length < 2 || args.length > 3) {
			System.err.println(usageMsg);
			System.exit(1);
		}

		if (args.length == 3) {
			delim = Delimeter.getDelimeter(args[2]);
		}
		Path inputFile = Paths.get(args[0]);
		Path outputFile = Paths.get(args[1]);

		FileSet fileSet = new FileSet(inputFile, outputFile, delim);
		fileSet.writePowerSet();
	}
}
