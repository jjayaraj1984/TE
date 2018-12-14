package SecretSanta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

public class Pairings {
	Names names = new Names();

	public HashMap<String, String> getPairings() throws IOException {
		HashMap<String, String> pairings = new HashMap<String, String>();

		ArrayList<String> shuffledList = new ArrayList<String>(names.getNames());
		Collections.shuffle(shuffledList);

		ArrayList<String> originalList = new ArrayList<String>(names.getNames());

		for (int i = 0; i < shuffledList.size(); i++) {
			
			if(originalList.get(i).toString().equals(shuffledList.get(i).toString())) {
				Collections.shuffle(shuffledList);
				i--;
			}
			else {

			pairings.put(originalList.get(i), shuffledList.get(i));
		}
		}
		

		return pairings;
	}
	
	
	
	public HashMap <String,String> returnFinalPairing() throws IOException {
		HashMap<String,String> finalPairings = new HashMap <String,String>(getPairings());
		
		ArrayList checkDuplicates = new ArrayList(finalPairings.values());
		
		for(int i =0; i<checkDuplicates.size();i++) {
			for (int j = i+1; j<checkDuplicates.size();j++) {
				if(checkDuplicates.get(i).toString().equals(checkDuplicates.get(j).toString())) {
					
					System.out.println("There are duplicates!");
					
				}
			}
		}
	
		
		return finalPairings;
	}
	
	
}
