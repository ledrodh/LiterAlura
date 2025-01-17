package com.project.LiterAlura.record;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosLivros(
        @JsonAlias("id") Long livroId,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors")
        List<Autor> autor,
        @JsonAlias("subjects")  List<String> genero,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("formats") Media imagen,
        @JsonAlias("download_count") Long quantidadeDownloads
) {

}
