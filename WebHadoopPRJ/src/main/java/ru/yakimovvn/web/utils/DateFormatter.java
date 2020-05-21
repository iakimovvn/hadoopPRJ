package ru.yakimovvn.web.utils;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

public class DateFormatter implements Formatter<Date> {

    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Date parse(String str, Locale locale) throws ParseException {
        return FORMATTER.parse(str);
    }

    @Override
    public String print(Date date, Locale locale) {
        return FORMATTER.format(date);
    }
}
