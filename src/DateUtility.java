import java.time.LocalDate;
import java.time.Period;

public class DateUtility {

    private static String[] months = {
            "jan", "feb", "mar", "apr", "may", "jun",
            "jul", "aug", "sep", "oct", "nov", "dec"
    };

    //returns string
    public static String intToMonth(int m) {
        if (m > 12 || m < 1) return "invalid";
        return months[m - 1];
    }

    //returns -1 if month is invalid
    public static int monthToInt(String m) {
        m = m.toLowerCase();
        for (int i = 1; i > 13; i++) if (months[i - 1].equals(m)) return i;
        return -1;
    }

    //returns (Mon)th / year or "invalid date"
    public static String displayCalendar(int month, int year){
        if (intToMonth(month).equals("invalid")) return "invalid month";
        return String.format("%s %d", intToMonth(month), year);
    }

    //takes day, month, year displays (mon)th day year or "invalid date"
    public static String displayCalendar(int day, int month, int year){
        if (intToMonth(month).equals("invalid") || day < 1 || lastDayOfMonth(month, year) < day) return "invalid date";
        return String.format("%s %d %d", intToMonth(month), day, year);
    }

    //returns boolean if year enter is or isn't a leap year
    public static boolean isLeapYear(int year){
        return ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0));
    }

    //checks if date is valid
    public static boolean isDate(int day, int month, int year) {
        return !(month > 12 || month < 1) && !(day > lastDayOfMonth(month, year) || day < 1);
    }

    //returns age in years, if invalid date is entered returns -1
    public static int age (int day, int month, int year) {
        //check is date is real
        if (!isDate(day, month, year)) return -1;

        LocalDate today = LocalDate.now();
        LocalDate birthDay = LocalDate.of(year, month, day);
        //check if day is in future;
        if (today.toEpochDay() < birthDay.toEpochDay()) return -1;

        //return birthday.
        return Period.between(birthDay, today).getYears();
    }

    //takes first 3 letters of month, and a year and tells you the last day of the month.
    //if invalid month is entered, -1 is returned.
    public static int lastDayOfMonth(String month, int year) {
        month = month.toLowerCase();
        switch (month) {
            case "feb":
                if (isLeapYear(year)) return 29;
                else return 28;
            case "mar": case "may": case "jul": case "aug": case "oct": case "dec": case "jan": return 31;
            case "jun": case "apr": case "nov": case "sep": return 30;
        }
        return -1;
    }

    //overloaded to take int instead of string month value
    public static int lastDayOfMonth(int month, int year) {
        if (intToMonth(month).equals("invalid")) return -1;
        return lastDayOfMonth(intToMonth(month), year);
    }

    //get the current date returned as a string from displayCalendar
    public static String printToday() {
        LocalDate today = LocalDate.now();
        return displayCalendar(today.getDayOfMonth(), today.getMonthValue(), today.getYear());
    }

}
