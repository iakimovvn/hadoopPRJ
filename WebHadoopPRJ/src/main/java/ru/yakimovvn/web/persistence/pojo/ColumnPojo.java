package ru.yakimovvn.web.persistence.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Data
@Builder
public class ColumnPojo {
    private String name;
    private String type;
}
