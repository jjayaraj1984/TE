package SecretSanta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BaseClass {
	/**
	 * 
	 *getPairings() method will be called from Pairings class
	 *It combines all 3 of the assignment constraints: 
	 *1. random selection(from Names.txt)
	 *2. No repeats(ThreeYears.txt) 
	 *3. Don't assign to immediately family(Families.txt)
	 * 
	 * Names.txt should be list of all names of people involved in Secret Santa 
	 * exchange (either separated by endlines or by commas)
	 * 
	 * Families.txt should list names of immediate family members of all families involved
	 * Each new line is considered as one family, and all members of that family are separated by commas. 
	 * If any name is present in families.txt but not in Names.txt, it will be ignored.
	 * All names that are not listed in families.txt but are present in Names.txt, will be treated as 
	 * not having any immediate family in the secret santa exchange.
	 * 
	 * ThreeYears.txt should list all the combinations that were assigned in previous years (as many as needed is permissible).
	 * Each assignment needs to be on a new line and two names are separated by a comma
	 * Any name needs to also be present in Names.txt, else its constraints will be ignored.
	 * The names however do not need to be in the same order as they appear in Names.txt.
	 * 
	 */

	public static void main(String[] args) throws IOException {
			
		Pairings pairings = new Pairings();
		pairings.getPairings();
		}

}
