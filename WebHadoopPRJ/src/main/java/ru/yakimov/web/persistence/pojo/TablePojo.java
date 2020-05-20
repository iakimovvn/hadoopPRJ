package ru.yakimov.web.persistence.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Data
@Builder
public class TablePojo {
    private String name;
    private List<ColumnPojo> primaries;
    private String databaseUrl;
    private String username;
    private String password;

}
