/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		// You fill this in //
		int nameStart = 0;
		int nameEnd = line.indexOf(" ");
		name = line.substring(nameStart, nameEnd);
		
		int rankStart = nameEnd + 1;
		for (int i = 0; i < NDECADES - 1; i++) { //loop over all entries
			int rankEnd = rankStart + line.substring(rankStart).indexOf(" "); //use whitespace for indexing
			rankArray[i] = Integer.parseInt(line.substring(rankStart, rankEnd)); //convert String to Integer
			rankStart = rankEnd + 1;
		}
		rankArray[NDECADES - 1] = Integer.parseInt(line.substring(rankStart)); //last value does not have a whitespace for indexing
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		// You need to turn this stub into a real implementation //
		return name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		// You need to turn this stub into a real implementation //
		return rankArray[decade];
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		// You need to turn this stub into a real implementation //
		String output;
		output = name + " [" + rankArray[0];
		for (int i = 1; i < NDECADES; i++) {
			output = output + " " + rankArray[i];
		}
		output = output + "]"; 
		return output;
	}
	
/* Private Instance Variables */
	private int[] rankArray = new int[NDECADES]; //Array of ranks for each decade, from 0 (START_DECADE) to NDECADES 
	private String name; //Name of baby
}

