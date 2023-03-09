package ru.malletmustdie.cibinternstesttask.repository.custom.impl;

import javax.persistence.EntityManager;

import java.util.Objects;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import ru.malletmustdie.cibinternstesttask.filter.SocksQueryCriteria;
import ru.malletmustdie.cibinternstesttask.filter.common.CommonBooleanBuilder;
import ru.malletmustdie.cibinternstesttask.model.QSock;
import ru.malletmustdie.cibinternstesttask.model.Sock;
import ru.malletmustdie.cibinternstesttask.repository.custom.SockCustomRepository;

@Repository
public class SockCustomRepositoryImpl extends QuerydslRepositorySupport implements SockCustomRepository {

    private static final QSock SOCK = QSock.sock;

    private final JPAQueryFactory queryFactory;

    private final CommonBooleanBuilder commonBooleanBuilder;

    public SockCustomRepositoryImpl(EntityManager entityManager,
                                    CommonBooleanBuilder commonBooleanBuilder) {
        super(Sock.class);
        queryFactory = new JPAQueryFactory(entityManager);
        this.commonBooleanBuilder = commonBooleanBuilder;
    }

    @Override
    public Page<Sock> getSocksByCriteria(SocksQueryCriteria criteria, Pageable pageable) {
        var query = queryFactory
                .select(SOCK)
                .from(SOCK)
                .where(getPredicate(criteria));
        var totalCount = query.fetch().size();
        var resultData = Objects.requireNonNull(getQuerydsl()).applyPagination(pageable, query).fetch();
        return new PageImpl<>(resultData, pageable, totalCount);
    }

    private BooleanBuilder getPredicate(SocksQueryCriteria criteria) {
        var predicate = new BooleanBuilder();
        commonBooleanBuilder.andMatchStringFilter(predicate, criteria.getColor(), SOCK.color);
        commonBooleanBuilder.andMatchNumberFilter(predicate, criteria.getCottonPart(), SOCK.cottonPart);
        return predicate;
    }

}
