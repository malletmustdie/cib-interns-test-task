package ru.malletmustdie.cibinternstesttask.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.malletmustdie.cibinternstesttask.filter.SocksQueryCriteria;
import ru.malletmustdie.cibinternstesttask.model.Sock;

public interface SockCustomRepository {

    Page<Sock> getSocksByCriteria(SocksQueryCriteria criteria, Pageable pageable);

}
