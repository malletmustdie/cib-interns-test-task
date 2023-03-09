package ru.malletmustdie.cibinternstesttask.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import ru.malletmustdie.cibinternstesttask.dto.SockDto;
import ru.malletmustdie.cibinternstesttask.filter.SocksQueryCriteria;

public interface SockService {

    ResponseEntity<Integer> createIncomeOperation(SockDto dto);

    ResponseEntity<Integer> createOutcomeOperation(SockDto dto);

    Page<SockDto> getAllSocksByCriteria(SocksQueryCriteria criteria, Pageable pageable);

}
