package com.example.tasktodoapp.validators;

import com.example.tasktodoapp.annot.DateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;


import java.util.Calendar;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {
    private String startDateField;
    private String endDateField;
    private String dateField;

    @Override
    public void initialize(DateRange constraintAnnotation) {
        startDateField = constraintAnnotation.startDate();
        endDateField = constraintAnnotation.endDate();
        dateField = constraintAnnotation.date();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper wrapper = new BeanWrapperImpl(value);

        //System.out.println(wrapper.);


        System.out.println("iside isVAlid on the custom validator");
        if (wrapper.getPropertyValue("list") == null) {
            return true;
        }

        if (wrapper.getPropertyValue(dateField) == null || wrapper.getPropertyValue(endDateField) == null || wrapper.getPropertyValue(startDateField) == null) {
            return true;
        }

        System.out.println("list is not null");

        Calendar startDate = (Calendar) wrapper.getPropertyValue(startDateField);
        Calendar endDate = (Calendar) wrapper.getPropertyValue(endDateField);
        Calendar date = (Calendar) wrapper.getPropertyValue(dateField);

        if (startDate == null || endDate == null) {
            return true; // Null values are handled by @NotNull
        }

        if ((date.after(endDate) && !date.equals(endDate)) || date.before(startDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode(dateField)
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
