package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Vaga;
import mz.sisge.modelo.Enumeracao.EnumPeriodo;
import mz.sisge.utilitario.Transactional;

public class VagaRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Vaga guardar(Vaga vaga) {
		return this.manager.merge(vaga);
	}

	public Vaga buscar(Long codigo) {
		return manager.find(Vaga.class, codigo);
	}

	@Transactional
	public void apagar(Vaga vaga) {
		vaga = buscar(vaga.getCodigo());
		this.manager.remove(vaga);
		this.manager.flush();
	}

	public List<Vaga> listarVagas() {
		TypedQuery<Vaga> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Vaga ORDER BY anoLectivo ASc",
						Vaga.class);
		return query.getResultList();
	}

	public Vaga vagaUnica(int anoLectivo, Long classe, EnumPeriodo periodo) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Vaga where anoLectivo = :anoLectivo "
									+ "AND classe.codigo = :classe AND periodo = :periodo",
							Vaga.class).setParameter("anoLectivo", anoLectivo)
					.setParameter("classe", classe)
					.setParameter("periodo", periodo).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}
