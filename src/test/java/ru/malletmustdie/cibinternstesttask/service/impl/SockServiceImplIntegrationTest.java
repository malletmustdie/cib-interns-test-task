package ru.malletmustdie.cibinternstesttask.service.impl;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.core.connection.RiderDataSource;
import com.github.database.rider.spring.api.DBRider;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import ru.malletmustdie.cibinternstesttask.TestDataFactory;
import ru.malletmustdie.cibinternstesttask.annotation.IntegrationTest;
import ru.malletmustdie.cibinternstesttask.filter.SocksQueryCriteria;
import ru.malletmustdie.cibinternstesttask.filter.base.IntegerFilter;
import ru.malletmustdie.cibinternstesttask.filter.base.StringFilter;
import ru.malletmustdie.cibinternstesttask.repository.SockRepository;

//@IntegrationTest
@SpringBootTest(properties = "spring.liquibase.enabled=false")
@DataSet(executeScriptsBefore = "reset_seq.sql")
@DBUnit(caseSensitiveTableNames = true, expectedDbType = RiderDataSource.DBType.H2, alwaysCleanBefore = true)
@DBRider
class SockServiceImplIntegrationTest {

    @Autowired
    private TestDataFactory dataFactory;

    @Autowired
    private SockServiceImpl sockService;

    @Test
    @DataSet(value = "data/yml/incomeOperationWithNewSock.yml")
    @ExpectedDataSet(value = "data/yml/incomeOperationWithNewSockExpected.yml",
                     ignoreCols = {"created_at", "updated_at"})
    void whenCreateNewIncomeOperationThenReturnOneNewRow() {
        var dto = dataFactory.sockDto();
        var actual = sockService.createIncomeOperation(dto);
        assertThat(actual.getBody())
                .isNotNull()
                .isEqualTo(dto.getQuantity());
    }

    @Test
    @DataSet(value = "data/yml/incomeOperationWithCurrentSock.yml")
    @ExpectedDataSet(value = "data/yml/incomeOperationWithCurrentSockExpected.yml",
                     ignoreCols = {"created_at", "updated_at"})
    void whenCreateNewCurrentIncomeOperationThenReturnUpdatedRow() {
        var dto = dataFactory.sockDto();
        var actual = sockService.createIncomeOperation(dto);
        assertThat(actual.getBody())
                .isNotNull()
                .isEqualTo(6);
    }

    @Test
    @DataSet(value = "data/yml/outcomeOperationWithCurrentSock.yml")
    @ExpectedDataSet(value = "data/yml/outcomeOperationWithCurrentSockExpected.yml",
                     ignoreCols = {"created_at", "updated_at"})
    void whenCreateOutcomeOperationThenReturnUpdatedRow() {
        var dto = dataFactory.sockDto();
        dto.setQuantity(7);
        var actual = sockService.createOutcomeOperation(dto);
        assertThat(actual.getBody())
                .isNotNull()
                .isEqualTo(3);
    }

    @Test
    @DataSet(value = "data/yml/ethalonData.yml")
    void whenGetAllSocksByCriteriaTheReturnOneRow() {
        var predicate = getCriteria();
        var actual = sockService.getAllSocksByCriteria(predicate, PageRequest.of(0, 100));
        assertThat(actual.getTotalElements()).isEqualTo(1L);
        assertThat(actual.getTotalPages()).isEqualTo(1L);
        assertThat(actual)
                .filteredOn(dto -> dto.getColor().equals("red")
                        && dto.getQuantity().equals(2)
                        && dto.getCottonPart().equals(80))
                .hasSize(1);
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