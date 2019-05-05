package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.bean.Disciplina_Classe_ExameBean;
import mz.sisge.modelo.Disciplina_Classe;
import mz.sisge.modelo.Disciplina_Classe_Exame;
import mz.sisge.modelo.Enumeracao.EnumAceitacao;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class Disciplina_Classe_ExameRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private Disciplina_Classe_ExameBean discClasse_Bean;

	@Inject
	private Disciplina_Classe disc_classe;

	@Transactional
	public void guardar(List<Disciplina_Classe> disciplinas) {

		Disciplina_Classe_Exame disc_exame = discClasse_Bean
				.getDisciplina_Classe_ExameCadastro();

		for (int posicao = 0; posicao < disciplinas.size(); posicao++) {
			disc_classe = disciplinas.get(posicao);
			disc_exame.setDisciplina(disc_classe);
			disc_exame.setExame(EnumAceitacao.Sim);

			Disciplina_Classe_Exame dsExameUnica = disciplina_Classe_ExameUnica(disc_classe
					.getCodigo());

			if (dsExameUnica != null && !dsExameUnica.equals(disc_exame)) {
				throw new ExcepcaoServico("A disciplina '"
						+ dsExameUnica.getDisciplina().getDisciplina()
								.getNome() + "' ja foi adicionada");
			}
			manager.merge(disc_exame);

		}
	}

	public Disciplina_Classe_Exame buscar(Long codigo) {
		return manager.find(Disciplina_Classe_Exame.class, codigo);
	}

	@Transactional
	public void apagar(Disciplina_Classe_Exame disciplina_Classe_Exame) {
		disciplina_Classe_Exame = buscar(disciplina_Classe_Exame.getCodigo());
		this.manager.remove(disciplina_Classe_Exame);
		this.manager.flush();
	}

	public List<Disciplina_Classe_Exame> listarDisciplinas_Classe_Exame() {
		TypedQuery<Disciplina_Classe_Exame> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Disciplina_Classe_Exame ORDER BY disciplina.disciplina.nome ASc",
						Disciplina_Classe_Exame.class);
		return query.getResultList();
	}

	public Disciplina_Classe_Exame disciplina_Classe_ExameUnica(Long disciplina) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Disciplina_Classe_Exame where disciplina.codigo = :disciplina",
							Disciplina_Classe_Exame.class)
					.setParameter("disciplina", disciplina).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}

	}

	public List<Disciplina_Classe> listarDisciplina_Classes() {
		TypedQuery<Disciplina_Classe> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Disciplina_Classe ORDER BY classe.descricao ASc",
						Disciplina_Classe.class);
		return query.getResultList();
	}

	public List<Disciplina_Classe_Exame> filtrarDisciplinas(Long classe) {
		TypedQuery<Disciplina_Classe_Exame> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Disciplina_Classe_Exame WHERE disciplina.classe.codigo = :codigo ORDER BY disciplina.disciplina.nome ASc",
						Disciplina_Classe_Exame.class).setParameter("codigo",
						classe);
		return query.getResultList();
	}

}
