package com.project.LiterAlura.dtos;

public enum Genero {

    ACAO ("Action"),
    ROMANCE ("Romance"),
    CRIME ("Crime"),
    COMEDIA ("Comedy"),
    DRAMA ("Drama"),
    AVENTURA ("Adventure"),
    FICCAO ("Fiction"),
    DESCONHECIDO("Desconocido");

    private String genero;

    Genero(String generoGutendex) {
        this.genero = generoGutendex;
    }

    public static Genero fromString(String text){
        for (Genero generoEnum: Genero.values()){
            if (generoEnum.genero.equals(text)){
                return generoEnum;
            }
        }
        return Genero.DESCONHECIDO;
    }
}
