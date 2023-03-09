package ru.malletmustdie.cibinternstesttask.filter;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.malletmustdie.cibinternstesttask.filter.base.IntegerFilter;
import ru.malletmustdie.cibinternstesttask.filter.base.StringFilter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Объект фильтрации, содержащий общие поля для запроса на получение носков")
public class SocksQueryCriteria {

    private StringFilter color;

    private IntegerFilter cottonPart;

}
