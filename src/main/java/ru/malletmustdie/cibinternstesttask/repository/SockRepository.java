package ru.malletmustdie.cibinternstesttask.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.malletmustdie.cibinternstesttask.model.Sock;

@Repository
public interface SockRepository extends JpaRepository<Sock, Long> {

    Optional<Sock> findByColorAndCottonPart(String color, Integer cottonPart);

}
