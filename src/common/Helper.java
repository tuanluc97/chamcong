package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
    public static boolean checkInvalidFomat(SimpleDateFormat dateFormat, String dateStr) {
        try {
            Date date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
}
