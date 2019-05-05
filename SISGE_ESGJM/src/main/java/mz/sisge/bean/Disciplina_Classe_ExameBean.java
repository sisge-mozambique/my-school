package mz.sisge.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.event.RowEditEvent;

import mz.sisge.modelo.Disciplina_Classe;
import mz.sisge.modelo.Disciplina_Classe_Exame;
import mz.sisge.modelo.Enumeracao.EnumAceitacao;
import mz.sisge.repositorio.Disciplina_Classe_ExameRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;
import mz.sisge.utilitario.Transactional;

@Named
@ViewScoped
public class Disciplina_Classe_ExameBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// MANAGER
	@Inject
	private EntityManager manager;

	@Inject
	private Disciplina_Classe_Exame disciplina_Classe_ExameCadastro;
	@Inject
	private Disciplina_Classe_ExameRepo disciplina_Classe_ExameRepo;

	// LISTAS
	private List<Disciplina_Classe_Exame> disciplina_Classe_ExamesLista;
	private List<Disciplina_Classe_Exame> disciplina_Classe_ExamesFiltro;
	private List<Disciplina_Classe> disciplina_ClassesLista;
	private List<Disciplina_Classe> disciplina_ClassesLista1;

	@Inject
	private Disciplina_Classe disciplinaSelecionada;
	private List<Disciplina_Classe> disciplina_ClassesFiltro;

	// PARAMETRO
	private Long classe;

	// GETS E SETS
	public Disciplina_Classe_Exame getDisciplina_Classe_ExameCadastro() {
		return disciplina_Classe_ExameCadastro;
	}

	public void setDisciplina_Classe_ExameCadastro(
			Disciplina_Classe_Exame disciplina_Classe_ExameCadastro) {
		this.disciplina_Classe_ExameCadastro = disciplina_Classe_ExameCadastro;
	}

	public List<Disciplina_Classe_Exame> getDisciplina_Classe_ExamesLista() {
		return disciplina_Classe_ExamesLista;
	}

	public void setDisciplina_Classe_ExamesLista(
			List<Disciplina_Classe_Exame> disciplina_Classe_ExamesLista) {
		this.disciplina_Classe_ExamesLista = disciplina_Classe_ExamesLista;
	}

	public List<Disciplina_Classe_Exame> getDisciplina_Classe_ExamesFiltro() {
		return disciplina_Classe_ExamesFiltro;
	}

	public void setDisciplina_Classe_ExamesFiltro(
			List<Disciplina_Classe_Exame> disciplina_Classe_ExamesFiltro) {
		this.disciplina_Classe_ExamesFiltro = disciplina_Classe_ExamesFiltro;
	}

	public EnumAceitacao[] getAceitacao() {
		return EnumAceitacao.values();
	}

	public List<Disciplina_Classe> getDisciplina_ClassesLista() {
		return disciplina_ClassesLista;
	}

	public void setDisciplina_ClassesLista(
			List<Disciplina_Classe> disciplina_ClassesLista) {
		this.disciplina_ClassesLista = disciplina_ClassesLista;
	}

	public List<Disciplina_Classe> getDisciplina_ClassesFiltro() {
		return disciplina_ClassesFiltro;
	}

	public void setDisciplina_ClassesFiltro(
			List<Disciplina_Classe> disciplina_ClassesFiltro) {
		this.disciplina_ClassesFiltro = disciplina_ClassesFiltro;
	}

	public Disciplina_Classe getDisciplinaSelecionada() {
		return disciplinaSelecionada;
	}

	public void setDisciplinaSelecionada(Disciplina_Classe disciplinaSelecionada) {
		this.disciplinaSelecionada = disciplinaSelecionada;
	}

	public List<Disciplina_Classe> getDisciplina_ClassesLista1() {
		return disciplina_ClassesLista1;
	}

	public void setDisciplina_ClassesLista1(
			List<Disciplina_Classe> disciplina_ClassesLista1) {
		this.disciplina_ClassesLista1 = disciplina_ClassesLista1;
	}

	public Long getClasse() {
		return classe;
	}

	public void setClasse(Long classe) {
		this.classe = classe;
	}

	// METHODOS
	@PostConstruct
	public void listar() {
		disciplina_Classe_ExamesLista = disciplina_Classe_ExameRepo
				.listarDisciplinas_Classe_Exame();
		disciplina_ClassesLista = disciplina_Classe_ExameRepo
				.listarDisciplina_Classes();
		disciplina_ClassesLista = new ArrayList<>();

	}

	public void guardar() {
		try {
			if (disciplina_ClassesLista.size() == 0) {
				throw new ExcepcaoServico("Nenhuma disciplina selecionada");
			}

			System.out.println(disciplina_Classe_ExameCadastro.getExame());
			disciplina_Classe_ExameRepo.guardar(disciplina_ClassesLista);
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}

	}

	public void listar1() {
		disciplina_ClassesLista1 = disciplina_Classe_ExameRepo
				.listarDisciplina_Classes();
	}

	public void adicionar(ActionEvent evento) {
		disciplinaSelecionada = (Disciplina_Classe) evento.getComponent()
				.getAttributes().get("disciplinaSelecionada");

		int achou = -1;
		for (int posicao = 0; posicao < disciplina_ClassesLista.size(); posicao++) {
			if (disciplina_ClassesLista.get(posicao).getDisciplina().getNome()
					.equals(disciplinaSelecionada.getDisciplina().getNome())) {
				achou = posicao;
			}
		}
		if (achou < 0) {
			disciplina_ClassesLista.add(disciplinaSelecionada);
		} else {
			disciplinaSelecionada = disciplina_ClassesLista.get(achou);
			FacesUtil
					.adicionarMensagemWarn("A disciplina já foi adicionada!!!");
		}
	}

	public void remover(ActionEvent evento) {
		try {

			disciplinaSelecionada = (Disciplina_Classe) evento.getComponent()
					.getAttributes().get("disciplinaSelecionada");
			int achou = -1;
			for (int posicao = 0; posicao < disciplina_ClassesLista.size(); posicao++) {
				if (disciplina_ClassesLista
						.get(posicao)
						.getDisciplina()
						.getNome()
						.equals(disciplinaSelecionada.getDisciplina().getNome())) {
					achou = posicao;
				}
			}
			if (achou > -1) {
				disciplina_ClassesLista.remove(achou);

			}

		} catch (RuntimeException e) {
			FacesUtil
					.adicionarMensagemWarn("Impossivel remover disciplina associada a registros");
		}
	}

	public void removerDefinitivamente(ActionEvent evento) {
		Disciplina_Classe_Exame atributo = (Disciplina_Classe_Exame) evento
				.getComponent().getAttributes().get("disciplinaSelecionada");

		disciplina_Classe_ExameRepo.apagar(atributo);
		FacesUtil.adicionarMensagemInfo("Disciplina de '"
				+ atributo.getDisciplina().getDisciplina().getNome()
				+ "', removida !!!");
	}

	// FILTROS
	public void filtrarPorDisciplina() {
		disciplina_Classe_ExamesFiltro = disciplina_Classe_ExameRepo
				.filtrarDisciplinas(classe);
		if (disciplina_Classe_ExamesFiltro.isEmpty()) {
			FacesUtil
					.adicionarMensagemWarn("Nenhuma disciplina se encontra vinculada aos exames da classe selecionada");
		}
	}

	// EDITOR DE CELULAS
	@Transactional
	public void onRowEdit(RowEditEvent event) {
		try {

			disciplina_Classe_ExameCadastro = ((Disciplina_Classe_Exame) event
					.getObject());

			disciplina_Classe_ExameCadastro
					.setExame(disciplina_Classe_ExameCadastro.getExame());
			manager.merge(disciplina_Classe_ExameCadastro);

			FacesUtil.adicionarMensagemInfo("Estado de exame modificado");

		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}

	}
}
