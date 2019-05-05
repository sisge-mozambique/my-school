package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.bean.Prof_DisciplinaBean;
import mz.sisge.modelo.Disciplina_Classe;
import mz.sisge.modelo.Professor;
import mz.sisge.modelo.Professor_Disciplina_Classe;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class Pro_Dis_ClasseRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	@Inject
	private Prof_DisciplinaBean beanPDC;
	@Inject
	private Disciplina_Classe disciplina;
	@Inject
	private Professor_Disciplina_Classe professorDisciplina;

	public void guardar(Professor professor, List<Disciplina_Classe> disciplinas) {
		professor = beanPDC.getProfessorDisciplinaCadastro().getProfessor();
		professorDisciplina.setProfessor(professor);
		manager.merge(professor);

		for (int posicao = 0; posicao < disciplinas.size(); posicao++) {
			disciplina = disciplinas.get(posicao);
			professorDisciplina.setDisciplina_Classe(disciplina);

			Professor_Disciplina_Classe professor_Disciplina_Classe = professorDisciplinaUnicoReg(
					professor.getCodigo(), disciplina.getCodigo());

			if (professor_Disciplina_Classe != null
					&& !professor_Disciplina_Classe.equals(disciplina)) {
				throw new ExcepcaoServico("O Professor '"
						+ professor_Disciplina_Classe.getProfessor().getNome()
						+ "' ja leciona a disciplina de "
						+ professor_Disciplina_Classe.getDisciplina_Classe()
								.getDisciplina().getNome());
			}

			manager.merge(professorDisciplina);

		}
	}

	public Professor_Disciplina_Classe buscar(Long codigo) {
		return manager.find(Professor_Disciplina_Classe.class, codigo);
	}

	@Transactional
	public void apagar(Professor_Disciplina_Classe pDisciplina_Classe) {
		pDisciplina_Classe = buscar(pDisciplina_Classe.getCodigo());
		this.manager.remove(pDisciplina_Classe);
		this.manager.flush();
	}

	public List<Professor_Disciplina_Classe> listarProfsDisciplina() {
		TypedQuery<Professor_Disciplina_Classe> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Professor_Disciplina_Classe ORDER BY professor.nome ASc",
						Professor_Disciplina_Classe.class);
		return query.getResultList();
	}

	public List<Professor_Disciplina_Classe> listarProfsDisciplina2() {
		TypedQuery<Professor_Disciplina_Classe> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Professor_Disciplina_Classe GROUP BY professor.nome ASc",
						Professor_Disciplina_Classe.class);
		return query.getResultList();
	}

	// FILTROS
	public List<Professor_Disciplina_Classe> filtrarProfessor(Long professor) {
		TypedQuery<Professor_Disciplina_Classe> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Professor_Disciplina_Classe WHERE professor.codigo = :codigo ORDER BY "
								+ "disciplina_Classe.classe.descricao ASc",
						Professor_Disciplina_Classe.class).setParameter(
						"codigo", professor);
		return query.getResultList();
	}

	public List<Disciplina_Classe> filtrarDisciplinasProClasse(Long classe) {
		TypedQuery<Disciplina_Classe> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Disciplina_Classe WHERE classe.codigo = :codigo",
						Disciplina_Classe.class).setParameter("codigo", classe);
		return query.getResultList();
	}

	public Professor_Disciplina_Classe professorDisciplinaUnicoReg(
			Long professor, Long disciplina) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Professor_Disciplina_Classe where professor.codigo = :professor AND "
									+ "disciplina_Classe.codigo = :disciplina",
							Professor_Disciplina_Classe.class)
					.setParameter("professor", professor)
					.setParameter("disciplina", disciplina).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

}
