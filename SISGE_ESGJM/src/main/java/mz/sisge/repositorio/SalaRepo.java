package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Sala;
import mz.sisge.modelo.Enumeracao.EnumBloco;
import mz.sisge.utilitario.Transactional;

public class SalaRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Sala guardar(Sala sala) {
		return this.manager.merge(sala);
	}

	public Sala buscar(Long codigo) {
		return manager.find(Sala.class, codigo);
	}

	@Transactional
	public void apagar(Sala sala) {
		sala = buscar(sala.getCodigo());
		this.manager.remove(sala);
		this.manager.flush();
	}

	public List<Sala> listarSalas() {
		TypedQuery<Sala> query = manager.createQuery(
				"FROM mz.sisge.modelo.Sala ORDER BY localizacao ASc",
				Sala.class);
		return query.getResultList();
	}

	public Sala salaUnica(String nome, EnumBloco localizacao) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Sala where upper(nome) = :nome AND localizacao = :localizacao",
							Sala.class)
					.setParameter("nome", nome.toUpperCase())
					.setParameter("localizacao", localizacao).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Sala acronimoUnico(String acronimo) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Sala where upper(acronimo) = :acronimo",
							Sala.class)
					.setParameter("acronimo", acronimo.toUpperCase())
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
