package com.project.LiterAlura.Repositorio;

import com.project.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    boolean existsByTitulo(String titulo);


    Livro findByTituloContainsIgnoreCase(String titulo);





    @Query("SELECT l FROM Livro l ORDER BY l.quantidadeDownloads DESC LIMIT 10")
    List<Livro> findTop10ByTituloByquantidadeDownloads();
}
