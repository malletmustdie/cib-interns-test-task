package ru.malletmustdie.cibinternstesttask.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.malletmustdie.cibinternstesttask.dto.SockDto;
import ru.malletmustdie.cibinternstesttask.exception.BusinessException;
import ru.malletmustdie.cibinternstesttask.exception.ErrorType;
import ru.malletmustdie.cibinternstesttask.filter.SocksQueryCriteria;
import ru.malletmustdie.cibinternstesttask.mapper.SockMapper;
import ru.malletmustdie.cibinternstesttask.model.Sock;
import ru.malletmustdie.cibinternstesttask.repository.SockRepository;
import ru.malletmustdie.cibinternstesttask.repository.custom.SockCustomRepository;
import ru.malletmustdie.cibinternstesttask.service.MessageSourceHelper;
import ru.malletmustdie.cibinternstesttask.service.SockService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SockServiceImpl implements SockService {

    private final SockRepository sockRepository;

    private final SockCustomRepository sockCustomRepository;

    private final SockMapper sockMapper;

    private final MessageSourceHelper messageSourceHelper;

    @Override
    @Transactional
    public ResponseEntity<Integer> createIncomeOperation(SockDto dto) {
        var result = new Sock();
        var currentSocks = sockRepository.findByColorAndCottonPart(dto.getColor(), dto.getCottonPart());
        result = currentSocks.map(sock -> incrementQuantityAndSaveSock(sock, dto, false))
                             .orElseGet(() -> sockRepository.save(sockMapper.map(dto)));
        return ResponseEntity.status(HttpStatus.OK)
                             .body(result.getQuantity());
    }

    @Override
    @Transactional
    public ResponseEntity<Integer> createOutcomeOperation(SockDto dto) {
        var currentSocks = sockRepository.findByColorAndCottonPart(dto.getColor(), dto.getCottonPart())
                                         .orElseThrow(() -> throwException(ErrorType.SOCK_NOT_FOUND,
                                                                           dto.getColor(),
                                                                           dto.getCottonPart()));
        var result = incrementQuantityAndSaveSock(currentSocks, dto, true);
        return ResponseEntity.status(HttpStatus.OK)
                             .body(result.getQuantity());
    }

    @Override
    @Transactional
    public Page<SockDto> getAllSocksByCriteria(SocksQueryCriteria criteria, Pageable pageable) {
        var pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return sockCustomRepository.getSocksByCriteria(criteria, pageRequest)
                                   .map(sockMapper::map);
    }

    private Sock incrementQuantityAndSaveSock(Sock sock, SockDto dto, Boolean decrementFlag) {
        var quantity = decrementFlag ? sock.getQuantity() - dto.getQuantity()
                                          : sock.getQuantity() + dto.getQuantity();
        sock.setQuantity(quantity);
        return sockRepository.save(sock);
    }

    private BusinessException throwException(ErrorType errorType, Object... placeholders) {
        var msg = messageSourceHelper.getMessage(errorType, placeholders);
        log.error(msg);
        return new BusinessException(errorType, msg);
    }

}
