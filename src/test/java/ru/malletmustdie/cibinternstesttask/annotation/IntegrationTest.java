package ru.malletmustdie.cibinternstesttask.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.connection.RiderDataSource;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.malletmustdie.cibinternstesttask.extension.PostgresContainerExtension;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(properties = "spring.liquibase.enabled=false")
@DataSet(executeScriptsBefore = "reset_seq.sql")
@DBUnit(caseSensitiveTableNames = true, expectedDbType = RiderDataSource.DBType.H2, alwaysCleanBefore = true)
@DBRider
public @interface IntegrationTest {
}
