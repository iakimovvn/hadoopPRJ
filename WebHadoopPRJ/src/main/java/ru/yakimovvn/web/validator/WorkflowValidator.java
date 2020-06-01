package ru.yakimovvn.web.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.yakimovvn.web.persistence.entities.Workflow;


/**
 * Created by IntelliJ Idea.
 * User: Якимов В.Н.
 * E-mail: yakimovvn@bk.ru
 */

@Service
@RequiredArgsConstructor
public class WorkflowValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Workflow.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Workflow workflow = (Workflow) target;

        if (workflow.getTitle().length() < 3 || workflow.getTitle().length() > 30)  {
            errors.rejectValue("title", "Error", "Title is not correct");
        }

    }
}
