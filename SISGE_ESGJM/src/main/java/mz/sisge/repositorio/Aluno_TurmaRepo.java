package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import mz.sisge.bean.Aluno_TurmaBean;
import mz.sisge.modelo.Aluno;
import mz.sisge.modelo.Aluno_Turma;
import mz.sisge.modelo.Turma;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class Aluno_TurmaRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	@Inject
	private Aluno aluno;
	@Inject
	private Aluno_Turma alTurma;
	@Inject
	private Aluno_TurmaBean beanAT;

	@Transactional
	public void guardar(Turma turma, List<Aluno> alunos) {
		turma = beanAT.getAluno_TurmaCadastro().getTurma();
		alTurma.setTurma(turma);
		manager.merge(turma);

		for (int posicao = 0; posicao < alunos.size(); posicao++) {
			aluno = alunos.get(posicao);
			alTurma.setAluno(aluno);
			Aluno_Turma aluno_Turmaxiste = alunoNoutraTurma(aluno.getCodigo());
			List<Aluno_Turma> aluno_TurmasLista = salaTurmaCheia(turma
					.getCodigo());
			if (aluno_Turmaxiste != null && !aluno_Turmaxiste.equals(alTurma)) {
				throw new ExcepcaoServico("O aluno "
						+ aluno.getNome()
						+ ", com BI nr '"
						+ aluno.getBilhete()
						+ "' ja faz parte da '"
						+ aluno_Turmaxiste.getTurma().getClasse()
								.getDescricao() + " - "
						+ aluno_Turmaxiste.getTurma().getNomeTurma() + " ("
						+ aluno_Turmaxiste.getTurma().getPeriodo() + " - "
						+ aluno_Turmaxiste.getTurma().getAnoLectivo() + ")");
			}

			if (aluno_TurmasLista.size() >= turma.getSala().getLote()) {
				throw new ExcepcaoServico("A sala " + turma.getSala().getNome()
						+ ", pertencente a turma " + turma.getNomeTurma()
						+ " da " + turma.getClasse().getDescricao()
						+ ", no ano lectivo de " + turma.getAnoLectivo()
						+ ", se encontra cheia. Tamanho maximo: "
						+ turma.getSala().getLote());
			}
			manager.merge(alTurma);

		}
	}

	public Aluno_Turma modificar(Aluno_Turma aluno_Turma) {
		return manager.merge(aluno_Turma);
	}

	public Aluno_Turma buscar(Long codigo) {
		return manager.find(Aluno_Turma.class, codigo);
	}

	public List<Aluno_Turma> buscarAlunosTurma(Long turma) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Aluno_Turma "
									+ "WHERE (turma.codigo = :turma AND aluno.estado = 'Activo') OR "
									+ "(turma.codigo = :turma AND aluno.estado = 'Externo') order by aluno.nome ASc",
							Aluno_Turma.class).setParameter("turma", turma)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public void remover(Aluno_Turma aluno_Turma) {
		aluno_Turma = buscar(aluno_Turma.getCodigo());
		manager.remove(aluno_Turma);
		manager.flush();
	}

	public List<Aluno_Turma> listar() {

		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Aluno_Turma ORDER BY turma.nomeTurma ASc",
							Aluno_Turma.class).getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}

	// PROIBICOES
	public Aluno_Turma alunoNoutraTurma(Long aluno) {

		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Aluno_Turma "
									+ "WHERE aluno.codigo = :aluno",
							Aluno_Turma.class).setParameter("aluno", aluno)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public List<Aluno_Turma> salaTurmaCheia(Long turma) {

		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Aluno_Turma "
									+ "WHERE turma.codigo = :turma",
							Aluno_Turma.class).setParameter("turma", turma)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}

}
