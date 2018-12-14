package SecretSanta;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Names {
	
	public ArrayList <String> getNames() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("Resources/Names.txt"));
		
		ArrayList <String> names = new ArrayList <String>();
		
		String line;
		
		while((line = br.readLine())!=null){
			
			String [] r = line.split(",");
			
			for(int i=0; i<r.length; i++) {
		String nameValue = String.valueOf(r[i]);
		names.add(nameValue);
			}
			
			
		}br.close();
		return names;
		
	}
}
