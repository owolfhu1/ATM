/**
 * Created by Orion Wolf_Hubbard on 6/5/2017.
 */
public class Main {
	
	
    public static void main(String... args) {
    	ATM atm = new ATM();
    	
        try { atm.loginMenu(); } catch (Exception e) { e.printStackTrace(); }

        //and that's it..
    }

}

