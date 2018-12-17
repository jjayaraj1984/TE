package SecretSanta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Pairings {
	Names names = new Names();

	/*
	 * The getPairings method is being called from BaseClass main. 
	 * 
	 */
	public void getPairings() throws IOException {
		ArrayList<String> originalList = new ArrayList<String>(names.getNames("Resources/Names.txt"));
		ArrayList<Integer> shuffledNumberList = new ArrayList<Integer>(originalList.size());
		Boolean[][] FlagMatrix = new Boolean[originalList.size()][originalList.size()];
		// FlagMmatrix is a matrix which has both rows and columns as indexes of the
		// names in Names.txt
		// Its elements represent ALL constraints of which name index is allowed to be
		// assigned to which name index.

		for (int i = 0; i < originalList.size(); i++) {
			shuffledNumberList.add(i);
		}

		System.out
				.println("Choose an assignment method: 1, for random selection, 2 for three years, and 3 for families");
		Scanner input = new Scanner(System.in);
		int option = input.nextInt();

		switch (option) {
		case 1:
			System.out.println("Generic random pairing"); // case1: Only constraint is that a person cannot be assigned
															// to themself
			FlagMatrix = AssignMatrix1();
			break;
		case 2:
			System.out.println("Different pairings every 3 years"); // case 2 also has constraints of case 1.

			FlagMatrix = AssignMatrix2();
			break;
		case 3:
			System.out.println("Immediate family members are not assigned to each other"); // case 3 also has
																							// constraints of case 2.
			Boolean[][] FlagFamily = new Boolean[originalList.size()][originalList.size()];
			FlagFamily = getFamilies(originalList);
			FlagMatrix = AssignMatrix3(FlagFamily);
			break;
		default:
			System.out.println("Not a valid selection");
		}
		
		if (CheckOverConstraint(FlagMatrix, originalList) == true)
		{
			System.out.println("Please reduce the constraints. There is no possible solution");
		}
		else
		{
		do {
			Collections.shuffle(shuffledNumberList);
		} while (!CheckAgainstMatrix(FlagMatrix, shuffledNumberList));

		for (int i = 0; i < originalList.size(); i++) {
			System.out.println(originalList.get(i) + " ," + originalList.get(shuffledNumberList.get(i)));
		}
		}

	}

	/*
	 * AssignMatrix1() assigns a matrix of size = number of names in the secret
	 * santa exchange This FlagMatrix is populated to ensure that each person is not
	 * assigned to him/herself
	 */
	private Boolean[][] AssignMatrix1() throws IOException {
		ArrayList<String> originalList = new ArrayList<String>(names.getNames("Resources/Names.txt"));
		Boolean[][] FlagMatrix = new Boolean[originalList.size()][originalList.size()];

		int i, j;

		/* Initialize all values of FlagMatrix as 0 */
		for (i = 0; i < originalList.size(); i++) {
			for (j = 0; j < originalList.size(); j++) {
				FlagMatrix[i][j] = true;
				if (i == j) {
					FlagMatrix[i][j] = false;
				} // cannot be assigned to oneself
			}
		}
		return FlagMatrix;

	}

	/*
	 * AssignMatrix2() Assigns FlagMatrix with elements populated by marking a past
	 * assignment declared in ThreeYears.txt with a 'false'
	 */
	private Boolean[][] AssignMatrix2() throws IOException {
		ArrayList<String> originalList = new ArrayList<String>(names.getNames("Resources/Names.txt"));
		ArrayList<String> threeYearList = new ArrayList<String>(names.getNames("Resources/ThreeYears.txt"));
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

	/*
	 * getFamilies uses Families.txt Outputs a matrix which has each row as a family
	 * with members according to the index of the columns The element is true if
	 * that name is in that particular family and false if not part of that family
	 * 
	 */
	public Boolean[][] getFamilies(ArrayList<String> originalList) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("Resources/Families.txt"));
		Boolean[][] FlagFamily = new Boolean[originalList.size()][originalList.size()];
		int i, j;
		for (i = 0; i < originalList.size(); i++) {
			for (j = 0; j < originalList.size(); j++)
				FlagFamily[i][j] = false; // default is that noone belongs to any family
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

	/*
	 * AssignMatrix3() uses FlagFamily which is output from getFamilies() using
	 * Families.txt Assigns FlagMatrix so that immediate members of the family will
	 * not be assigned each other
	 */
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

	/*
	 * Checks the FlagMatrix to see if there is atleast one possible solution. 
	 * This is done by checking every row and column of FlagMatrix
	 * If there is no possible solution, it returns a value of true
	 * and prompts the user to reduce the constraints.
	 */
	
	private boolean CheckOverConstraint(Boolean[][] FlagMatrix, ArrayList<String> originalList)
			throws IOException {

		int i,j;
		Boolean sumRow[] = new Boolean[originalList.size()];
		Boolean sumColumn[] = new Boolean[originalList.size()];
		
		for (i = 0; i < originalList.size(); i++) {
			sumRow[i] = false;				
			sumColumn[i] = false;
		}

		for (i = 0; i < originalList.size(); i++) {
			for (j = 0; j < originalList.size(); j++)
			{
				if (FlagMatrix[i][j]==true)
						{
							sumRow[i] = true;
						}
				if (FlagMatrix[j][i]==true)
						{
							sumColumn[i] = true;
						}
			}
		}

		
		for (i = 0; i < originalList.size(); i++)
		{
			if ((sumRow[i] == false) || (sumColumn[i] == false))
				{
				return true;	//there is atleast one row or column which has no possibility of being assigned
				}
		}
		return false;
	}
	
	/*
	 * Checks against the constraint matrix. If any constraint is broken, it returns
	 * a value of false and the names will be reshuffled.
	 */
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
