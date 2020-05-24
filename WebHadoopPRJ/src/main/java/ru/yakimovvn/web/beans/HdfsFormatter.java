package ru.yakimovvn.web.beans;

import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */


@Component
public class HdfsFormatter {

    public String rewriteToRoot(String path){
        return Paths.get(path).getParent().toString();
    }

    public String dateLongFormat(long longDataTime){
        Date date = new Date(longDataTime);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }


    public String sizeFormat(long sizeLong){
        String size=String.valueOf(sizeLong);
        int sizeLength = size.length();

        if(sizeLength < 4)
            return size + "bt";
        if(sizeLength < 7)
            return size.substring(0,sizeLength-4) + "kb";
        if(sizeLength < 10)
            return size.substring(0,sizeLength-7) + "mb";
        return size.substring(0,sizeLength-10) + "Gb";


    }
}
