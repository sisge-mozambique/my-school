package mz.sisge.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mz.sisge.modelo.Aluno;
import mz.sisge.modelo.Aluno_Turma;
import mz.sisge.repositorio.AlunoRepo;
import mz.sisge.repositorio.Aluno_TurmaRepo;
import mz.sisge.service.Aluno_TurmaService;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class Aluno_TurmaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Aluno_Turma aluno_TurmaCadastro;
	@Inject
	private Aluno_Turma aluno_TurmaSelecionado;
	@Inject
	private Aluno_TurmaRepo aluno_TurmaRepo;

	@Inject
	private Aluno_TurmaService aluno_TurmaService;

	@Inject
	private AlunoRepo alunoRepo;
	@Inject
	private Aluno alunoSelecionado;

	private List<Aluno> alunosLista;
	private List<Aluno> alunosLista2;
	private List<Aluno_Turma> aluno_TurmasLista;
	private List<Aluno_Turma> aluno_TurmasListaFiltro;

	// GETS E SETS
	public Aluno_Turma getAluno_TurmaCadastro() {
		return aluno_TurmaCadastro;
	}

	public void setAluno_TurmaCadastro(Aluno_Turma aluno_TurmaCadastro) {
		this.aluno_TurmaCadastro = aluno_TurmaCadastro;
	}

	public Aluno_Turma getAluno_TurmaSelecionado() {
		return aluno_TurmaSelecionado;
	}

	public void setAluno_TurmaSelecionado(Aluno_Turma aluno_TurmaSelecionado) {
		this.aluno_TurmaSelecionado = aluno_TurmaSelecionado;
	}

	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}

	public List<Aluno> getAlunosLista() {
		return alunosLista;
	}

	public void setAlunosLista(List<Aluno> alunosLista) {
		this.alunosLista = alunosLista;
	}

	public List<Aluno_Turma> getAluno_TurmasLista() {
		return aluno_TurmasLista;
	}

	public void setAluno_TurmasLista(List<Aluno_Turma> aluno_TurmasLista) {
		this.aluno_TurmasLista = aluno_TurmasLista;
	}

	public List<Aluno_Turma> getAluno_TurmasListaFiltro() {
		return aluno_TurmasListaFiltro;
	}

	public void setAluno_TurmasListaFiltro(
			List<Aluno_Turma> aluno_TurmasListaFiltro) {
		this.aluno_TurmasListaFiltro = aluno_TurmasListaFiltro;
	}

	public List<Aluno> getAlunosLista2() {
		return alunosLista2;
	}

	public void setAlunosLista2(List<Aluno> alunosLista2) {
		this.alunosLista2 = alunosLista2;
	}

	// METODOS
	@PostConstruct
	public void listar() {
		aluno_TurmasLista = aluno_TurmaRepo.listar();
	}

	public void listarPorSelecao() {
		alunosLista = alunoRepo.listarAlunosConcorrentesATurma(
				aluno_TurmaCadastro.getTurma().getAnoLectivo(),
				aluno_TurmaCadastro.getTurma().getPeriodo(),
				aluno_TurmaCadastro.getTurma().getClasse().getCodigo());
		alunosLista2 = new ArrayList<>();
	}

	public void guardar() {
		try {
			if (alunosLista2.size() == 0) {
				throw new ExcepcaoServico("Nenhum aluno selecionado");
			}
			aluno_TurmaRepo.guardar(aluno_TurmaCadastro.getTurma(),
					alunosLista2);
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	public void modificar() {
		try {
			aluno_TurmaSelecionado = aluno_TurmaRepo.buscar(aluno_TurmaCadastro
					.getCodigo());

			System.out.println(aluno_TurmaSelecionado.getAluno().getNome());

			aluno_TurmaSelecionado.setCodigo(aluno_TurmaCadastro.getCodigo());
			aluno_TurmaSelecionado.setAluno(aluno_TurmaCadastro.getAluno());
			aluno_TurmaSelecionado.setTurma(aluno_TurmaCadastro.getTurma());

			aluno_TurmaService.modificar(aluno_TurmaSelecionado);
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	public void adicionar(ActionEvent evento) {
		alunoSelecionado = (Aluno) evento.getComponent().getAttributes()
				.get("alunoSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < alunosLista2.size(); posicao++) {
			if (alunosLista2.get(posicao).getNome()
					.equals(alunoSelecionado.getNome())) {
				achou = posicao;
			}
		}
		if (achou < 0) {
			alunosLista2.add(alunoSelecionado);
		} else {
			alunoSelecionado = alunosLista2.get(achou);
			FacesUtil
					.adicionarMensagemWarn("O Aluno '"
							+ alunoSelecionado.getNome() + "' com BI nr ("
							+ alunoSelecionado.getBilhete()
							+ "), já foi adicionado!!!");
		}

	}

	public void remover(ActionEvent evento) {
		alunoSelecionado = (Aluno) evento.getComponent().getAttributes()
				.get("alunoSelecionado");
		int achou = -1;
		for (int posicao = 0; posicao < alunosLista2.size(); posicao++) {
			if (alunosLista2.get(posicao).getNome()
					.equals(alunoSelecionado.getNome())) {
				achou = posicao;
			}
		}
		if (achou > -1) {
			alunosLista2.remove(achou);

		}
	}

}