package mz.sisge.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.Sala;
import mz.sisge.modelo.Enumeracao.EnumBloco;
import mz.sisge.repositorio.SalaRepo;
import mz.sisge.service.SalaService;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class SalaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Sala salaCadastro;
	@Inject
	private Sala salaSelecionada;

	@Inject
	private SalaRepo salaRepo;
	@Inject
	private SalaService salaService;

	private List<Sala> salasLista;
	private List<Sala> salasFiltro;

	// GETS E SETS
	public Sala getSalaCadastro() {
		return salaCadastro;
	}

	public void setSalaCadastro(Sala salaCadastro) {
		this.salaCadastro = salaCadastro;
	}

	public Sala getSalaSelecionada() {
		return salaSelecionada;
	}

	public void setSalaSelecionada(Sala salaSelecionada) {
		this.salaSelecionada = salaSelecionada;
	}

	public List<Sala> getSalasLista() {
		return salasLista;
	}

	public void setSalasLista(List<Sala> salasLista) {
		this.salasLista = salasLista;
	}

	public List<Sala> getSalasFiltro() {
		return salasFiltro;
	}

	public void setSalasFiltro(List<Sala> salasFiltro) {
		this.salasFiltro = salasFiltro;
	}

	public EnumBloco[] getBlocos() {
		return EnumBloco.values();
	}

	// METODOS
	public void guardar() {
		try {
			salaService.guardar(salaCadastro);
			salaCadastro = new Sala();
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	@PostConstruct
	public void listar() {
		salasLista = salaRepo.listarSalas();
	}

	public void apagar() {
		try {
			salaCadastro = salaRepo.buscar(salaCadastro.getCodigo());
			salaRepo.apagar(salaCadastro);
			FacesUtil.adicionarMensagemInfo("Registro removido");
		} catch (RuntimeException e) {
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}

}