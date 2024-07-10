package com.project.LiterAlura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer aniversario;

    private Integer dataFalecimento;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    //@Transient
    private List<Livro> livros;


    public Autor(com.project.LiterAlura.record.Autor autor) {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Integer getAniversario() {
        return aniversario;
    }

    public Integer getDataFalecimento() {
        return dataFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public Autor(com.project.LiterAlura.model.Autor autor) {
        this.nome = autor.nome;
        this.aniversario = autor.aniversario;
        this.dataFalecimento = autor.dataFalecimento;
    }

    @Override
    public String toString() {
        return
                "nome='" + nome + '\'' +
                        ", aniversario=" + aniversario +
                        ", dataFalecimento=" + dataFalecimento;
    }
}
