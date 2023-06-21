package com.example.tasktodoapp.annot;


import com.example.tasktodoapp.validators.DateRangeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Repeatable(MultiDateRange.class)
@Documented
public @interface DateRange {
    String message() default "Deadline of the Task cannot be after deadline of the list";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startDate();

    String endDate();

    String date();
}