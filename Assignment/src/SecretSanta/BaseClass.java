package SecretSanta;

import java.util.ArrayList;
import java.util.Scanner;

public class BaseClass {

	public static void main(String[] args) {
		addParticipants();

	}
	
	public static void addParticipants() {
		System.out.println("Add participants. Type done when you're finished");
		Scanner input = new Scanner(System.in);
		String names = input.nextLine();
		
		ArrayList <String> participants = new ArrayList <String>();
		
		while(names != "done"){
			
			participants.add(names);
				
		}
		
		System.out.println(participants);
	}

}
