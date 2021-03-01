package br.com.codenation;

import br.com.codenation.exceptions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Time {
    private Long id;
    private String nome;
    private LocalDate dataCriacao;
    private String corUniformePrincipal;
    private String corUniformeSecundario;
    private List<Jogador> jogadores;

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public Time(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
        this.id = id;
        this.nome= nome;
        this.dataCriacao = dataCriacao;
        this.corUniformePrincipal = corUniformePrincipal;
        this.corUniformeSecundario = corUniformeSecundario;
        this.jogadores = new ArrayList<>();
    }
    private boolean existeId(Long id) {
        return this.jogadores.stream()
                .anyMatch(j -> j.getId().equals(id));
        // retorna verdadeiro se tiver, anymatch pelo menos um true
        // allMatch todos true etc..
    }

    private void validaJogador(Long id) {
        if (!existeId(id)) {
            throw new JogadorNaoEncontradoException();
        }
    }

    public Long buscarCapitaoDoTime() throws CapitaoNaoInformadoException {
        Optional<Jogador> optJogador = this.jogadores.stream()
    // com o option eu encapsulo o retorno e consigo usar diversos metodos

                .filter(Jogador::isCapitao) // jogador -> jogador.isCapitao()
                .findAny();// o findany retona um option
        if (optJogador.isPresent()) {
            // necessito do isPresente para verificar se o elemento esta contido
            // para me retornar um booleano, para então conseguir usar o get() que é advindo de Optional
            return optJogador.get().getId();// o metodo get() é de optional
        }
        throw new CapitaoNaoInformadoException();
    }

    public void definirCapitao(Long id) {
        validaJogador(id);
        try {
            Long idCapitao = this.buscarCapitaoDoTime();
            if (idCapitao != null && idCapitao != 0){
                jogadores.get(Math.toIntExact(idCapitao)).setCapitao(false);
                // o capitao anterior fica false
            }
        }
        catch (CapitaoNaoInformadoException e) {
            System.out.println(e);
        }
        jogadores.stream().filter(j -> j.getId().equals(id)).forEach(j -> j.setCapitao(true));
        // defino um novo capitao
    }

    public String buscarNomeJogador(Long idJogador) {
        validaJogador(id);
        return this.jogadores.get(Math.toIntExact(idJogador)).getNome();
    }

    public Long buscarMelhorJogador() {
        Optional<Jogador> optJogador = this.jogadores.stream()
                .max(Comparator.comparing(Jogador::getNivelHabilidade));
        // compara o que foi passado no lambda
        if (optJogador.isPresent()) {
            return optJogador.get().getId();
        }
        throw new JogadorNaoEncontradoException();
    }

    public Long buscarJogadorMaisVelho() {
        Optional<Jogador> optJogador = this.jogadores.stream()
                .max(Comparator.comparing(Jogador::calcularIdade));
        if (optJogador.isPresent()) {
            return optJogador.get().getId();
        }
        throw new JogadorNaoEncontradoException();

    }

    public Long buscarJogadorMaiorSalario() {
        Optional<Jogador> optJogador = this.jogadores.stream()
                .max(Comparator.comparing(Jogador::getSalario));
        if (optJogador.isPresent()) {
            return optJogador.get().getId();
        }
        throw new JogadorNaoEncontradoException();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getCorUniformePrincipal() {
        return corUniformePrincipal;
    }

    public void setCorUniformePrincipal(String corUniformePrincipal) {
        this.corUniformePrincipal = corUniformePrincipal;
    }

    public String getCorUniformeSecundario() {
        return corUniformeSecundario;
    }

    public void setCorUniformeSecundario(String corUniformeSecundario) {
        this.corUniformeSecundario = corUniformeSecundario;
    }



}
