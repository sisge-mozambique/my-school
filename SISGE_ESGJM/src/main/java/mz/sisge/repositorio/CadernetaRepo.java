package mz.sisge.repositorio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.bean.CadernetaBean;
import mz.sisge.modelo.Aluno_Turma;
import mz.sisge.modelo.Caderneta;
import mz.sisge.modelo.Pauta;
import mz.sisge.modelo.Professor_Disciplina_Classe;
import mz.sisge.modelo.Enumeracao.EnumPassagem;
import mz.sisge.modelo.Enumeracao.EnumPeriodo;
import mz.sisge.modelo.Enumeracao.EnumTrimestre;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class CadernetaRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	@Inject
	private Aluno_Turma aluno_Turma;
	@Inject
	private Caderneta cadTurma;
	@Inject
	private CadernetaBean beanCad;
	@Inject
	private Pauta pauta;

	@Transactional
	public void gerarCaderneta(
			Professor_Disciplina_Classe professor_Disciplina_Classe,
			List<Aluno_Turma> alunos) {

		professor_Disciplina_Classe = beanCad.getCadernetaCadastro()
				.getDisciplina();

		cadTurma.setDisciplina(professor_Disciplina_Classe);

		for (int posicao = 0; posicao < alunos.size(); posicao++) {
			aluno_Turma = alunos.get(posicao);

			Caderneta cadernetaDoAlunoUnica = cadernetaDoAlunoUnica(aluno_Turma
					.getAluno().getCodigo(),
					professor_Disciplina_Classe.getCodigo(), aluno_Turma
							.getAluno().getVaga().getAnoLectivo(),
					aluno_Turma.getCodigo());

			cadTurma.setAluno(aluno_Turma);
			cadTurma.setAnoLectivo(aluno_Turma.getAluno().getVaga()
					.getAnoLectivo());

			cadTurma.setAC1_I(new BigDecimal(0));
			cadTurma.setAC2_I(new BigDecimal(0));
			cadTurma.setAS1_I(new BigDecimal(0));
			cadTurma.setAS2_I(new BigDecimal(0));
			cadTurma.setAPT_I(new BigDecimal(0));
			cadTurma.setMedia_I(new BigDecimal(0));

			cadTurma.setAC1_II(new BigDecimal(0));
			cadTurma.setAC2_II(new BigDecimal(0));
			cadTurma.setAS1_II(new BigDecimal(0));
			cadTurma.setAS2_II(new BigDecimal(0));
			cadTurma.setAPT_II(new BigDecimal(0));
			cadTurma.setMedia_II(new BigDecimal(0));

			cadTurma.setAC1_III(new BigDecimal(0));
			cadTurma.setAC2_III(new BigDecimal(0));
			cadTurma.setAS1_III(new BigDecimal(0));
			cadTurma.setAS2_III(new BigDecimal(0));
			cadTurma.setAPT_III(new BigDecimal(0));
			cadTurma.setMedia_III(new BigDecimal(0));

			cadTurma.setMediaGeral(new BigDecimal(0));

			pauta.setAluno_Turma(aluno_Turma);
			pauta.setMedia_Fim(new BigDecimal(0));
			pauta.setNotaExame(new BigDecimal(0));
			pauta.setMedia_FimExame(new BigDecimal(0));
			pauta.setEnumPassagem(EnumPassagem.REPROVADO);
			pauta.setEnumPassagemExame(EnumPassagem.INDEFINIDO);

			if (cadernetaDoAlunoUnica != null
					&& !cadernetaDoAlunoUnica.equals(cadTurma)) {
				throw new ExcepcaoServico(
						"A caderneta do aluno "
								+ cadernetaDoAlunoUnica.getAluno().getAluno()
										.getNome()
								+ ", para a disciplina selecionada ja existe! Remova todos os "
								+ "registros dos alunos com cadernetas criadas, para adicionar "
								+ "cadernetas de externos.");
			}

			List<Pauta> pauta2 = pautaExistente(aluno_Turma.getCodigo());

			if (pauta2.isEmpty()) {
				manager.merge(pauta);
			}

			manager.merge(cadTurma);
		}
	}

	public Caderneta buscar(Long codigo) {
		return manager.find(Caderneta.class, codigo);
	}

	@Transactional
	public void apagar(Caderneta caderneta) {
		caderneta = buscar(caderneta.getCodigo());
		this.manager.remove(caderneta);
		this.manager.flush();
	}

	public List<Caderneta> listarCaderneta() {
		TypedQuery<Caderneta> query = manager.createQuery(
				"FROM mz.sisge.modelo.Caderneta GROUP BY aluno",
				Caderneta.class);
		return query.getResultList();
	}

	// FILTROS Gera CADeRNeTA
	public List<Professor_Disciplina_Classe> filtrarDiscipPorClassGeraCaderneta(
			Long classe) {
		TypedQuery<Professor_Disciplina_Classe> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Professor_Disciplina_Classe WHERE "
								+ "disciplina_Classe.classe.codigo = :classe ORDeR BY disciplina_Classe.classe.descricao ASc",
						Professor_Disciplina_Classe.class).setParameter(
						"classe", classe);
		return query.getResultList();
	}

	public List<Aluno_Turma> filtrarAlunosPorTurmaGeraCaderneta(Long turma,
			EnumPeriodo periodo) {
		TypedQuery<Aluno_Turma> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Aluno_Turma WHERE "
								+ "(turma.codigo = :turma AND aluno.vaga.periodo = :periodo AND "
								+ "aluno.estado = 'Activo') OR (turma.codigo = :turma AND aluno.vaga.periodo = :periodo AND "
								+ "aluno.estado = 'Externo') "
								+ "ORDeR BY turma.nomeTurma ASc",
						Aluno_Turma.class).setParameter("turma", turma)
				.setParameter("periodo", periodo);
		return query.getResultList();
	}

	// FILTROS CADRNTA NOTAS
	public List<Caderneta> filtrarTurmaPorProfessorGeraCaderneta(Long professor) {
		TypedQuery<Caderneta> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Caderneta WHERE "
								+ "disciplina.codigo = :professor GROUP BY aluno.turma",
						Caderneta.class).setParameter("professor", professor);
		return query.getResultList();
	}

	public List<Caderneta> filtrarCadernetaPorTurmasListaPorProfessor(
			Long disciplina, Long turma) {
		TypedQuery<Caderneta> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Caderneta WHERE "
								+ "(disciplina.codigo = :disciplina AND aluno.turma.codigo = :turma "
								+ "AND aluno.aluno.estado = 'Activo') OR (disciplina.codigo = :disciplina "
								+ "AND aluno.turma.codigo = :turma "
								+ "AND aluno.aluno.estado = 'Externo')"
								+ "ORDeR BY aluno.aluno.nome ASc",
						Caderneta.class).setParameter("disciplina", disciplina)
				.setParameter("turma", turma);
		return query.getResultList();
	}

	// PROIBICOES
	public Caderneta cadernetaDoAlunoUnica(Long aluno, Long professor,
			int anoLectivo, Long turma) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Caderneta where disciplina.codigo = :professor "
									+ "AND anoLectivo = :anoLectivo "
									+ "AND aluno.codigo = :turma AND aluno.aluno.codigo = :aluno",
							Caderneta.class)
					.setParameter("professor", professor)
					.setParameter("anoLectivo", anoLectivo)
					.setParameter("turma", turma).setParameter("aluno", aluno)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// PROIBICOES
	public Caderneta NotaLancada(Long aluno, EnumTrimestre trimestre,
			Long disciplina) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Caderneta where aluno.codigo = :aluno "
									+ "AND disciplina.codigo = :disciplina AND trimestre = :trimestre ",
							Caderneta.class).setParameter("aluno", aluno)
					.setParameter("disciplina", disciplina)
					.setParameter("trimestre", trimestre).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	// PAUTA
	public List<Caderneta> populaListaRegistrosCadernetaPorAlunoTurma(Long turma) {
		TypedQuery<Caderneta> query = manager.createQuery(
				"FROM mz.sisge.modelo.Caderneta WHERE "
						+ "aluno.turma.codigo = :turma "
						+ "ORDER BY aluno.aluno.nome Asc", Caderneta.class)
				.setParameter("turma", turma);
		return query.getResultList();
	}

	public List<Aluno_Turma> listarAlunoTurma() {
		TypedQuery<Aluno_Turma> query = manager.createQuery(
				"FROM mz.sisge.modelo.Aluno_Turma a GROUP BY a.turma.codigo",
				Aluno_Turma.class);
		return query.getResultList();
	}

	// COUNT DISCIPLINAS / CLASS
	public BigDecimal notasTOTAL(Long aluno) {
		return (BigDecimal) manager
				.createQuery(
						"SELECT sum(mediaGeral) FROM mz.sisge.modelo.Caderneta WHERE aluno.codigo = :aluno")
				.setParameter("aluno", aluno).getSingleResult();
	}

	public Long numeroDisciplinas(Long aluno) {
		return (Long) manager
				.createQuery(
						"SELECT COUNT(disciplina) FROM mz.sisge.modelo.Caderneta WHERE aluno.codigo = :aluno")
				.setParameter("aluno", aluno).getSingleResult();
	}

	// PROIBICOES PAUTA
	public List<Pauta> pautaExistente(Long aluno) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Pauta where aluno_Turma.codigo = :aluno",
							Pauta.class).setParameter("aluno", aluno)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	// PROIBICOES PAUTA
	public Pauta pautaExistente1(Long aluno) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Pauta where aluno_Turma.codigo = :aluno",
							Pauta.class).setParameter("aluno", aluno)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
