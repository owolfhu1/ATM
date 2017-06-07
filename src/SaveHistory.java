
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


public class SaveHistory {
	static PrintWriter writer;
	
	//get filename path
	static String filename = (System.getProperty("user.dir") + File.separatorChar + "src" + File.separatorChar + "history" + File.separatorChar + "history.txt");
	
	//adds new json to txt json
	public static void addAccount(Account account) throws Exception {
		//get json from history.txt
		JSONObject json = new JSONObject(readFile());
		JSONObject newj = new JSONObject();
		
		//build new Object to go number:object in history.txt
		newj.put("pin", account.getPin());
		newj.put("name", account.toString());
		newj.put("balance", Double.toString(account.getBalance()));
		newj.put("history", "");
		
		//add newj to main json
		json.put(account.getNumber(), newj);
		
		//try to write json to history.txt
		try (FileWriter file = new FileWriter(filename)) {
			file.write(json.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//called when user logs out, saves there history for when atm reboots
	public static void addHistory(Account account, ArrayList<String> history) throws Exception {
		//get json from history.txt
		JSONObject json = new JSONObject(readFile());
		
		//get past history from json
		String oldHistory = (String)json.getJSONObject(account.getNumber()).get("history");
		
		//add new history to past history
		for (String s : history) oldHistory += String.format("%s|", s); 
		
		//but it all back
		json.optJSONObject(account.getNumber()).put("history", oldHistory);
		json.optJSONObject(account.getNumber()).put("balance", Double.toString(account.getBalance()));
		
		//try to write it to history.txt
		try (FileWriter file = new FileWriter(filename)) {
			file.write(json.toString(4));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//returns file as String[]
	public static ArrayList<String> readLines(File file) throws Exception {
		if (!file.exists()) {
			return new ArrayList<String>();
	    }
	    BufferedReader reader = new BufferedReader(new FileReader(file));
	    ArrayList<String> results = new ArrayList<String>();
	    String line = reader.readLine();
	    while (line != null) {
	        results.add(line);
	        line = reader.readLine();
	    }
	    reader.close();
	    return results;
	}
	//returns history.txt as String
	public static String readFile(){
		File file = new File(filename);
		String string = "";
		try {
		    ArrayList<String> lines = readLines(file);
			for(String line : lines ) string += line;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}
	
}