package com.project.LiterAlura.Repositorio;

import com.project.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IAutorRepository extends JpaRepository<Autor,Long> {

    List<Autor> findAll();


    List<Autor> findByAniversarioLessThanOrDataFalecimentoGreaterThanEqual(int anoBuscado, int anoBuscado1);

    Optional<Autor> findFirstByNomeContainsIgnoreCase(String escritor);
}
