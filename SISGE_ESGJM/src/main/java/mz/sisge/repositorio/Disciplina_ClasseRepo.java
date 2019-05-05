package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.bean.Disciplina_Classe_Bean;
import mz.sisge.modelo.Classe;
import mz.sisge.modelo.Disciplina;
import mz.sisge.modelo.Disciplina_Classe;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class Disciplina_ClasseRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	@Inject
	private Disciplina disciplina;
	@Inject
	private Disciplina_Classe disClasse;
	@Inject
	private Disciplina_Classe_Bean beanDC;

	@Transactional
	public void guardar(Classe classe, List<Disciplina> disciplinas) {
		classe = beanDC.getDisciplina_ClasseCadastro().getClasse();
		disClasse.setClasse(classe);
		manager.merge(classe);

		for (int posicao = 0; posicao < disciplinas.size(); posicao++) {
			disciplina = disciplinas.get(posicao);
			disClasse.setDisciplina(disciplina);
			Disciplina_Classe dsUnica = disciplina_ClasseUnica(
					classe.getCodigo(), disciplina.getCodigo());
			if (dsUnica != null && !dsUnica.equals(disClasse)) {
				throw new ExcepcaoServico("A disciplina '"
						+ dsUnica.getDisciplina().getNome()
						+ "' ja existe para a "
						+ dsUnica.getClasse().getDescricao());
			}

			manager.merge(disClasse);

		}
	}

	public Disciplina_Classe buscar(Long codigo) {
		return manager.find(Disciplina_Classe.class, codigo);
	}

	public Disciplina_Classe buscar2(Long disc, Long classe) {
		return manager
				.createQuery(
						"FROM mz.sisge.modelo.Disciplina_Classe WHERE"
								+ " disciplina.codigo = :disc "
								+ "AND classe.codigo = :classe",
						Disciplina_Classe.class).setParameter("disc", disc)
				.setParameter("classe", classe).getSingleResult();
	}

	@Transactional
	public void apagar(Disciplina_Classe disciplina_Classe) {
		disciplina_Classe = buscar(disciplina_Classe.getCodigo());
		this.manager.remove(disciplina_Classe);
		this.manager.flush();
	}

	public List<Disciplina_Classe> listarDisciplina_Classes() {
		TypedQuery<Disciplina_Classe> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Disciplina_Classe ORDER BY classe.descricao ASc",
						Disciplina_Classe.class);
		return query.getResultList();
	}

	public List<Disciplina_Classe> listarDisciplina_Classes2() {
		TypedQuery<Disciplina_Classe> query = manager.createQuery(
				"FROM mz.sisge.modelo.Disciplina_Classe GROUP BY classe",
				Disciplina_Classe.class);
		return query.getResultList();
	}

	// FILTROS
	public List<Disciplina_Classe> filtrarDisciplinas(Long classe) {
		TypedQuery<Disciplina_Classe> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Disciplina_Classe WHERE classe.codigo = :codigo ORDER BY disciplina.nome ASc",
						Disciplina_Classe.class).setParameter("codigo", classe);
		return query.getResultList();
	}

	public Disciplina_Classe disciplina_ClasseUnica(Long classe, Long disciplina) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Disciplina_Classe where classe.codigo = :classe AND disciplina.codigo = :disciplina",
							Disciplina_Classe.class)
					.setParameter("classe", classe)
					.setParameter("disciplina", disciplina).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
