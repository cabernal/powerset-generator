package me.cabernal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class PowerSetApp 
{
	public enum Delimeter {
		CSV(","),
		TSV("\t"),
		NEW_LINE("\n");
		
		private final String delimeter;
		private Delimeter(String delimeter) {
			this.delimeter = delimeter;
		}
		public String getDelimeter() {
			return delimeter;
		}
	}

    public static void main( String[] args )
    {
    	if(args.length != 2){
    		System.err.println("usage: <program> <input-file> <output-file>");
    		System.exit(1);
    	}
    	Path inputFile = Paths.get(args[0]);
    	Path outputFile = Paths.get(args[1]);
    	
    	/*
    	 * TODO:
    	 * - Verify file paths
    	 * - Use string builder
    	 * - Add third argument: set file format
    	 * - Create output file if it dne
    	 * - Finally Close
    	 * - Create FileSet(inFile, outFile, delimeter)
    	 * - Change class name
    	 */

    	try {
			BufferedReader reader = Files.newBufferedReader(inputFile);
			
			// Assume newline separated values as our set format
			String line = null;
			Set<String> inputSet = new HashSet<String>();
			while ((line = reader.readLine()) != null){
				System.out.println("READING: " + line);
				inputSet.add(line);
			}
			
			PowerSet<String> powerSet = new PowerSet<String>(inputSet);
			Charset charset = Charset.forName("US-ASCII");
			BufferedWriter writer = Files.newBufferedWriter(outputFile, charset);
			
			for(Set<String> subset : powerSet){
				//write subset to file
				String[] subsetArray = new String[subset.size()];
				subset.toArray(subsetArray);
				String subsetString = String.join(",", subsetArray);
				System.out.println("WRITING: " + subsetString);
				writer.write(subsetString);
			}
			
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
			//TODO: Finally close
		}
    	
    			
    	
    	
    }
}
