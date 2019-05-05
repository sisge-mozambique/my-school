package mz.sisge.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.Disciplina;
import mz.sisge.modelo.Enumeracao.EnumDisciplinaGrupo;
import mz.sisge.repositorio.DisciplinaRepo;
import mz.sisge.service.DisciplinaService;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class DisciplinaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Disciplina disciplinaCadastro;
	@Inject
	private Disciplina disciplinaSelecionada;
	@Inject
	private DisciplinaRepo disciplinaRepo;
	@Inject
	private DisciplinaService disciplinaService;

	// LISTAS
	private List<Disciplina> disciplinasLista;
	private List<Disciplina> disciplinasFiltro;

	// GETS e SETS
	public Disciplina getDisciplinaCadastro() {
		return disciplinaCadastro;
	}

	public void setDisciplinaCadastro(Disciplina disciplinaCadastro) {
		this.disciplinaCadastro = disciplinaCadastro;
	}

	public List<Disciplina> getDisciplinasLista() {
		return disciplinasLista;
	}

	public void setDisciplinasLista(List<Disciplina> disciplinasLista) {
		this.disciplinasLista = disciplinasLista;
	}

	public List<Disciplina> getDisciplinasFiltro() {
		return disciplinasFiltro;
	}

	public void setDisciplinasFiltro(List<Disciplina> disciplinasFiltro) {
		this.disciplinasFiltro = disciplinasFiltro;
	}

	public EnumDisciplinaGrupo[] getGrupo() {
		return EnumDisciplinaGrupo.values();
	}

	public Disciplina getDisciplinaSelecionada() {
		return disciplinaSelecionada;
	}

	public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
		this.disciplinaSelecionada = disciplinaSelecionada;
	}

	// METODOS
	public void guardar() {
		try {
			disciplinaService.guardar(disciplinaCadastro);
			disciplinaCadastro = new Disciplina();
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	@PostConstruct
	public void listar() {
		disciplinasLista = disciplinaRepo.listarDisciplinas();
	}

	public void apagar() {
		try {
			disciplinaCadastro = disciplinaRepo.buscar(disciplinaCadastro
					.getCodigo());
			disciplinaRepo.apagar(disciplinaCadastro);
			FacesUtil.adicionarMensagemInfo("Registro removido");
		} catch (RuntimeException e) {
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}

}
