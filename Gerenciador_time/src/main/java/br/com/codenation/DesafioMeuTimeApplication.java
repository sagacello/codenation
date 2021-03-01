package br.com.codenation;

import br.com.codenation.exceptions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
//https://medium.com/@racc.costa/optional-no-java-8-e-no-java-9-7c52c4b797f1

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private List<Time> times ;
	public DesafioMeuTimeApplication() {
		this.times = new ArrayList<>();
	}

	private boolean existe(Long idTime) {
		// existe time
		return times.stream().anyMatch(t -> t.getId().equals(idTime));
	}

	public boolean existe(Long idTime, Long idJogador) {
		// verifica se existe um jogador se existir um time
		// e se o jogador está dentro do time
		Optional<Time> optTime = times.stream()
				//o filtro potencialmente retorna uma lista de times, o findAny pega qualquer item da lista
				// para poder tirar da lista preciso do findAny
				.filter(t -> t.getId().equals(idTime))
				.findAny();
		if (optTime.isPresent()) {
			Time time = optTime.get();// time receb o optional do time com o get
			Optional<Jogador> optJogador = time.getJogadores().stream()
					.filter(j -> j.getId().equals(idJogador))
					.findAny();
			return optJogador.isPresent();
		}
		return false;
	}

	private boolean existeJogador(Long idJogador) {
		// existe jogador
		return times.stream()
				.flatMap(t -> t.getJogadores().stream())
				// flatMap ja desce dois nives na lista , mais dificil de usar!
				.anyMatch(j -> j.getId().equals(idJogador));
	}

	private long buscarIdTimeDoJogador(Long idJogador) {
		//basicamente esse metodo vai ver pra cada time se entre os jogadores do time
		// testado está o jogador passado como parâmetro. Se tiver, ele retorna o id do time,
		// se não diz que esse jogador não tá em nenhum time
		// com o metodo optio eu tenho sempre que informar se o valor esta presente ou ausente
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getJogadores().stream().anyMatch(j -> j.getId().equals(idJogador)))
				// o anyMatch vai me retornar true se pelo menos um elemento der match com i id passado
				.findAny(); // retorna qualquer elemento de um Stream , como so vai existir um id , me retorna ele
		if (optTime.isPresent()) {
			return optTime.get().getId();// retorna o id
		}
		throw new JogadorNaoEncontradoException();
	}

	private void validaTime(Long id) {
		if (this.existe(id)) {
			throw new IdentificadorUtilizadoException();
		}
	}

	private void validaJogador(Long idTime, Long idJogador) {
		if (this.existe(idTime, idJogador)) {
			throw new IdentificadorUtilizadoException();
		}
	}

	private void validaJogador(Long id) {
		if (!this.existeJogador(id)) {
			throw new JogadorNaoEncontradoException();
		}
	}

	private void existeTime(Long id) {
		if (!existe(id)) {
			throw new TimeNaoEncontradoException();
		}
	}

	public void incluirTime(Long id, String nome, LocalDate dataCriacao
			, String corUniformePrincipal, String corUniformeSecundario) {
		validaTime(id);
		times.add(new Time(id, nome, dataCriacao
				, corUniformePrincipal, corUniformeSecundario));
	}

	public void incluirJogador(Long id, Long idTime, String nome
			, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		existeTime(idTime);
		validaJogador(idTime, id);
		//stream().filter(t -> t.getId().equals(idTime)).
		times.forEach(t -> t.getJogadores().add(new Jogador(id, nome, dataNascimento, nivelHabilidade, salario)));
	}

	public void definirCapitao(Long idJogador) {
		validaJogador(idJogador);
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getId().equals(buscarIdTimeDoJogador(idJogador))).findAny();
		optTime.ifPresent(time -> time.definirCapitao(idJogador));

	}

	public Long buscarCapitaoDoTime(Long idTime) {
		existeTime(idTime);
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getId().equals(idTime)).findAny();
		if (optTime.isPresent()) {
			return optTime.get().buscarCapitaoDoTime();
		}
		throw new CapitaoNaoInformadoException();
	}

	public String buscarNomeJogador(Long idJogador) {
		validaJogador(idJogador);
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getId().equals(buscarIdTimeDoJogador(idJogador))).findAny();
		if (optTime.isPresent()) {
			Optional<Jogador> optJogador = optTime.get()
					.getJogadores().stream()
					.filter(j -> j.getId()
							.equals(idJogador))
					.findAny();
			if (optJogador.isPresent()) {
				return optJogador.get().getNome();
			}
		}
		throw new TimeNaoEncontradoException();
	}

	public String buscarNomeTime(Long idTime) {
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getId().equals(idTime)).findAny();
		if(optTime.isPresent()) {
			return optTime.get().getNome();
		}
		throw new TimeNaoEncontradoException();
	}

	public List<Long> buscarJogadoresDoTime(Long idTime) {
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getId().equals(idTime)).findAny();
		if(optTime.isPresent()) {
			return optTime.get().getJogadores().stream()
					.map(Jogador::getId).collect(Collectors.toList());
			// convertendo stream de volta para lista.
		}
		throw new TimeNaoEncontradoException();
	}

	public Long buscarMelhorJogadorDoTime(Long idTime) {
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getId().equals(idTime)).findAny();
		if(optTime.isPresent()) {
			Optional<Long> optIdJogador =
					optTime.get().getJogadores().stream()
							.sorted(Comparator.comparing(Jogador::getNivelHabilidade).reversed())
							// o reversed retorna o maior valor
							.map(Jogador::getId).findFirst();
			if(optIdJogador.isPresent()) {
				return optIdJogador.get();
			}

		}
		throw new TimeNaoEncontradoException();
	}

	public Long buscarJogadorMaisVelho(Long idTime) {
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getId().equals(idTime)).findAny();
		if(optTime.isPresent()) {
			Optional<Long> optIdJogador =
					optTime.get().getJogadores().stream()
							.sorted(Comparator.comparing(Jogador::getDataNascimento))
							// sem o reversed para me retornar o mais novo
							.map(Jogador::getId).findFirst();
			if(optIdJogador.isPresent()) {
				return optIdJogador.get();
			}
		}
		throw new TimeNaoEncontradoException();
	}

	public List<Long> buscarTimes() {
		return times.stream().map(Time::getId).sorted().collect(Collectors.toList());
	}

	public Long buscarJogadorMaiorSalario(Long idTime) {
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getId().equals(idTime)).findAny();
		if(optTime.isPresent()) {
			Optional<Long> optIdJogador =
					optTime.get().getJogadores().stream()
							.sorted(Comparator.comparing(Jogador::getSalario).reversed())
							.map(Jogador::getId).findFirst();
			if(optIdJogador.isPresent()) {
				return optIdJogador.get();
			}

		}
		throw new TimeNaoEncontradoException();
	}

	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		validaJogador(idJogador);
		Optional<Time> optTime = times.stream()
				.filter(t -> t.getId().equals(buscarIdTimeDoJogador(idJogador))).findAny();
		if (optTime.isPresent()) {
			Optional<Jogador> optJogador = optTime.get()
					.getJogadores().stream()
					.filter(j -> j.getId()
							.equals(idJogador))
					.findAny();
			if (optJogador.isPresent()) {
				return optJogador.get().getSalario();
			}
		}
		throw new TimeNaoEncontradoException();
	}

	public List<Long> buscarTopJogadores(Integer top) {
		return times.stream()
				.flatMap(t -> t.getJogadores().stream())
				.sorted(Comparator.comparing(Jogador::getSalario).reversed()) // Ordena e compara os salarios , depois coloca em Desc
				.map(Jogador::getId)
				.limit(top) // limitar o resultado pelo numero de elementos, limit
				.collect(Collectors.toList());
	}
}