package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Aluno_Turma;
import mz.sisge.modelo.Pauta;
import mz.sisge.utilitario.Transactional;

public class PautaRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Transactional
	public Pauta guardar(Pauta pauta) {
		return this.manager.merge(pauta);
	}

	public Pauta buscar(Long codigo) {
		return this.manager.find(Pauta.class, codigo);
	}

	public Pauta buscarPauta(Long aluno) {
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

	public List<Pauta> listarPautaPorTurma(Long turma) {
		TypedQuery<Pauta> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Pauta where aluno_Turma.turma.codigo = :turma ORDER BY aluno_Turma.aluno.nome Asc",
						Pauta.class).setParameter("turma", turma);
		return query.getResultList();
	}

	public List<Aluno_Turma> listarAlunosTurma() {
		TypedQuery<Aluno_Turma> query = manager.createQuery(
				"FROM mz.sisge.modelo.Aluno_Turma GROUP BY turma.nomeTurma",
				Aluno_Turma.class);
		return query.getResultList();
	}
}
