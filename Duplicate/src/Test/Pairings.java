package Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class Pairings extends StorePairings {
	Names names = new Names();

	/*
	 * Secret Santa
	 * getPairings() is called from BaseClass main class
	 * 
	 */
	
	public void getPairings() throws IOException {
		System.out.println("hi world");
		ArrayList<String> originalList = new ArrayList<String>(names.getNames("Resources/Names.txt"));
		ArrayList<Integer> shuffledNumberList = new ArrayList<Integer>(originalList.size());
		Boolean[][] FlagMatrix = new Boolean[originalList.size()][originalList.size()];
		for (int i = 0; i < originalList.size(); i++) {
			shuffledNumberList.add(i);
		}

//		FlagMatrix = AssignMatrix1();					// CASE 1

//		FlagMatrix = AssignMatrix2();					// CASE 2

		Boolean[][] FlagFamily = new Boolean[originalList.size()][originalList.size()];
		FlagFamily = getFamilies("Resources/Families.txt", originalList);
		FlagMatrix = AssignMatrix3(FlagFamily);

		do {
			Collections.shuffle(shuffledNumberList);
		} while (!CheckAgainstMatrix(FlagMatrix, shuffledNumberList));

		for (int i = 0; i < originalList.size(); i++) {
			System.out.println(originalList.get(i) + " ," + originalList.get(shuffledNumberList.get(i)));
		}

	}

	private Boolean[][] AssignMatrix1() throws IOException {
		ArrayList<String> originalList = new ArrayList<String>(names.getNames("Resources/Names.txt"));
		Boolean[][] FlagMatrix = new Boolean[originalList.size()][originalList.size()];

		int i, j;

		/* Initialize all values of row[] as 0 */
		for (i = 0; i < originalList.size(); i++) {
			for (j = 0; j < originalList.size(); j++) {
				FlagMatrix[i][j] = true;
				if (i == j) {
					FlagMatrix[i][j] = false;
				} // cannot be assigned to oneself
			}
//			System.out.println(Arrays.toString(FlagMatrix[i]));
		}
		return FlagMatrix;

	}

	private Boolean[][] AssignMatrix2() throws IOException {
		ArrayList<String> originalList = new ArrayList<String>(names.getNames("Resources/Names.txt"));
		ArrayList<String> threeYearList = new ArrayList<String>(names.getNames("Resources/ThreeYears.txt"));
		System.out.println(threeYearList.size());
		Boolean[][] FlagMatrix = new Boolean[originalList.size()][originalList.size()];

		FlagMatrix = AssignMatrix1();

		int i;

		for (i = 0; i < threeYearList.size(); i = i + 2) // cannot be assigned if already assigned in last three years
		{
			try {
				FlagMatrix[originalList.indexOf(threeYearList.get(i))][originalList
						.indexOf(threeYearList.get(i + 1))] = false;
			} catch (Exception e) {
				System.out.println("An error occurred and one of the values in ThreeYears.txt is "
						+ "not present in Names.txt and is therefore ignored");
			}
		}

		return FlagMatrix;

	}

	public Boolean[][] getFamilies(String filename, ArrayList<String> originalList) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		Boolean[][] FlagFamily = new Boolean[originalList.size()][originalList.size()];
		int i, j;
		for (i = 0; i < originalList.size(); i++) {
			for (j = 0; j < originalList.size(); j++)
				FlagFamily[i][j] = false; // default is that they don't belong to any family
		}
		String line;
		j = 0;
		while ((line = br.readLine()) != null) {
			String[] r = line.split(",");
			for (i = 0; i < r.length; i++) {
				String nameValue = String.valueOf(r[i]);
				nameValue.replaceAll("\\s+", "");
				try {
					FlagFamily[j][originalList.indexOf(nameValue)] = true;
				} catch (Exception e) {
					System.out.println("An error occurred and " + nameValue
							+ " is not part of Names.txt but is present in Families.txt and is therefore ignored");
				}
			}
			j++;
		}
		br.close();

		return FlagFamily;

	}

	private Boolean[][] AssignMatrix3(Boolean[][] FlagFamily) throws IOException {

		ArrayList<String> originalList = new ArrayList<String>(names.getNames("Resources/Names.txt"));
		Boolean[][] FlagMatrix = new Boolean[originalList.size()][originalList.size()];
		FlagMatrix = AssignMatrix2();
		int i, j, k, x, y;
		Integer TempFlag[] = new Integer[originalList.size()];
		for (i = 0; i < originalList.size(); i++) {
			k = 0;
			for (j = 0; j < originalList.size(); j++) {
				if (FlagFamily[i][j] == true) {
					TempFlag[k] = j;
					k++;
				}
			}
			for (x = 0; x < k; x++) {
				for (y = 0; y < k; y++) {
					FlagMatrix[TempFlag[x]][TempFlag[y]] = false;
					FlagMatrix[TempFlag[y]][TempFlag[x]] = false;
				}
			}

		}

		return FlagMatrix;

	}

	private boolean CheckAgainstMatrix(Boolean[][] FlagMatrix, ArrayList<Integer> shuffledNumberList)
			throws IOException {

		int i;

		for (i = 0; i < shuffledNumberList.size(); i++) {
			if (FlagMatrix[i][shuffledNumberList.get(i)] == false) {
				return false;
			}

		}
		return true;

	}

}
