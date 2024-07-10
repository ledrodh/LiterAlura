package com.project.LiterAlura.service;

public interface IConverteDados {
    <T> T converterDadosJsonAJava(String json , Class<T> clase);
}
