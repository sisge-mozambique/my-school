package mz.sisge.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.Disciplina_Classe;
import mz.sisge.modelo.Professor_Disciplina_Classe;
import mz.sisge.repositorio.Disciplina_ClasseRepo;
import mz.sisge.repositorio.Pro_Dis_ClasseRepo;
import mz.sisge.service.Professor_Disc_Service;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class Prof_DisciplinaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Professor_Disciplina_Classe professorDisciplinaCadastro;
	@Inject
	private Professor_Disciplina_Classe professorDisciplinaSelecao;
	@Inject
	private Pro_Dis_ClasseRepo professorDisciplinaRepo;
	@Inject
	private Professor_Disc_Service professor_Disc_Service;

	@Inject
	private Disciplina_ClasseRepo disciplina_ClasseRepo;
	@Inject
	private Disciplina_Classe disciplina_Classe;

	private List<Disciplina_Classe> disciplinasClasseLista;
	private List<Disciplina_Classe> disciplinasClasseFiltro;
	private List<Professor_Disciplina_Classe> professoresDisciplinaLista;
	private List<Professor_Disciplina_Classe> professoresDisciplinaFiltro;

	// PARAMS FILTRO
	private Long professor;
	private Long classe;

	// GETS E SETS

	public Long getClasse() {
		return classe;
	}

	public void setClasse(Long classe) {
		this.classe = classe;
	}

	public Professor_Disciplina_Classe getProfessorDisciplinaCadastro() {
		return professorDisciplinaCadastro;
	}

	public void setProfessorDisciplinaCadastro(
			Professor_Disciplina_Classe professorDisciplinaCadastro) {
		this.professorDisciplinaCadastro = professorDisciplinaCadastro;
	}

	public Professor_Disciplina_Classe getProfessorDisciplinaSelecao() {
		return professorDisciplinaSelecao;
	}

	public void setProfessorDisciplinaSelecao(
			Professor_Disciplina_Classe professorDisciplinaSelecao) {
		this.professorDisciplinaSelecao = professorDisciplinaSelecao;
	}

	public Disciplina_Classe getDisciplina_Classe() {
		return disciplina_Classe;
	}

	public void setDisciplina_Classe(Disciplina_Classe disciplina_Classe) {
		this.disciplina_Classe = disciplina_Classe;
	}

	public List<Disciplina_Classe> getDisciplinasClasseLista() {
		return disciplinasClasseLista;
	}

	public void setDisciplinasClasseLista(
			List<Disciplina_Classe> disciplinasClasseLista) {
		this.disciplinasClasseLista = disciplinasClasseLista;
	}

	public List<Professor_Disciplina_Classe> getProfessoresDisciplinaLista() {
		return professoresDisciplinaLista;
	}

	public void setProfessoresDisciplinaLista(
			List<Professor_Disciplina_Classe> professoresDisciplinaLista) {
		this.professoresDisciplinaLista = professoresDisciplinaLista;
	}

	public List<Professor_Disciplina_Classe> getProfessoresDisciplinaFiltro() {
		return professoresDisciplinaFiltro;
	}

	public void setProfessoresDisciplinaFiltro(
			List<Professor_Disciplina_Classe> professoresDisciplinaFiltro) {
		this.professoresDisciplinaFiltro = professoresDisciplinaFiltro;
	}

	public Long getProfessor() {
		return professor;
	}

	public void setProfessor(Long professor) {
		this.professor = professor;
	}

	public List<Disciplina_Classe> getDisciplinasClasseFiltro() {
		return disciplinasClasseFiltro;
	}

	public void setDisciplinasClasseFiltro(
			List<Disciplina_Classe> disciplinasClasseFiltro) {
		this.disciplinasClasseFiltro = disciplinasClasseFiltro;
	}

	// METODOS
	@PostConstruct
	public void listar() {
		professoresDisciplinaLista = professorDisciplinaRepo
				.listarProfsDisciplina();
		disciplinasClasseLista = disciplina_ClasseRepo
				.listarDisciplina_Classes();
		disciplinasClasseLista = new ArrayList<>();
	}

	public void listarDisciplinasClasse() {
		disciplinasClasseFiltro = disciplina_ClasseRepo
				.listarDisciplina_Classes();
	}

	// FILTROS
	public void filtrarDiscPorClasse() {
		disciplinasClasseLista = professorDisciplinaRepo
				.filtrarDisciplinasProClasse(classe);
		if (disciplinasClasseLista.isEmpty()) {
			FacesUtil
					.adicionarMensagemWarn("Nenhuma disciplina vinculada com a classe selecionada");
		}
	}

	// FILTROS
	public void filtrarDiscPorProfessor() {
		professoresDisciplinaFiltro = professorDisciplinaRepo
				.filtrarProfessor(professor);
		if (professoresDisciplinaFiltro.isEmpty()) {
			FacesUtil
					.adicionarMensagemWarn("Nenhuma disciplina vinculada com ao professor");
		}
	}

	public void guardar() {
		try {
			if (disciplinasClasseLista.isEmpty()) {
				throw new ExcepcaoServico("Nenhuma disciplina selecionada");
			}

			professor_Disc_Service.guardar(
					professorDisciplinaCadastro.getProfessor(),
					disciplinasClasseLista);
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	public void adicionar(ActionEvent evento) {
		disciplina_Classe = (Disciplina_Classe) evento.getComponent()
				.getAttributes().get("disciplinaSelecionada");

		int achou = -1;
		for (int posicao = 0; posicao < disciplinasClasseLista.size(); posicao++) {
			if (disciplinasClasseLista.get(posicao).getDisciplina().getNome()
					.equals(disciplina_Classe.getDisciplina().getNome())) {
				achou = posicao;
			}
		}
		if (achou < 1) {
			System.out.println(disciplina_Classe.getCodigo());
			disciplinasClasseLista.add(disciplina_Classe);
		} else {
			disciplina_Classe = disciplinasClasseLista.get(achou);
			FacesUtil.adicionarMensagemWarn("A disciplina '"
					+ disciplina_Classe.getDisciplina().getNome()
					+ "', já foi adicionada!!!");
		}

	}

	public void remover(ActionEvent evento) {
		disciplina_Classe = (Disciplina_Classe) evento.getComponent()
				.getAttributes().get("disciplinaSelecionada");
		int achou = -1;
		for (int posicao = 0; posicao < disciplinasClasseLista.size(); posicao++) {
			if (disciplinasClasseLista.get(posicao).getDisciplina().getNome()
					.equals(disciplina_Classe.getDisciplina().getNome())) {
				achou = posicao;
			}
		}
		if (achou > -1) {
			disciplinasClasseLista.remove(achou);

		}
	}

	public void removerDefinitivamente(ActionEvent evento) {
		Professor_Disciplina_Classe atributo = (Professor_Disciplina_Classe) evento
				.getComponent().getAttributes().get("disciplinaSelecionada");

		professorDisciplinaRepo.apagar(atributo);
		FacesUtil.adicionarMensagemInfo("Disciplina de '"
				+ atributo.getDisciplina_Classe().getDisciplina().getNome()
				+ "' Removida!!!");
	}

}
