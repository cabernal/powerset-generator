package me.cabernal;

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
    	}
        for (String s: args){
        	System.out.println(s);
        }
    }
}
