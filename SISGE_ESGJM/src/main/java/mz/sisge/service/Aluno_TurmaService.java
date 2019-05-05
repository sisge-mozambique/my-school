package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.Aluno_Turma;
import mz.sisge.repositorio.Aluno_TurmaRepo;
import mz.sisge.utilitario.Transactional;

public class Aluno_TurmaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Aluno_TurmaRepo aluno_TurmaRepo;

	@Transactional
	public Aluno_Turma modificar(Aluno_Turma aluno_Turma) {

		return aluno_TurmaRepo.modificar(aluno_Turma);

	}

}
