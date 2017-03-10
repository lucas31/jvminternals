package com.jsonConverter;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.*;

public class TwoWayKrugger {

	public static void main(String[] args) {
		AnyObject obj = new AnyObject();
		
		obj.someValue = 94352;
		obj.otherValue = 9;
		
		obj.becauseWhyNotChar = 'z';
		
		obj.whatIsLove = new ArrayList<String>();
		obj.whatIsLove.add("Baby don't hurt me");
		obj.whatIsLove.add("Baby don't hurt me");
		obj.whatIsLove.add("No more");
		
		obj.someRandomNumbers = new ArrayList<Integer>();
		obj.someRandomNumbers.add(5);
		obj.someRandomNumbers.add(35);
		obj.someRandomNumbers.add(153);
		obj.someRandomNumbers.add(57);
		obj.someRandomNumbers.add(36285);
		
		hockeyMask(obj);
		
	}
	
	@SuppressWarnings({ "rawtypes" })
	public static boolean hockeyMask(AnyObject obj) {
		
		ArrayList<String> toSave = new ArrayList<String>();
		
		for (Field f : obj.getClass().getDeclaredFields()) {
			toSave.add("\"" + f.getName() + "\":");
			try {
				f.setAccessible(true);
				if (f.get(obj) instanceof ArrayList) {
				    for(int i=0; i<((ArrayList)f.get(obj)).size(); i++) {
				    	toSave.add(
				    			(i==0 ? "[" : "") + "\"" + 
				    			((ArrayList)f.get(obj)).get(i).toString() + "\"" + 
				    			(i == ((ArrayList)f.get(obj)).size() - 1 ? "]" : ""));
				    }					    
				}
				else {
					if(f.get(obj) == null) toSave.add("null");
					else toSave.add("\"" + f.get(obj).toString() + "\"");
				}
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
				return false;
			} catch (IllegalAccessException e) {
				System.out.println(e.getMessage());
				return false;
			}		
		}
		
		try{
			PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/output.json", "UTF-8");
		    writer.write("{\n");
			for(int i=0; i<toSave.size(); i++) {
		    	writer.write(toSave.get(i));		    	
		    	if(!toSave.get(i).endsWith(":")) {
		    		if(i != toSave.size()-1) writer.write(",");
		    		writer.write("\n");
		    	}
		    }
			writer.write("}\n");
		    writer.close();
		} catch (IOException e) {
		   e.printStackTrace();	
		}	
		
		return true;
	}
}