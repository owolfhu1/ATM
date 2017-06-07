import java.util.ArrayList;
import java.util.Scanner;

/**
 *  Created by Orion Wolf_Hubbard on 6/5/2017.
 */

public interface iATM {
    //prints formated text with System.out.printf
    public void pf(String format, Object... args);

    //prompts to add new account or log in
    public void loginMenu() throws Exception;

    //prompts to get balance, add money, withdraw money, print transaction, or logout
    public void mainMenu() throws Exception;
}
