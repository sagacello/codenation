package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class Jogador {
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private Integer nivelHabilidade;
    private BigDecimal salario;
    private boolean isCapitao;

    Jogador(Long id, String nome, LocalDate dataNascimento
            , Integer nivelHabilidade, BigDecimal salario) {
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.nivelHabilidade = nivelHabilidade;
        this.salario = salario;
        this.isCapitao = false;
    }

    public int calcularIdade() {

        return Period.between(this.dataNascimento, LocalDate.now()).getYears();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Integer getNivelHabilidade() {
        return this.nivelHabilidade;
    }

    public void setNivelHabilidade(int nivelHabilidade) {
        this.nivelHabilidade = nivelHabilidade;
    }

    public BigDecimal getSalario() {
        return this.salario;
    }

    public void setId(BigDecimal salario) {
        this.salario = salario;
    }

    public boolean isCapitao() {
        return this.isCapitao;
    }

    public void setCapitao(boolean isCapitao) {
        this.isCapitao = isCapitao;
    }
}