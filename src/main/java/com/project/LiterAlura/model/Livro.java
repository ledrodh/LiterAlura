package com.project.LiterAlura.model;

import com.project.LiterAlura.dtos.Genero;
import com.project.LiterAlura.record.DadosLivros;
import com.project.LiterAlura.record.Media;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long livroId;

    @Column(unique = true)
    private String titulo;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL) // Asegura que el autor se guarde automáticamente
    @JoinColumn(name = "autor_id")
    //@Transient
    private Autor autor;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String idioma;
    private String imagen;
    private Long quantidadeDownloads;

    public Livro() {
    }

    public Livro(DadosLivros dadosLivros) {
        this.livroId = dadosLivros.livroId();
        this.titulo = dadosLivros.titulo();
        // Se autor é uma lista de autores (como parece em seu registro DadosLivro)
        if (dadosLivros.autor() != null && !dadosLivros.autor().isEmpty()) {
            this.autor = new Autor(dadosLivros.autor().get(0)); // Pega o primeiro autor da lista.
        } else {
            this.autor = null; // ou trate o caso de não haver autor.
        }
        this.genero =  generoModificado(dadosLivros.genero());
        this.idioma = idiomaModificado(dadosLivros.idioma());
        this.imagen = imagenModificada(dadosLivros.imagen());
        this.quantidadeDownloads = dadosLivros.quantidadeDownloads();
    }

    public Livro(Livro livro) {
    }

    private Genero generoModificado(List<String> generos) {
        if (generos == null || generos.isEmpty()) {
            return Genero.DESCONHECIDO;
        }
        Optional<String> firstGenero = generos.stream()
                .map(g -> {
                    int index = g.indexOf("--");
                    return index != -1 ? g.substring(index + 2).trim() : null;
                })
                .filter(Objects::nonNull)
                .findFirst();
        return firstGenero.map(Genero::fromString).orElse(Genero.DESCONHECIDO);
    }

    private String idiomaModificado(List<String> idiomas) {
        if (idiomas == null || idiomas.isEmpty()) {
            return "Desconhecido";
        }
        return idiomas.get(0);
    }

    private String imagenModificada(Media media) {
        if (media == null || media.imagen().isEmpty()) {
            return "Sem imagen";
        }
        return media.imagen();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLivroId() {
        return livroId;
    }

    public void setLivroId(Long livroId) {
        this.livroId = livroId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getQuantidadeDownloads() {
        return quantidadeDownloads;
    }

    public void setQuantidadeDownloads(Long quantidadeDownloads) {
        this.quantidadeDownloads = quantidadeDownloads;
    }
    @Override
    public String toString() {
        return
                "  \nid=" + id +
                        "  \nLibro id=" + livroId +
                        ", \ntitulo='" + titulo + '\'' +
                        ", \nauthors=" + (autor != null ? autor.getNome() : "N/A")+
                        ", \ngenero=" + genero +
                        ", \nidioma=" + idioma +
                        ", \nimagen=" + imagen +
                        ", \ncantidadDescargas=" + quantidadeDownloads;
    }
}
