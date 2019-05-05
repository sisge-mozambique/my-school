package mz.sisge.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.Disciplina;
import mz.sisge.modelo.Disciplina_Classe;
import mz.sisge.repositorio.DisciplinaRepo;
import mz.sisge.repositorio.Disciplina_ClasseRepo;
import mz.sisge.service.Disciplina_Classe_Service;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class Disciplina_Classe_Bean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Disciplina_Classe disciplina_ClasseCadastro;
	@Inject
	private Disciplina_Classe disciplina_ClasseSelecao;
	@Inject
	private Disciplina_ClasseRepo disciplina_ClasseRepo;
	@Inject
	private Disciplina_Classe_Service disClaService;

	@Inject
	private DisciplinaRepo disciRepo;
	@Inject
	private Disciplina disciplinaSelecionada;

	private List<Disciplina> disciplinasLista;
	private List<Disciplina_Classe> disciplina_ClassesLista;
	private List<Disciplina_Classe> disciplina_ClassesFiltro;

	// PARAMS FILTRO
	private Long classe;

	// GETS E SETS
	public List<Disciplina> getDisciplinasLista() {
		return disciplinasLista;
	}

	public void setDisciplinasLista(List<Disciplina> disciplinasLista) {
		this.disciplinasLista = disciplinasLista;
	}

	public Disciplina getDisciplinaSelecionada() {
		return disciplinaSelecionada;
	}

	public void setDisciplinaSelecionada(Disciplina disciplinaSelecionada) {
		this.disciplinaSelecionada = disciplinaSelecionada;
	}

	public Disciplina_Classe getDisciplina_ClasseCadastro() {
		return disciplina_ClasseCadastro;
	}

	public void setDisciplina_ClasseCadastro(
			Disciplina_Classe disciplina_ClasseCadastro) {
		this.disciplina_ClasseCadastro = disciplina_ClasseCadastro;
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

	public Disciplina_Classe getDisciplina_ClasseSelecao() {
		return disciplina_ClasseSelecao;
	}

	public void setDisciplina_ClasseSelecao(
			Disciplina_Classe disciplina_ClasseSelecao) {
		this.disciplina_ClasseSelecao = disciplina_ClasseSelecao;
	}

	public Long getClasse() {
		return classe;
	}

	public void setClasse(Long classe) {
		this.classe = classe;
	}

	// METODOS
	@PostConstruct
	public void listar() {
		disciplina_ClassesLista = disciplina_ClasseRepo
				.listarDisciplina_Classes();
		disciplinasLista = disciRepo.listarDisciplinas();
		disciplinasLista = new ArrayList<>();
	}

	public void listar2() {
		disciplina_ClassesLista = disciplina_ClasseRepo
				.listarDisciplina_Classes2();
	}

	public void listar3() {
		disciplina_ClassesLista = disciplina_ClasseRepo
				.listarDisciplina_Classes();
	}

	// FILTROS
	public void filtrarPorDisciplina() {
		disciplina_ClassesFiltro = disciplina_ClasseRepo
				.filtrarDisciplinas(classe);
		if (disciplina_ClassesFiltro.isEmpty()) {
			FacesUtil
					.adicionarMensagemWarn("Nenhuma disciplina se encontra vinculada com a classe selecionada");
		}
	}

	public void guardar() {
		try {
			if (disciplinasLista.size() == 0) {
				throw new ExcepcaoServico("Nenhuma disciplina selecionada");
			}

			disClaService.guardar(disciplina_ClasseCadastro.getClasse(),
					disciplinasLista);
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	public void adicionar(ActionEvent evento) {
		disciplinaSelecionada = (Disciplina) evento.getComponent()
				.getAttributes().get("disciplinaSelecionada");

		int achou = -1;
		for (int posicao = 0; posicao < disciplinasLista.size(); posicao++) {
			if (disciplinasLista.get(posicao).getNome()
					.equals(disciplinaSelecionada.getNome())) {
				achou = posicao;
			}
		}
		if (achou < 0) {
			disciplinasLista.add(disciplinaSelecionada);
		} else {
			disciplinaSelecionada = disciplinasLista.get(achou);
			FacesUtil.adicionarMensagemWarn("A disciplina '"
					+ disciplinaSelecionada.getNome()
					+ "', já foi adicionada!!!");
		}

	}

	public void remover(ActionEvent evento) {
		try {

			disciplinaSelecionada = (Disciplina) evento.getComponent()
					.getAttributes().get("disciplinaSelecionada");
			int achou = -1;
			for (int posicao = 0; posicao < disciplinasLista.size(); posicao++) {
				if (disciplinasLista.get(posicao).getNome()
						.equals(disciplinaSelecionada.getNome())) {
					achou = posicao;
				}
			}
			if (achou > -1) {
				disciplinasLista.remove(achou);

			}

		} catch (RuntimeException e) {
			FacesUtil
					.adicionarMensagemWarn("Impossivel remover disciplina associada a registros");
		}
	}

	public void removerDefinitivamente(ActionEvent evento) {
		Disciplina_Classe atributo = (Disciplina_Classe) evento.getComponent()
				.getAttributes().get("disciplinaSelecionada");

		System.out.println(atributo.getDisciplina().getNome());

		disciplina_ClasseRepo.apagar(atributo);
		FacesUtil.adicionarMensagemInfo("Disciplina de '"
				+ atributo.getDisciplina().getNome() + "', removida da '"
				+ atributo.getClasse().getDescricao() + "' !!!");
	}

}
