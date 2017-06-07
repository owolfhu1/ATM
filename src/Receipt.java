import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;


/**
 *  Created by Orion Wolf_Hubbard on 6/5/2017.
 */

public abstract class Receipt {

	//prints out recite with history from AMT session
    public static String print(Account account, ArrayList<String> history) {
        String temp = " _______________________________________\n";
        temp += f("|  ");
        temp += f("|  Recite for %s", account);
        temp += f("|  ");
        temp += f("|  Today is %s", DateUtility.printToday());
        temp += f("|  ");
        for (String aHistory : history) temp += f("|  -%s", aHistory);
        temp += f("|  ");
        temp += f("|  your balance is now %s", NumberUtility.toDollars(account.getBalance()));
        temp += f("|  have a nice day, %s.",account);
        temp += f("|________________________________________________");
        return temp;
    }
    
    //prints full history, from history.txt and history saved in ATM session
    public static String printFull(Account account, ArrayList<String> history) throws JSONException {
    	JSONObject json = new JSONObject(SaveHistory.readFile());
    	String[] oldHistory = json.getJSONObject(account.getNumber()).getString("history").split("[|]");
        String temp = " _______________________________________\n";
        temp += f("|  ");
        temp += f("|  full history for %s", account);
        temp += f("|  ");
        temp += f("|  Today is %s", DateUtility.printToday());
        temp += f("|  ");
        for (String aHistory : oldHistory) temp += f("|  -%s", aHistory);
        for (String aHistory : history) temp += f("|  -%s", aHistory);
        temp += f("|  ");
        temp += f("|  your balance is now %s", NumberUtility.toDollars(account.getBalance()));
        temp += f("|  have a nice day, %s.",account);
        temp += f("|________________________________________________");
        return temp;
    }
    
    //takes a formated string and returns a string padded with | at index(40)
    public static String f(String format, Object... args) {
    	String string = String.format(format,args);
    	string = string + "                                        ";
    	string = string.substring(0, 40) + "|\n";
    	return string;
    }

}
