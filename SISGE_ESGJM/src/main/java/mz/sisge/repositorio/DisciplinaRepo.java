package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Disciplina;
import mz.sisge.modelo.Enumeracao.EnumDisciplinaGrupo;
import mz.sisge.utilitario.Transactional;

public class DisciplinaRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Disciplina guardar(Disciplina disciplina) {
		return this.manager.merge(disciplina);
	}

	public Disciplina buscar(Long codigo) {
		return manager.find(Disciplina.class, codigo);
	}

	@Transactional
	public void apagar(Disciplina disciplina) {
		disciplina = buscar(disciplina.getCodigo());
		this.manager.remove(disciplina);
		this.manager.flush();
	}

	public List<Disciplina> listarDisciplinas() {
		TypedQuery<Disciplina> query = manager.createQuery(
				"FROM mz.sisge.modelo.Disciplina d ORDER BY d.nome Asc",
				Disciplina.class);
		return query.getResultList();
	}

	public Disciplina disciplinaUnica(String nome, EnumDisciplinaGrupo grupo) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Disciplina where upper(nome) = :nome AND grupo = :grupo",
							Disciplina.class)
					.setParameter("nome", nome.toUpperCase())
					.setParameter("grupo", grupo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Disciplina siglaUnica(String sigla) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Disciplina where upper(sigla) = :sigla",
							Disciplina.class)
					.setParameter("sigla", sigla.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
