package mz.sisge.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import mz.sisge.modelo.Disciplina_Classe;
import mz.sisge.modelo.Professor;
import mz.sisge.repositorio.Pro_Dis_ClasseRepo;
import mz.sisge.utilitario.Transactional;

public class Professor_Disc_Service implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Pro_Dis_ClasseRepo professorClasseRepo;

	@Transactional
	public void guardar(Professor professor, List<Disciplina_Classe> disciplinas) {

		professorClasseRepo.guardar(professor, disciplinas);

	}

}
