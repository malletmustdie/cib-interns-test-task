package ru.malletmustdie.cibinternstesttask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.malletmustdie.cibinternstesttask.dto.SockDto;
import ru.malletmustdie.cibinternstesttask.filter.SocksQueryCriteria;
import ru.malletmustdie.cibinternstesttask.service.SockService;
import static ru.malletmustdie.cibinternstesttask.util.ApiPathConstants.API_V_1;
import static ru.malletmustdie.cibinternstesttask.util.ApiPathConstants.INCOME;
import static ru.malletmustdie.cibinternstesttask.util.ApiPathConstants.OUTCOME;
import static ru.malletmustdie.cibinternstesttask.util.ApiPathConstants.SOCKS;

/**
 * Контроллер для работы с операциями склада носков
 */
@Tag(name = "Socks")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = API_V_1 + SOCKS)
public class SockController {

    private final SockService sockService;

    @Operation(description = "Регистрация приход носков на склад")
    @PostMapping(INCOME)
    public ResponseEntity<Integer> income(@RequestBody @Validated SockDto dto) {
        return sockService.createIncomeOperation(dto);
    }

    @Operation(description = "Регистрация отпуска носков со склада")
    @PostMapping(OUTCOME)
    public ResponseEntity<Integer> outcome(@RequestBody @Validated SockDto dto) {
        return sockService.createOutcomeOperation(dto);
    }

    @Operation(description = "Получение общего количества носков на складе по критерию")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                         description = "OK",
                         content = {
                                 @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                          array = @ArraySchema(schema = @Schema(implementation = SockDto.class)))
                         }),
    })
    @GetMapping
    public Page<SockDto> socksByCriteria(@ParameterObject SocksQueryCriteria criteria,
                                               final Pageable pageable) {
        return sockService.getAllSocksByCriteria(criteria, pageable);
    }

}
