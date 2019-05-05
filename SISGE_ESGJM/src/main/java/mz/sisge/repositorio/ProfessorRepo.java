package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Professor;
import mz.sisge.utilitario.Transactional;

public class ProfessorRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Professor guardar(Professor professor) {
		return this.manager.merge(professor);
	}

	public Professor buscar(Long codigo) {
		return manager.find(Professor.class, codigo);
	}

	@Transactional
	public void apagar(Professor professor) {
		professor = buscar(professor.getCodigo());
		this.manager.remove(professor);
		this.manager.flush();
	}

	public List<Professor> listarProfessores() {
		TypedQuery<Professor> query = manager.createQuery(
				"FROM mz.sisge.modelo.Professor", Professor.class);
		return query.getResultList();
	}

	public Professor bilheteUnico(String bilhete) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Professor where bilhete = :bilhete",
							Professor.class)
					.setParameter("bilhete", bilhete.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
