package id.backend.todo_list.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeUtils {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static Date doGetDateFormat(String date) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime localDate = LocalDateTime.parse(date, formatter);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date resultFormatted = sdf.parse(localDate.format(formatter));
        return resultFormatted;
    }

}
