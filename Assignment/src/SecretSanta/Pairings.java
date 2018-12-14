package SecretSanta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Pairings {
	Names names = new Names();

	public HashMap<String, String> getPairings() throws IOException {
		HashMap<String, String> pairings = new HashMap<String, String>();
		ArrayList<String> originalList = new ArrayList<String>(names.getNames());

		ArrayList<String> finalShuffledList = new ArrayList<String>(getShuffledList());

		for (int i = 0; i < finalShuffledList.size(); i++) {
			pairings.put(originalList.get(i).toString(), finalShuffledList.get(i).toString());
		}

		return pairings;
	}

	private ArrayList getShuffledList() throws IOException {
		ArrayList<String> originalList = new ArrayList<String>(names.getNames());
		ArrayList<String> shuffledList = new ArrayList<String>(originalList);
		Collections.shuffle(shuffledList);

		while (checkDuplicates(originalList, shuffledList)) {
			Collections.shuffle(shuffledList);

		}

		return shuffledList;

	}

	private boolean checkDuplicates(ArrayList<String> originalList, ArrayList<String> shuffledList) {
		for (int i = 0; i < originalList.size(); i++) {

			if (originalList.get(i).toString().equals(shuffledList.get(i).toString())) {
				return true;
			}

		}
		return false;
	}
	
	/**
	 * Create a two dimensional array
	 */

}
