package SecretSanta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BaseClass {
	/**
	 * 
	 * @param args
	 * @throws IOException Create random number take random number element from
	 *                     array
	 * 
	 */

	public static void main(String[] args) throws IOException {
		
		Pairings pairings = new Pairings();

		
		System.out.println("The 2018 Secret Santa pairings: " + pairings.getPairings());

	}

}
