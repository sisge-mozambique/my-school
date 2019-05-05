package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Classe;
import mz.sisge.modelo.Enumeracao.EnumNivelEscolar;
import mz.sisge.utilitario.Transactional;

public class ClasseRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Classe guardar(Classe classe) {
		return this.manager.merge(classe);
	}

	public Classe buscar(Long codigo) {
		return manager.find(Classe.class, codigo);
	}

	@Transactional
	public void apagar(Classe classe) {
		classe = buscar(classe.getCodigo());
		this.manager.remove(classe);
		this.manager.flush();
	}

	public List<Classe> listarClasses() {
		TypedQuery<Classe> query = manager.createQuery(
				"FROM mz.sisge.modelo.Classe c ORDER BY c.descricao Asc",
				Classe.class);
		return query.getResultList();
	}

	public Classe classeUnica(String descricao, EnumNivelEscolar nivel) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Classe where  descricao = :descricao AND nivel = :nivel",
							Classe.class)
					.setParameter("descricao", descricao.toUpperCase())
					.setParameter("nivel", nivel).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
