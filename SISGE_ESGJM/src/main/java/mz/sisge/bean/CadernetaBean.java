package mz.sisge.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.primefaces.event.RowEditEvent;

import com.mz.sgfv.seguranca.Seguranca;

import mz.sisge.modelo.Aluno_Turma;
import mz.sisge.modelo.Caderneta;
import mz.sisge.modelo.Historico;
import mz.sisge.modelo.Pauta;
import mz.sisge.modelo.Professor_Disciplina_Classe;
import mz.sisge.modelo.Enumeracao.EnumAceitacao;
import mz.sisge.modelo.Enumeracao.EnumPassagem;
import mz.sisge.modelo.Enumeracao.EnumTrimestre;
import mz.sisge.repositorio.CadernetaRepo;
import mz.sisge.repositorio.PautaRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;
import mz.sisge.utilitario.Transactional;

@Named
@ViewScoped
public class CadernetaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Caderneta cadernetaCadastro;

	@Inject
	private Caderneta cadernetaSelecionada;

	@Inject
	private CadernetaRepo cadernetaRepo;
	private List<Caderneta> cadernetasLista;
	private List<Caderneta> cadernetasListaAlunosPorTurma;
	private List<Caderneta> cadernetasListaTurmas;
	private List<Caderneta> cadernetasListaProfessores;
	private List<Caderneta> cadernetasListaTrimestre;
	private List<Caderneta> cadernetasFiltro;
	private List<Caderneta> cadernetasListaNotas;

	@Inject
	private Aluno_Turma aluno_TurmaCadastro;
	@Inject
	private Aluno_Turma aluno_TurmaSelecionado;
	private List<Aluno_Turma> aluno_TurmasLista;
	private List<Aluno_Turma> aluno_TurmasListaFiltro;

	@Inject
	private Professor_Disciplina_Classe Professor_Disciplina_ClasseCadastro;
	private List<Professor_Disciplina_Classe> professorDisClassLista;
	private List<Caderneta> professorCadrntaTurmaLista;

	@Inject
	private Pauta pautaCadastro;
	@Inject
	private PautaRepo pautaRepo;
	private List<Pauta> pautasLista;
	private List<Pauta> pautasFiltro;

	// MANAGER
	@Inject
	private EntityManager manager;

	// pARAMS
	private Long anoPautaFiltro;
	private Long turmaPautaFiltro;

	// HISTORICO DE ENTIDADE
	@Inject
	private Historico historicoCadastro;
	private List<Historico> listaHistoricos;
	private List<Historico> filtroHistoricos;

	@Inject
	private Seguranca usuario;

	// GETS E SETS
	public Aluno_Turma getAluno_TurmaCadastro() {
		if (aluno_TurmaCadastro == null) {
			aluno_TurmaCadastro = new Aluno_Turma();
		}
		return aluno_TurmaCadastro;
	}

	public void setAluno_TurmaCadastro(Aluno_Turma aluno_TurmaCadastro) {
		this.aluno_TurmaCadastro = aluno_TurmaCadastro;
	}

	public List<Aluno_Turma> getAluno_TurmasListaFiltro() {
		return aluno_TurmasListaFiltro;
	}

	public void setAluno_TurmasListaFiltro(
			List<Aluno_Turma> aluno_TurmasListaFiltro) {
		this.aluno_TurmasListaFiltro = aluno_TurmasListaFiltro;
	}

	public List<Caderneta> getProfessorCadrntaTurmaLista() {
		return professorCadrntaTurmaLista;
	}

	public void setProfessorCadrntaTurmaLista(
			List<Caderneta> professorCadrntaTurmaLista) {
		this.professorCadrntaTurmaLista = professorCadrntaTurmaLista;
	}

	public List<Caderneta> getCadernetasListaProfessores() {
		return cadernetasListaProfessores;
	}

	public void setCadernetasListaProfessores(
			List<Caderneta> cadernetasListaProfessores) {
		this.cadernetasListaProfessores = cadernetasListaProfessores;
	}

	public List<Caderneta> getCadernetasListaTrimestre() {
		return cadernetasListaTrimestre;
	}

	public void setCadernetasListaTrimestre(
			List<Caderneta> cadernetasListaTrimestre) {
		this.cadernetasListaTrimestre = cadernetasListaTrimestre;
	}

	public Caderneta getCadernetaCadastro() {
		return cadernetaCadastro;
	}

	public void setCadernetaCadastro(Caderneta cadernetaCadastro) {
		this.cadernetaCadastro = cadernetaCadastro;
	}

	public List<Aluno_Turma> getAluno_TurmasLista() {
		return aluno_TurmasLista;
	}

	public void setAluno_TurmasLista(List<Aluno_Turma> aluno_TurmasLista) {
		this.aluno_TurmasLista = aluno_TurmasLista;
	}

	public EnumTrimestre[] getTrimestres() {
		return EnumTrimestre.values();
	}

	public List<Caderneta> getCadernetasLista() {
		return cadernetasLista;
	}

	public void setCadernetasLista(List<Caderneta> cadernetasLista) {
		this.cadernetasLista = cadernetasLista;
	}

	public List<Caderneta> getCadernetasFiltro() {
		return cadernetasFiltro;
	}

	public void setCadernetasFiltro(List<Caderneta> cadernetasFiltro) {
		this.cadernetasFiltro = cadernetasFiltro;
	}

	public Caderneta getCadernetaSelecionada() {
		return cadernetaSelecionada;
	}

	public void setCadernetaSelecionada(Caderneta cadernetaSelecionada) {
		this.cadernetaSelecionada = cadernetaSelecionada;
	}

	public List<Caderneta> getCadernetasListaTurmas() {
		return cadernetasListaTurmas;
	}

	public void setCadernetasListaTurmas(List<Caderneta> cadernetasListaTurmas) {
		this.cadernetasListaTurmas = cadernetasListaTurmas;
	}

	public Professor_Disciplina_Classe getProfessor_Disciplina_ClasseCadastro() {
		return Professor_Disciplina_ClasseCadastro;
	}

	public void setProfessor_Disciplina_ClasseCadastro(
			Professor_Disciplina_Classe professor_Disciplina_ClasseCadastro) {
		Professor_Disciplina_ClasseCadastro = professor_Disciplina_ClasseCadastro;
	}

	public Pauta getPautaCadastro() {
		return pautaCadastro;
	}

	public void setPautaCadastro(Pauta pautaCadastro) {
		this.pautaCadastro = pautaCadastro;
	}

	public Aluno_Turma getAluno_TurmaSelecionado() {
		return aluno_TurmaSelecionado;
	}

	public void setAluno_TurmaSelecionado(Aluno_Turma aluno_TurmaSelecionado) {
		this.aluno_TurmaSelecionado = aluno_TurmaSelecionado;
	}

	public Long getAnoPautaFiltro() {
		return anoPautaFiltro;
	}

	public void setAnoPautaFiltro(Long anoPautaFiltro) {
		this.anoPautaFiltro = anoPautaFiltro;
	}

	public Long getTurmaPautaFiltro() {
		return turmaPautaFiltro;
	}

	public void setTurmaPautaFiltro(Long turmaPautaFiltro) {
		this.turmaPautaFiltro = turmaPautaFiltro;
	}

	public List<Caderneta> getCadernetasListaNotas() {
		return cadernetasListaNotas;
	}

	public void setCadernetasListaNotas(List<Caderneta> cadernetasListaNotas) {
		this.cadernetasListaNotas = cadernetasListaNotas;
	}

	public List<Professor_Disciplina_Classe> getProfessorDisClassLista() {
		return professorDisClassLista;
	}

	public void setProfessorDisClassLista(
			List<Professor_Disciplina_Classe> professorDisClassLista) {
		this.professorDisClassLista = professorDisClassLista;
	}

	public List<Caderneta> getCadernetasListaAlunosPorTurma() {
		return cadernetasListaAlunosPorTurma;
	}

	public void setCadernetasListaAlunosPorTurma(
			List<Caderneta> cadernetasListaAlunosPorTurma) {
		this.cadernetasListaAlunosPorTurma = cadernetasListaAlunosPorTurma;
	}

	public List<Pauta> getPautasLista() {
		return pautasLista;
	}

	public void setPautasLista(List<Pauta> pautasLista) {
		this.pautasLista = pautasLista;
	}

	public List<Pauta> getPautasFiltro() {
		return pautasFiltro;
	}

	public void setPautasFiltro(List<Pauta> pautasFiltro) {
		this.pautasFiltro = pautasFiltro;
	}

	public Historico getHistoricoCadastro() {
		return historicoCadastro;
	}

	public void setHistoricoCadastro(Historico historicoCadastro) {
		this.historicoCadastro = historicoCadastro;
	}

	public List<Historico> getListaHistoricos() {
		return listaHistoricos;
	}

	public void setListaHistoricos(List<Historico> listaHistoricos) {
		this.listaHistoricos = listaHistoricos;
	}

	public List<Historico> getFiltroHistoricos() {
		return filtroHistoricos;
	}

	public void setFiltroHistoricos(List<Historico> filtroHistoricos) {
		this.filtroHistoricos = filtroHistoricos;
	}

	// METODOS1
	public void popularDisciplGeraCaderneta() {

		professorDisClassLista = cadernetaRepo
				.filtrarDiscipPorClassGeraCaderneta(aluno_TurmaCadastro
						.getTurma().getClasse().getCodigo());

		if (professorDisClassLista.isEmpty()) {
			throw new ExcepcaoServico("Lista vazia");
		}

		aluno_TurmasLista = cadernetaRepo.filtrarAlunosPorTurmaGeraCaderneta(
				aluno_TurmaCadastro.getTurma().getCodigo(), aluno_TurmaCadastro
						.getTurma().getPeriodo());
	}

	// MTODOS2
	public void popularTurmasListaPorProfessor() {
		professorCadrntaTurmaLista = cadernetaRepo
				.filtrarTurmaPorProfessorGeraCaderneta(Professor_Disciplina_ClasseCadastro
						.getCodigo());
	}

	public void popularCadernetaPorTurmasListaPorProfessor() {
		try {
			cadernetasLista = cadernetaRepo
					.filtrarCadernetaPorTurmasListaPorProfessor(
							cadernetaCadastro.getDisciplina().getCodigo(),
							cadernetaCadastro.getAluno().getTurma().getCodigo());
		} catch (NullPointerException e) {
			FacesUtil.adicionarMensagemWarn("Selecione uma disciplina");
			cadernetasLista = new ArrayList<Caderneta>();
		}
	}

	public void gerarCaderneta() {
		try {
			if (aluno_TurmasLista.size() == 0) {
				throw new ExcepcaoServico(
						"Selecione uma turma que contenha ao menos 1 (um) Aluno");
			}
			cadernetaRepo.gerarCaderneta(cadernetaCadastro.getDisciplina(),
					aluno_TurmasLista);
			FacesUtil.adicionarMensagemInfo("Caderneta criada!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	// EDITOR DE CELULAS
	@Transactional
	public void onRowEditPauta(RowEditEvent event) {
		try {

			pautaCadastro = ((Pauta) event.getObject());

			BigDecimal mediaFimExame = (pautaCadastro.getNotaExame()
					.add(pautaCadastro.getMedia_Fim())).divide(
					new BigDecimal(2), 2, 2);

			if (pautaCadastro.getEnumPassagem() == EnumPassagem.DISPENSADO) {
				throw new ExcepcaoServico(
						"O Aluno selecionado nao requer da nota de EXAME");
			}

			if (pautaCadastro.getEnumPassagem() == EnumPassagem.EXCLUIDO) {
				throw new ExcepcaoServico("O Aluno selecionado esta EXCLUIDO");
			}

			if (pautaCadastro.getEnumPassagem() == EnumPassagem.APROVADO) {
				throw new ExcepcaoServico(
						"O Aluno selecionado nao requere actualizacoes referentes ao EXAME");
			}

			if (pautaCadastro.getEnumPassagem() == EnumPassagem.REPROVADO) {
				throw new ExcepcaoServico(
						"O Aluno selecionado nao requere actualizacoes referentes ao EXAME");
			}

			if (pautaCadastro.getEnumPassagem() == EnumPassagem.ADMITIDO
					&& pautaCadastro.getAluno_Turma().getTurma().getClasse()
							.getExame().equals(EnumAceitacao.Sim)) {

				if (mediaFimExame.doubleValue() < 10) {
					pautaCadastro.setMedia_FimExame(mediaFimExame);
					pautaCadastro.setEnumPassagemExame(EnumPassagem.REPROVADO);
					manager.merge(pautaCadastro);
				}

				if (mediaFimExame.doubleValue() >= 10
						&& mediaFimExame.doubleValue() <= 20) {
					pautaCadastro.setMedia_FimExame(mediaFimExame);
					pautaCadastro.setEnumPassagemExame(EnumPassagem.APROVADO);
					manager.merge(pautaCadastro);
				}
			}

			manager.merge(pautaCadastro);

			FacesUtil.adicionarMensagemInfo("Actualizando a pauta");

		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	@Transactional
	public void onRowEdit(RowEditEvent event) {
		try {

			cadernetaCadastro = ((Caderneta) event.getObject());

			cadernetaCadastro.setAC1_I(cadernetaCadastro.getAC1_I());
			cadernetaCadastro.setAC2_I(cadernetaCadastro.getAC2_I());
			cadernetaCadastro.setAS1_I(cadernetaCadastro.getAS1_I());
			cadernetaCadastro.setAS2_I(cadernetaCadastro.getAS2_I());
			cadernetaCadastro.setAPT_I(cadernetaCadastro.getAPT_I());

			cadernetaCadastro.setAC1_II(cadernetaCadastro.getAC1_II());
			cadernetaCadastro.setAC2_II(cadernetaCadastro.getAC2_II());
			cadernetaCadastro.setAS1_II(cadernetaCadastro.getAS1_II());
			cadernetaCadastro.setAS2_II(cadernetaCadastro.getAS2_II());
			cadernetaCadastro.setAPT_II(cadernetaCadastro.getAPT_II());

			cadernetaCadastro.setAC1_III(cadernetaCadastro.getAC1_III());
			cadernetaCadastro.setAC2_III(cadernetaCadastro.getAC2_III());
			cadernetaCadastro.setAS1_III(cadernetaCadastro.getAS1_III());
			cadernetaCadastro.setAS2_III(cadernetaCadastro.getAS2_III());
			cadernetaCadastro.setAPT_III(cadernetaCadastro.getAPT_III());
			cadernetaCadastro.setFaltas(cadernetaCadastro.getFaltas());

			BigDecimal mediaGeral = cadernetaRepo.notasTOTAL(cadernetaCadastro
					.getAluno().getCodigo());
			Long numeroDisciplinas = cadernetaRepo
					.numeroDisciplinas(cadernetaCadastro.getAluno().getCodigo());

			BigDecimal mediaFinal = mediaGeral.divide(new BigDecimal(
					numeroDisciplinas.intValue()), 2, 2);

			Pauta pauta = cadernetaRepo.pautaExistente1(cadernetaCadastro
					.getAluno().getCodigo());

			if (pauta != null) {
				pautaCadastro = pautaRepo.buscarPauta(cadernetaCadastro
						.getAluno().getCodigo());
				pautaCadastro.setMedia_Fim(mediaFinal);
				manager.merge(pautaCadastro);
				if (mediaFinal.intValue() >= 10
						&& mediaFinal.intValue() <= 20
						&& cadernetaCadastro.getDisciplina()
								.getDisciplina_Classe().getClasse().getExame()
								.equals(EnumAceitacao.Não)) {
					pautaCadastro.setEnumPassagem(EnumPassagem.APROVADO);
					manager.merge(pautaCadastro);
				}

				if (mediaFinal.intValue() < 10
						&& cadernetaCadastro.getDisciplina()
								.getDisciplina_Classe().getClasse().getExame()
								.equals(EnumAceitacao.Não)) {

					pautaCadastro.setEnumPassagem(EnumPassagem.REPROVADO);
					manager.merge(pautaCadastro);
				}

				if (mediaFinal.intValue() >= 10
						&& mediaFinal.intValue() < 14
						&& cadernetaCadastro.getDisciplina()
								.getDisciplina_Classe().getClasse().getExame()
								.equals(EnumAceitacao.Sim)) {

					pautaCadastro.setEnumPassagem(EnumPassagem.ADMITIDO);
					manager.merge(pautaCadastro);
				}

				if (mediaFinal.intValue() >= 14
						&& mediaFinal.intValue() <= 20
						&& cadernetaCadastro.getDisciplina()
								.getDisciplina_Classe().getClasse().getExame()
								.equals(EnumAceitacao.Sim)) {
					pautaCadastro.setEnumPassagem(EnumPassagem.DISPENSADO);
					manager.merge(pautaCadastro);
				}

				if (mediaFinal.intValue() < 10
						&& cadernetaCadastro.getDisciplina()
								.getDisciplina_Classe().getClasse().getExame()
								.equals(EnumAceitacao.Sim)) {
					pautaCadastro.setEnumPassagem(EnumPassagem.EXCLUIDO);
					manager.merge(pautaCadastro);
				}

			}

			String uername = usuario.getUsuarioLogado().getUsername()
					.toString();
			manager.merge(cadernetaCadastro);

			historicoCadastro.setAluno(cadernetaCadastro.getAluno().getAluno()
					.getCodigo().toString());
			historicoCadastro.setAlunoNome(cadernetaCadastro.getAluno()
					.getAluno().getNome());
			historicoCadastro.setAnoLectivo(cadernetaCadastro.getAnoLectivo());
			historicoCadastro.setClasse(cadernetaCadastro.getAluno().getTurma()
					.getClasse().getDescricao());
			historicoCadastro.setDisciplina(cadernetaCadastro.getDisciplina()
					.getDisciplina_Classe().getDisciplina().getNome());
			historicoCadastro.setData(new Date());
			historicoCadastro.setEmailUsuario(uername);
			manager.merge(historicoCadastro);

			FacesUtil.adicionarMensagemInfo("Nota registrada");

		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	// OUTROS MTHODOS
	public void remover(ActionEvent evento) {
		aluno_TurmaSelecionado = (Aluno_Turma) evento.getComponent()
				.getAttributes().get("alunoSelecionado");
		int achou = -1;
		for (int posicao = 0; posicao < aluno_TurmasLista.size(); posicao++) {
			if (aluno_TurmasLista.get(posicao).getAluno()
					.equals(aluno_TurmaSelecionado.getAluno())) {
				achou = posicao;
			}
		}
		if (achou > -1) {
			aluno_TurmasLista.remove(achou);
		}
	}

	// POPULA PAUTA
	public void populaListaRegistrosCadernetaPorAlunoTurma() {
		cadernetasListaAlunosPorTurma = cadernetaRepo
				.populaListaRegistrosCadernetaPorAlunoTurma(turmaPautaFiltro);
	}

	@PostConstruct
	public void listarAlunoTurma() {
		aluno_TurmasListaFiltro = cadernetaRepo.listarAlunoTurma();
	}

	// FILTRO PAUTA POR TURMA
	public void listarPautaPorTurma() {
		pautasLista = pautaRepo.listarPautaPorTurma(turmaPautaFiltro);
		if (pautasLista.isEmpty()) {
			FacesUtil
					.adicionarMensagemWarn("A pauta da turma selecionada nao existe....");
		}
	}

}
