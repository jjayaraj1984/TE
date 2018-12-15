package Test;

import java.util.HashMap;
import java.util.Map;

public class StorePairings {
	
	 HashMap<String, String> list = new HashMap <String, String>();
	 
	  public HashMap<String, String> getValue()
      {
       return list;
      }

     public void setValue(HashMap<String, String> santaList)
      {
        this.list = santaList;
      }
   
   }


