package com.project.LiterAlura.principal;

import com.project.LiterAlura.Repositorio.IAutorRepository;
import com.project.LiterAlura.Repositorio.LivroRepository;
import com.project.LiterAlura.model.Livro;
import com.project.LiterAlura.model.LivroRespostaApi;
import com.project.LiterAlura.model.Autor;
import com.project.LiterAlura.record.DadosLivros;
import com.project.LiterAlura.service.ConsumoApi;
import com.project.LiterAlura.service.ConverterDados;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados conversor = new ConverterDados();
    private static String API_BASE = "https://gutendex.com/books/?search=";
    private List<Livro> dadosLivros = new ArrayList<>();
    private LivroRepository livroRepository;
    private IAutorRepository autorRepository;
    public Principal(LivroRepository livroRepository, IAutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public Principal() {

    }


    public void  exibirMenu(){
        var opcao = -1;
        while ( opcao !=0 ){
        var menu = """
                1 - Buscar Livro pelo titulo
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar atores vivos em determinado ano
                5 - Listar livros em determinado idioma
                
                
                0 - Sair                                 
                """;
            try {
                System.out.println(menu);
                opcao = leitura.nextInt();
                leitura.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("  Por favor, ingrese um numero valido.  ");
                leitura.nextLine();
                continue;
            }

            switch (opcao){
                case 1:
                    buscarLivroEmWeb();
                    break;
                case 2:
                    livrosBuscados();
                    break;
                case 3:
                    buscarLivroPorNome();
                    break;
                case 4:
                    BuscarAutores();
                    break;
                case 5:
                    buscarAutoresPorAno();
                    break;
                case 0:
                    System.out.println("    aplicação encerrada.    ");
                default:
                    System.out.println("   opção incorreta   ");
                    break;




            }
        }

    }

    private void buscarAutoresPorAno() {
        System.out.println("Indique o ano para verificar quais autores estão vivos: \n");
        var anoBuscado = leitura.nextInt();
        leitura.nextLine();

        List<Autor> autoresVivos = autorRepository.findByAniversarioLessThanOrDataFalecimentoGreaterThanEqual(anoBuscado, anoBuscado);

        if (autoresVivos.isEmpty()) {
            System.out.println("Não foram encontrados autores que estivessem vivos no ano " + anoBuscado + ".");
        } else {
            System.out.println("Os autores que estavam vivos no ano " + anoBuscado + "são:");
            Set<String> autoresUnicos = new HashSet<>();

            for (Autor autor : autoresVivos) {
                if (autor.getAniversario() != null && autor.getDataFalecimento() != null) {
                    if (autor.getAniversario() <= anoBuscado && autor.getDataFalecimento() >= anoBuscado) {
                        if (autoresUnicos.add(autor.getNome())) {
                            System.out.println("Autor: " + autor.getNome());
                        }
                    }
                }
            }
        }
    }

    private void BuscarAutores() {
        List<Autor> autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("Não se encontra o livro na base de dados \n");
        } else {
            System.out.println("Livros encontrado na base de dados \n");
            Set<String> autoresUnicos = new HashSet<>();
            for (Autor autor : autores) {

                if (autoresUnicos.add(autor.getNome())){
                    System.out.println(autor.getNome()+'\n');
                }
            }
        }
    }

    private void buscarLivroPorNome() {
        System.out.println("Insira o título do livro que deseja buscar: ");
        var titulo = leitura.nextLine();
        Livro livroBuscado = livroRepository.findByTituloContainsIgnoreCase(titulo);
        if (livroBuscado != null) {
            System.out.println("O livro buscado foi: "+ livroBuscado);
        } else {
            System.out.println("O livro com titulo '" + titulo + "' Não se encontra");
        }
    }

    @Transactional(readOnly = true)
    private void livrosBuscados() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("Não foram encontrados livros no banco de dados.");
        } else {
            System.out.println("Livros encontrados no banco de dados: ");
            for (Livro livro : livros) {
                System.out.println(livro.toString());
            }
        }
    }

    private Livro getDadosLivro(){
        System.out.println("DIgite o nome do livro ");
        var nomeLivro = leitura.nextLine().toLowerCase();
        var json = consumoApi.obterDados(API_BASE + nomeLivro.replace(" ", "%20"));
        //System.out.println("JSON INICIAL: " + json);
        LivroRespostaApi datos = conversor.converterDadosJsonAJava(json, LivroRespostaApi.class);

        if (datos != null && datos.getResultadoLivros() != null && !datos.getResultadoLivros().isEmpty()) {
            DadosLivros primeiroLivro = datos.getResultadoLivros().get(0); // Obter o primeiro livro da lista
            return new Livro(primeiroLivro);
        } else {
            System.out.println("Sem resultado");
            return null;
        }
    }

    private void buscarLivroEmWeb() {
        Livro livro = getDadosLivro();

        if (livro == null){
            System.out.println("livro não encontrado");
            return;
        }

        try{
            boolean livroExists = livroRepository.existsByTitulo(livro.getTitulo());
            if (livroExists){
                System.out.println("O livro já existe no banco de dados");
            }else {
                livroRepository.save(livro);
                System.out.println(livro.toString());
            }
        }catch (InvalidDataAccessApiUsageException e){
            System.out.println("Não se pode persistir em um livro buscado");
        }
    }

    }



