package com.project.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.LiterAlura.record.DadosLivros;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LivroRespostaApi {

    @JsonAlias("results")
    List<DadosLivros> resultadoLivros;

    public List<DadosLivros> getResultadoLivros() {
        return resultadoLivros;
    }

    public void setResultadoLivros(List<DadosLivros> resultadoLivros) {
        this.resultadoLivros = resultadoLivros;
    }

}
