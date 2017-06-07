
/**
 *  Created by Orion Wolf_Hubbard on 6/5/2017.
 */

public class Account {
    private String name;
    private String number;
    private String pin;
    private double balance;

    public Account(String name, String number, String pin, double balance) {
        this.name = name;
        this.number = number;
        this.pin = pin;
        this.balance = balance;
    }

    public String getNumber() {
        return number;
    }

    public double getBalance() {
        return balance;
    }

    //rather than getPin, checkPin for safety :)
    public boolean checkPin(String pin) {
        return this.pin.equals(pin);
    }

    public double addMoney(double money) {
        return balance += money;
    }

    @Override
    public String toString() {
        return name;
    }
    
    //only used for saving pin to history.txt
	public String getPin() {
		return pin;
	}
}
