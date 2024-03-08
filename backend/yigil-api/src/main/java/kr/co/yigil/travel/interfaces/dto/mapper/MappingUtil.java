package kr.co.yigil.travel.interfaces.dto.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MappingUtil {

    public static String doubleToString(double value) {
        return String.valueOf(value);
    }

    public static String intToString(int value) {
        return String.valueOf(value);
    }

    public static String localDateTimeToString(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return dateTime.format(formatter);
    }

}
