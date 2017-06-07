import java.text.NumberFormat;
import java.util.Random;

public class NumberUtility {

    //returns number to requested decimalPlaces and % sign at end (default 2 places)
    public static String toPercent(double number, int decimalPlaces) {
        return decimalToFixed(number, decimalPlaces) + "%";
    }
    public static String toPercent(double number) {
        return toPercent(number, 2);
    }

    //fixes to 2 decimal places for money and adds a $ sign
    public static String toDollars(double money) {
        NumberFormat c = NumberFormat.getCurrencyInstance();
        return c.format(money);
    }

    //fixes decimal places for number to requested decimalPlaces
    public static double decimalToFixed(double number, int decimalPlaces) {
        NumberFormat c = NumberFormat.getNumberInstance();
        c.setMaximumFractionDigits(decimalPlaces);
        return Double.parseDouble(c.format(number));
    }

    //random int between low and high input default low is 0
    public static int randomInt(int low, int high){
        Random rand = new Random();
        return rand.nextInt(high-low) + low;
    }
    public static int randomInt(int high) {
        return randomInt(0, high);
    }

    //random double between low and high input default low is 0
    public static double randomDouble(double low, double high){
        Random rand = new Random();
        double range = high - low;
        return rand.nextDouble() * range + low;
    }
    public static double randomDouble(double high){
        return randomDouble(0, high);
    }

    //max of any number of numbers
    public static double max(double... numbers){
        double temp = 0;
        for (int i = 0; i < numbers.length; i++ ) {
            if (i == 0) temp = numbers[0];
            else temp = Math.max(temp, numbers[i]);
        }
        return temp;
    }

    //min of any number of numbers
    public static double min(double... numbers){
        double temp = 0;
        for (int i = 0; i < numbers.length; i++ ) {
            if (i == 0) temp = numbers[0];
            else temp = Math.min(temp, numbers[i]);
        }
        return temp;
    }

}
