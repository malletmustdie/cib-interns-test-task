package ru.malletmustdie.cibinternstesttask.repository;

import com.github.database.rider.core.api.dataset.DataSet;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.malletmustdie.cibinternstesttask.annotation.IntegrationTest;

@IntegrationTest
class SockRepositoryTest {

    public static final String COLOR = "red";

    public static final Integer COTTON_PART = 80;

    @Autowired
    private SockRepository sockRepository;

    @DataSet(value = "data/yml/ethalonData.yml")
    @Test
    void findByColorAndCottonPart() {
        var actual = sockRepository.findByColorAndCottonPart(COLOR, COTTON_PART);
        assertThat(actual)
                .hasValueSatisfying(sock -> {
                    assertThat(sock).isNotNull();
                    assertThat(sock.getColor()).isEqualTo(COLOR);
                    assertThat(sock.getCottonPart()).isEqualTo(COTTON_PART);
                });
    }

}