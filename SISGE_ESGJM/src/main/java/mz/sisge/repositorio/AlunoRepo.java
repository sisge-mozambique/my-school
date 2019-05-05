package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Aluno;
import mz.sisge.modelo.Enumeracao.EnumPeriodo;
import mz.sisge.utilitario.Transactional;

public class AlunoRepo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Aluno guardar(Aluno aluno) {
		return this.manager.merge(aluno);
	}

	public Aluno buscar(Long codigo) {
		return manager.find(Aluno.class, codigo);
	}

	@Transactional
	public void apagar(Aluno aluno) {
		aluno = buscar(aluno.getCodigo());
		this.manager.remove(aluno);
		this.manager.flush();
	}

	public List<Aluno> listarAlunos() {
		TypedQuery<Aluno> query = manager.createQuery(
				"FROM mz.sisge.modelo.Aluno ORDER BY nome Asc", Aluno.class);
		return query.getResultList();
	}

	// ALUNOS DEPENDENTE FILTRO
	public List<Aluno> listarAlunosConcorrentesATurma(int anoLectivo,
			EnumPeriodo periodo, Long classe) {
		TypedQuery<Aluno> query = manager
				.createQuery(
						"FROM mz.sisge.modelo.Aluno WHERE (vaga.anoLectivo = :anoLectivo "
								+ "AND vaga.periodo = :periodo AND vaga.classe.codigo = :classe AND estado = 'Activo') "
								+ "OR (vaga.anoLectivo = :anoLectivo "
								+ "AND vaga.periodo = :periodo AND vaga.classe.codigo = :classe AND estado = 'Externo')"
								+ " ORDER BY nome Asc", Aluno.class)
				.setParameter("anoLectivo", anoLectivo)
				.setParameter("periodo", periodo)
				.setParameter("classe", classe);
		return query.getResultList();
	}

	public Aluno matriculaUnica(String bilhete, int anoLectivo, Long classe) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Aluno where upper(bilhete) = :bilhete AND"
									+ " vaga.classe.codigo = :classe AND"
									+ " anoLectivo = :anoLectivo", Aluno.class)
					.setParameter("bilhete", bilhete.toUpperCase())
					.setParameter("anoLectivo", anoLectivo)
					.setParameter("classe", classe).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public Aluno bilheteUnico(String bilhete) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Aluno where upper(bilhete) = :bilhete",
							Aluno.class).setParameter("bilhete", bilhete)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Aluno> controleVagas(Long vaga) {
		try {
			return manager
					.createQuery(
							"FROM mz.sisge.modelo.Aluno where vaga.codigo = :vaga",
							Aluno.class).setParameter("vaga", vaga)
					.getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}

}
