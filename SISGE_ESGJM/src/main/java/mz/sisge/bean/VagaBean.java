package mz.sisge.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.Vaga;
import mz.sisge.modelo.Enumeracao.EnumPeriodo;
import mz.sisge.repositorio.VagaRepo;
import mz.sisge.service.VagaService;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class VagaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Vaga vagaCadastro;
	@Inject
	private Vaga vagaSelecionada;

	@Inject
	private VagaRepo vagaRepo;
	@Inject
	private VagaService vagaService;

	private List<Vaga> vagasLista;
	private List<Vaga> vagasFiltro;

	// GETS E SETS
	public Vaga getVagaCadastro() {
		return vagaCadastro;
	}

	public void setVagaCadastro(Vaga vagaCadastro) {
		this.vagaCadastro = vagaCadastro;
	}

	public Vaga getVagaSelecionada() {
		return vagaSelecionada;
	}

	public void setVagaSelecionada(Vaga vagaSelecionada) {
		this.vagaSelecionada = vagaSelecionada;
	}

	public List<Vaga> getVagasLista() {
		return vagasLista;
	}

	public void setVagasLista(List<Vaga> vagasLista) {
		this.vagasLista = vagasLista;
	}

	public List<Vaga> getVagasFiltro() {
		return vagasFiltro;
	}

	public void setVagasFiltro(List<Vaga> vagasFiltro) {
		this.vagasFiltro = vagasFiltro;
	}

	public EnumPeriodo[] getPeriodos() {
		return EnumPeriodo.values();
	}

	// METODOS
	public void guardar() {
		try {
			vagaService.guardar(vagaCadastro);
			vagaCadastro = new Vaga();
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	@PostConstruct
	public void listar() {
		vagasLista = vagaRepo.listarVagas();
	}

	public void apagar() {
		try {
			vagaCadastro = vagaRepo.buscar(vagaCadastro.getCodigo());
			vagaRepo.apagar(vagaCadastro);
			FacesUtil.adicionarMensagemInfo("Registro removido");
		} catch (RuntimeException e) {
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}
}
