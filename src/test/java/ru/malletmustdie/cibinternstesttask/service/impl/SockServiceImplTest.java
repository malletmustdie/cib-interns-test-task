package ru.malletmustdie.cibinternstesttask.service.impl;

import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import ru.malletmustdie.cibinternstesttask.AbstractDataFactoryNonContext;
import ru.malletmustdie.cibinternstesttask.dto.SockDto;
import ru.malletmustdie.cibinternstesttask.exception.BusinessException;
import ru.malletmustdie.cibinternstesttask.exception.ErrorType;
import ru.malletmustdie.cibinternstesttask.filter.SocksQueryCriteria;
import ru.malletmustdie.cibinternstesttask.filter.base.IntegerFilter;
import ru.malletmustdie.cibinternstesttask.filter.base.StringFilter;
import ru.malletmustdie.cibinternstesttask.mapper.SockMapper;
import ru.malletmustdie.cibinternstesttask.model.Sock;
import ru.malletmustdie.cibinternstesttask.repository.SockRepository;
import ru.malletmustdie.cibinternstesttask.repository.custom.SockCustomRepository;
import ru.malletmustdie.cibinternstesttask.service.MessageSourceHelper;

@ExtendWith(MockitoExtension.class)
class SockServiceImplTest extends AbstractDataFactoryNonContext {

    private static final String JSON =
            "{\n"
            + "  \"color\": \"red\",\n"
            + "  \"cottonPart\": 80,\n"
            + "  \"quantity\": 3\n"
            + "}";

    @Mock
    private SockRepository sockRepository;

    @Mock
    private SockCustomRepository sockCustomRepository;

    @Mock
    private SockMapper sockMapper;

    @Mock
    private MessageSourceHelper messageSourceHelper;

    @InjectMocks
    private SockServiceImpl sockService;

    @Test
    void whenCreateIncomeOperationWithNewEntityThenReturnOk() {
        var dto = getDto();
        var entity = getEntity();
        when(sockRepository.findByColorAndCottonPart(any(), any())).thenReturn(Optional.empty());
        when(sockRepository.save(any(Sock.class))).thenReturn(entity);
        when(sockMapper.map(any(SockDto.class))).thenReturn(entity);
        var actual = sockService.createIncomeOperation(dto).getBody();
        assertThat(actual).isNotNull().isEqualTo(entity.getQuantity());
        verify(sockRepository, times(1)).save(any(Sock.class));
        verify(sockMapper, times(1)).map(any(SockDto.class));
        verifyNoMoreInteractions(sockRepository, sockCustomRepository, sockMapper, messageSourceHelper);
    }

    @Test
    void whenCreateIncomeOperationWithActualEntityThenReturnOk() {
        var dto = getDto();
        var entity = getEntity();
        var expected = entity.setQuantity(entity.getQuantity() + dto.getQuantity());
        when(sockRepository.findByColorAndCottonPart(any(), any())).thenReturn(Optional.of(entity));
        when(sockRepository.save(any(Sock.class))).thenReturn(expected);
        var actual = sockService.createIncomeOperation(dto).getBody();
        assertThat(actual).isNotNull()
                          .isEqualTo(expected.getQuantity());
        verify(sockRepository, times(1)).save(any(Sock.class));
        verifyNoMoreInteractions(sockRepository, sockCustomRepository, sockMapper, messageSourceHelper);
    }

    @Test
    void whenCreateOutcomeOperationWithActualEntityThenReturnOk() {
        var dto = getDto();
        var entity = getEntity();
        var expected = entity.setQuantity(entity.getQuantity() - dto.getQuantity());
        when(sockRepository.findByColorAndCottonPart(any(), any())).thenReturn(Optional.of(entity));
        when(sockRepository.save(any(Sock.class))).thenReturn(expected);
        var actual = sockService.createOutcomeOperation(dto).getBody();
        assertThat(actual).isNotNull()
                          .isEqualTo(expected.getQuantity());
        verify(sockRepository, times(1)).save(any(Sock.class));
        verifyNoMoreInteractions(sockRepository, sockCustomRepository, sockMapper, messageSourceHelper);
    }

    @Test
    void whenCreateOutcomeOperationWithIncorrectEntityThenThrowException() {
        var dto = getDto();
        when(sockRepository.findByColorAndCottonPart(any(), any())).thenReturn(Optional.empty());
        when(messageSourceHelper.getMessage(any(ErrorType.class), any())).thenReturn("some-msg");
        assertThatThrownBy(() -> sockService.createOutcomeOperation(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("some-msg");
        verify(messageSourceHelper, times(1)).getMessage(any(), any());
        verify(sockRepository, never()).save(any(Sock.class));
        verifyNoMoreInteractions(sockRepository, sockCustomRepository, sockMapper, messageSourceHelper);
    }

    @Test
    void whenGetAllSocksByInCorrectCriteriaThenReturnDtoList() {
        var predicate = getCriteria();
        var entities = new PageImpl<>(List.of(getEntity()));
        var dto = getDto();
        when(sockCustomRepository.getSocksByCriteria(any(SocksQueryCriteria.class), any())).thenReturn(entities);
        when(sockMapper.map(any(Sock.class))).thenReturn(dto);
        var actual = sockService.getAllSocksByCriteria(predicate, PageRequest.of(0, 100));
        assertThat(actual.getTotalElements()).isEqualTo(1L);
        assertThat(actual.getTotalPages()).isEqualTo(1L);
        assertThat(actual)
                .flatExtracting(SockDto::getColor)
                .containsExactlyInAnyOrder(entities.stream()
                                                     .map(Sock::getColor)
                                                     .toArray());
        verify(sockCustomRepository, times(1)).getSocksByCriteria(any(SocksQueryCriteria.class), any());
        verify(sockMapper, times(1)).map(any(Sock.class));
        verifyNoMoreInteractions(sockRepository, sockCustomRepository, sockMapper, messageSourceHelper);
    }

    private SockDto getDto() {
        return this.getFromString(JSON,  new TypeReference<SockDto>() {});
    }

    private Sock getEntity() {
        return this.getFromString(JSON, new TypeReference<Sock>() {});
    }

    private SocksQueryCriteria getCriteria() {
        var stringFilter = new StringFilter();
        stringFilter.setEqual("red");
        var numberFilter = new IntegerFilter();
        numberFilter.setMoreOrEqual(80);
        var criteria = new SocksQueryCriteria();
        criteria.setColor(stringFilter);
        criteria.setCottonPart(numberFilter);
        return criteria;
    }

}