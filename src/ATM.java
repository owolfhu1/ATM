import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;

/**
 * Created by Orion Wolf_Hubbard on 6/5/2017.
 */

public class ATM implements iATM {
	Scanner in = new Scanner(System.in);
	boolean loggedIn = false;
	ArrayList<String> history = new ArrayList<>();
	Accounts accounts = new Accounts();

	// this function will be called inorder to start the program.
	// It will loop until a user is logged in, then will go to mainMenu()
	@Override
	public void loginMenu() throws Exception {
		// as long as we are logged out, keep prompting this menu
		while (!loggedIn) {
			pf("press enter to continue");
			in.nextLine();
			pf("hello guest, to log in enter 'login', to create a new user enter 'new': ");
			String logNew = in.nextLine();
			if (logNew.toLowerCase().equals("login")) {
				pf("Please enter your account#: ");
				String number = in.nextLine();
				pf("Please enter your pin: ");
				String pin = in.nextLine();
				if (accounts.checkFails(number)) {
					if (accounts.login(number, pin)) loggedIn = true;
					else pf("Incorrect pin: please try again\n");
				} else pf("Account # %s has failed to login 3 times and is now locked out.\n", number);
			}
			if (logNew.toLowerCase().equals("new")) {
				pf("please enter your name: ");
				String name = in.nextLine();
				pf("Please enter an account#: ");
				String number = in.nextLine();
				pf("Please enter a pin: ");
				String pin = in.nextLine();
				pf("how much money would you like to deposit into the new account: ");
				double money;
				if (!in.hasNextDouble()) {
					if (accounts.addAccount(name, number, pin)) {
						loggedIn = true;
						SaveHistory.addAccount(accounts.active);
					} else pf("sorry %s, That number is already taken, please try again.\n", name);
				} else {
					money = in.nextDouble();
					if (accounts.addAccount(name, number, pin, money)) {
						loggedIn = true;
						SaveHistory.addAccount(accounts.active);
					} else pf("sorry %s, That number is already taken, please try again.\n", name);
				}
			}
		}
		//Once out of loop, user is logged in, add login to history and go to mainMenu();
		pf("Hello, %s, you are now logged in.\n", accounts.active);
		history.add(String.format("Logged in on %s", DateUtility.printToday()));
		history.add(String.format("Balance: %s", NumberUtility.toDollars(accounts.active.getBalance())));
		mainMenu();
	}

	
	//while user is logged in, this loops, when user is logged out, go back to 
	@Override
	public void mainMenu() throws Exception {
		in = new Scanner(System.in);
		while (loggedIn) {
			pf("press enter to continue");
			in.nextLine();
			pf("\nMain Menu: (B)alance (D)eposit (W)ithdraw (P)rint (L)ogout (F)ull History (pick a letter): ");
			char selection = in.nextLine().toLowerCase().charAt(0);
			
			//switches menu options
			switch (selection) {
				//balance
				case 'b': pf("Your balance is %s.\n", NumberUtility.toDollars(accounts.active.getBalance())); break;
				//deposit
				case 'd':
					pf("How much would you like to deposit?: ");
					if (!in.hasNextDouble()) pf("sorry that is not a valid number");
					else {
						double temp = in.nextDouble();
						pf("Depositing %s to your account.\n", NumberUtility.toDollars(temp));
						pf("You now have %s in your account.\n", NumberUtility.toDollars(accounts.active.addMoney(temp)));
						history.add(String.format("Deposited %s", NumberUtility.toDollars(temp)));
					}
					break;
				//Withdraw		
				case 'w':
					pf("How much would you like to withdraw?: ");
					if (!in.hasNextDouble()) pf("sorry that is not a valid number");
					else {
						double temp = in.nextDouble();
						pf("Withdrawing %s to from your account.\n", NumberUtility.toDollars(temp));
						pf("You now have %s in your account.\n", NumberUtility.toDollars(accounts.active.addMoney(-temp)));
						history.add(String.format("Withdrew %s", NumberUtility.toDollars(temp)));
					}
					break;
				//print
				case 'p': pf(Receipt.print(accounts.active, history)); break;
				//logout
				case 'l': loggedIn = false; break;
				//full print
				case 'f': pf(Receipt.printFull(accounts.active, history)); break;
			}
		}
		
		// save history to history/history.txt
		SaveHistory.addHistory(accounts.active, history);

		pf("Bye Bye %s, come again when you need more money!\n", accounts.active);
		accounts.logout();

		// clear history on logout
		history = new ArrayList<>();

		// now that we are logged out again, go to loginMenu()
		loginMenu();
	}

	
	//pf() = System.out.printf();
	@Override
	public void pf(String format, Object... args) {
		System.out.printf(format, args);
	}

}