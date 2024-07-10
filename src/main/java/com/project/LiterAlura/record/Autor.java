package com.project.LiterAlura.record;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.core.annotation.AliasFor;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Autor(
        @JsonAlias("name") String nome,
        @JsonAlias("birth_year") Integer aniversario,
        @JsonAlias("death_year") Integer dataFalecimento

) {
}
