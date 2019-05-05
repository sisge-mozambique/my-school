package mz.sisge.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import mz.sisge.modelo.Disciplina_Classe;
import mz.sisge.repositorio.Disciplina_Classe_ExameRepo;
import mz.sisge.utilitario.Transactional;

public class Disciplina_Classe_ExameService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Disciplina_Classe_ExameRepo disciplina_Classe_ExameRepo;

	@Transactional
	public void guardar(List<Disciplina_Classe> disciplinas) {
		disciplina_Classe_ExameRepo.guardar(disciplinas);
	}

}
