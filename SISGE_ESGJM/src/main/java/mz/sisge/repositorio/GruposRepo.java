package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import mz.sisge.modelo.Grupo;

public class GruposRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Grupo porId(Long id) {
		return manager.find(Grupo.class, id);
	}

	public List<Grupo> previlegios() {
		return manager.createQuery("from Grupo", Grupo.class).getResultList();
	}

}
