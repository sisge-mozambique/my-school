package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Turma;
import mz.sisge.modelo.Enumeracao.EnumPeriodo;
import mz.sisge.modelo.Enumeracao.EnumTurma;
import mz.sisge.utilitario.Transactional;

public class TurmaRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Turma guardar(Turma turma) {
		return manager.merge(turma);

	}

	public Turma buscar(Long codigo) {
		return manager.find(Turma.class, codigo);
	}

	@Transactional
	public void apagar(Turma turma) {
		turma = buscar(turma.getCodigo());
		this.manager.remove(turma);
		this.manager.flush();
	}

	public List<Turma> listarTurmas() {
		TypedQuery<Turma> query = manager.createQuery(
				"FROM mz.sisge.modelo.Turma ORDER BY nomeTurma ASc",
				Turma.class);
		return query.getResultList();
	}

	// PROIBICOES
	public Turma salaOcupada(Long sala, EnumPeriodo periodo, int anoLectivo) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Turma WHERE sala.codigo = :sala "
									+ "AND periodo = :periodo "
									+ "AND anoLectivo = :anoLectivo",
							Turma.class).setParameter("sala", sala)
					.setParameter("periodo", periodo)
					.setParameter("anoLectivo", anoLectivo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Turma turmaUnica(Long classe, EnumTurma nomeTurma,
			EnumPeriodo periodo, int anoLectivo) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Turma WHERE classe.codigo = :classe "
									+ "AND periodo = :periodo "
									+ "AND anoLectivo = :anoLectivo "
									+ "AND nomeTurma = :nomeTurma", Turma.class)
					.setParameter("classe", classe)
					.setParameter("periodo", periodo)
					.setParameter("anoLectivo", anoLectivo)
					.setParameter("nomeTurma", nomeTurma).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}