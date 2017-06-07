import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  Created by Orion Wolf_Hubbard on 6/5/2017.
 */

public class Accounts {
	ArrayList<Account> accounts = new ArrayList<>();
	ArrayList<String> fails = new ArrayList<>();
    Account active;

    //constructor gets all accounts from history.txt
    public Accounts() {
		try {
			JSONObject json = new JSONObject(SaveHistory.readFile());
		
			//get all the keys in json history
			Iterator<?> keys = json.keys();
    	
			//makes accounts with keys
			while( keys.hasNext() ) {
				String key = (String)keys.next();
				addAccount(
    	    		json.getJSONObject(key).getString("name"),
    	    		key, 
    	    		json.getJSONObject(key).getString("pin"),
    	    		Double.parseDouble(json.getJSONObject(key).getString("balance"))
				);
			}
    	} catch (JSONException e) { e.printStackTrace(); }
    	active = null;
    }
    
    //adds new account with number, pin and money or money default = 0
    public boolean addAccount(String name, String number, String pin, double balance){
        //check if the account number already exists
        //if not, make new account, log in, return true
        if (indexOfAccount(number) == -1) {
            active = new Account(name, number, pin, balance);
            accounts.add(active);
            return true;
        }
        //if the account exists return false
        return false;
    }
    public boolean addAccount(String name, String number, String pin) {
        return addAccount(name, number, pin, 0);
    }

    //logs out active account
    public void logout(){ active = null; }

    //check if user exists, if pin is correct (logs in and returns true) or (returns false)
    public boolean login(String number, String pin) {
        int index = indexOfAccount(number);
        if (index != -1) {
            if (accounts.get(index).checkPin(pin)){
                active = accounts.get(index);
                
                //clears failed login attempts on login
                fails = new ArrayList<>();
                return true;
            }
        }
        //add account number to failed list to block them out after 3 logins
        fails.add(number);
        return false;
    }
    
    //checks if user has tried and failed to log in 3 times
    public boolean checkFails(String number) {
    	int counter = 0;
    	for (String fail : fails) if (fail.equals(number)) counter++;
    	return counter < 3;
    }

    //finds the index of the account in the accounts array, returns -1 if index doesn't exist
    public int indexOfAccount(String number){
        for (int i = 0; i < accounts.size(); i++) if (accounts.get(i).getNumber().equals(number)) return i;
        return -1;
    }

    //adds money, to withdraw, use addMoney(-money);
    public double addMoney(double money){
        if (active != null) return active.addMoney(money);
        return 0.0;
    }

    //returns a formatted string to be printed
    public String printSummary(ArrayList<String> history) {
        return Receipt.print(active, history);
    }
}
