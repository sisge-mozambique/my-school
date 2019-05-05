package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.DadosEscolares;
import mz.sisge.modelo.Enumeracao.EnumCategoria;
import mz.sisge.utilitario.Transactional;

public class DadosEscolaresRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public DadosEscolares guardar(DadosEscolares dadosEscolares) {
		return manager.merge(dadosEscolares);

	}

	public DadosEscolares buscar(Long codigo) {
		return manager.find(DadosEscolares.class, codigo);
	}

	@Transactional
	public void apagar(DadosEscolares dadosEscolares) {
		dadosEscolares = buscar(dadosEscolares.getCodigo());
		this.manager.remove(dadosEscolares);
		this.manager.flush();
	}

	public List<DadosEscolares> listarDadosEscolares() {
		TypedQuery<DadosEscolares> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.DadosEscolares ORDER BY nomeDirector ASc",
						DadosEscolares.class);
		return query.getResultList();
	}

	// PROIBICOES
	public DadosEscolares DadosEscolaresUnicos(String director, String escola,
			String chefeSecretaria, EnumCategoria nivel) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.DadosEscolares WHERE upper(nomeDirector) = :director "
									+ "AND upper(nomeEscola) = :escola "
									+ "AND upper(chefeSecretaria) = :chefeSecretaria "
									+ "AND upper(nivelChefeSecretaria) = :nivel",
							DadosEscolares.class)
					.setParameter("director", director.toUpperCase())
					.setParameter("escola", escola.toUpperCase())
					.setParameter("chefeSecretaria",
							chefeSecretaria.toUpperCase())
					.setParameter("nivel", nivel).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}