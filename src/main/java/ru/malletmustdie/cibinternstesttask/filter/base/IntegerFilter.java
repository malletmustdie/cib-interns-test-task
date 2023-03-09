package ru.malletmustdie.cibinternstesttask.filter.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Schema(description = "Фильтр параметров типа Integer")
public class IntegerFilter extends NumberFilter<Integer> {
}
