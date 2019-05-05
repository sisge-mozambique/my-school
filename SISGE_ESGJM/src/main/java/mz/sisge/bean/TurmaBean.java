package mz.sisge.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.Turma;
import mz.sisge.modelo.Enumeracao.EnumPeriodo;
import mz.sisge.modelo.Enumeracao.EnumTurma;
import mz.sisge.repositorio.TurmaRepo;
import mz.sisge.service.TurmaServico;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class TurmaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Turma turmaCadastro;
	@Inject
	private Turma turmaSelecionada;

	@Inject
	private TurmaRepo turmaRepo;
	@Inject
	private TurmaServico turmaServico;

	private List<Turma> turmasLista;
	private List<Turma> turmasFiltro;

	// PARAMS FILTRO
	private int anoLectivo;
	private Long classe;

	// GETS E SETS
	public Turma getTurmaCadastro() {
		return turmaCadastro;
	}

	public void setTurmaCadastro(Turma turmaCadastro) {
		this.turmaCadastro = turmaCadastro;
	}

	public Turma getTurmaSelecionada() {
		return turmaSelecionada;
	}

	public void setTurmaSelecionada(Turma turmaSelecionada) {
		this.turmaSelecionada = turmaSelecionada;
	}

	public List<Turma> getTurmasLista() {
		return turmasLista;
	}

	public void setTurmasLista(List<Turma> turmasLista) {
		this.turmasLista = turmasLista;
	}

	public List<Turma> getTurmasFiltro() {
		return turmasFiltro;
	}

	public void setTurmasFiltro(List<Turma> turmasFiltro) {
		this.turmasFiltro = turmasFiltro;
	}

	public int getAnoLectivo() {
		return anoLectivo;
	}

	public void setAnoLectivo(int anoLectivo) {
		this.anoLectivo = anoLectivo;
	}

	public Long getClasse() {
		return classe;
	}

	public void setClasse(Long classe) {
		this.classe = classe;
	}

	public EnumTurma[] getEnumTurmas() {
		return EnumTurma.values();
	}

	public EnumPeriodo[] getPeriodos() {
		return EnumPeriodo.values();
	}

	// METODOS
	@PostConstruct
	public void listar() {
		turmasLista = turmaRepo.listarTurmas();
	}

	public void guardar() {
		try {
			this.turmaServico.guardar(this.turmaCadastro);
			turmaCadastro = new Turma();
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	public void apagar() {
		try {
			turmaCadastro = turmaRepo.buscar(turmaCadastro.getCodigo());
			turmaRepo.apagar(turmaCadastro);
			FacesUtil.adicionarMensagemInfo("Registro removido");
		} catch (RuntimeException e) {
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}

}
