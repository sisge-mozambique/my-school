package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Historico;
import mz.sisge.utilitario.Transactional;

public class HistoricoRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Transactional
	public Historico guardar(Historico historico) {
		return this.manager.merge(historico);
	}

	public List<Historico> listarHistorias() {
		TypedQuery<Historico> query = manager.createQuery(
				"FROM mz.sisge.modelo.Historico ORDER BY data Desc",
				Historico.class);
		return query.getResultList();
	}

}