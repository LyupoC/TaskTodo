package com.example.tasktodoapp.annot;


import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MultiDateRange {
    DateRange[] value();
}
