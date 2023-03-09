package ru.malletmustdie.cibinternstesttask.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Schema(description = "Объект передачи данных носков")
public class SockDto {

    @Parameter(description = "Цвет носков", example = "Red")
    @NotBlank(message = "Поля не могут быть пустым")
    @NotNull(message = "Поля не могут равняться null")
    private String color;

    @Parameter(description = "Процента хлопка в составе носков", example = "70")
    @Min(value = 0, message = "Содержание хлопка должно больше 0")
    @Max(value = 100, message = "Содержание хлопка должно не меньше 100")
    @NotNull(message = "Поля не могут равняться null")
    private Integer cottonPart;

    @Parameter(description = "Количество пар носков", example = "3")
    @Min(value = 1, message = "Количество пар не может быть меньше 1")
    @NotNull(message = "Поля не могут равняться null")
    private Integer quantity;

}
