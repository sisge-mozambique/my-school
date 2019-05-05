package mz.sisge.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import mz.sisge.modelo.Classe;
import mz.sisge.modelo.Disciplina;
import mz.sisge.repositorio.Disciplina_ClasseRepo;
import mz.sisge.utilitario.Transactional;

public class Disciplina_Classe_Service implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Disciplina_ClasseRepo disciplina_ClasseRepo;

	@Transactional
	public void guardar(Classe classe, List<Disciplina> disciplinas) {

		disciplina_ClasseRepo.guardar(classe, disciplinas);

	}

}
