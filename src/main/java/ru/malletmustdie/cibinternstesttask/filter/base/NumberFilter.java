package ru.malletmustdie.cibinternstesttask.filter.base;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class NumberFilter<T extends Number & Comparable<T>> extends ComparableFilter<T> {

}
