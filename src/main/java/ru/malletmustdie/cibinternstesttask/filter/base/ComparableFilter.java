package ru.malletmustdie.cibinternstesttask.filter.base;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ComparableFilter<T extends Comparable<? super T>> extends MainFilter<T> {

    @Parameter(description = "Строго больше")
    private T moreThan;

    @Parameter(description = "Строго меньше")
    private T lessThan;

    @Parameter(description = "Больше или равно")
    private T moreOrEqual;

    @Parameter(description = "Меньше или равно")
    private T lessOrEqual;

}
