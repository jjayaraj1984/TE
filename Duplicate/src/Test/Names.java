package Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Names {
	
	public ArrayList <String> getNames(String filename) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		ArrayList <String> names = new ArrayList <String>();
		
		String line;
		
		while((line = br.readLine())!=null){
			
			String [] r = line.split(",");
			
			for(int i=0; i<r.length; i++) 
			{
				String nameValue = String.valueOf(r[i]);
				names.add(nameValue.replaceAll("\\s+",""));
		
			}
			}br.close();
		
		
		return names;
		
	}
	
	private boolean checkDuplicateNames( ArrayList <String> names )
	{
	    for( int i = 0; i < names.size(); ++i )
	    {
	        for( int j = i + 1; j < names.size(); ++j )
	        {
	            if( names.get(i).toString().equals(names.get(j).toString()))
	                return true;
	        }
	    }
	    return false;
	}
}
