package me.cabernal;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * Hello world!
 *
 */
public class PowerSetApp 
{
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
    	 */
    	
    	try {
			BufferedReader reader = Files.newBufferedReader(inputFile);
			
			// Assume newline separated values as our set format
			String line = null;
			Set<String> inputSet = new HashSet<String>();
			while ((line = reader.readLine()) != null){
				inputSet.add(line);
			}
			
			PowerSet<String> powerSet = new PowerSet<String>(inputSet);
			
			
		} catch (IOException e) {
			System.err.format("IOException: %s%n", e);
		}
    	
    			
    	
    	
    }
}
