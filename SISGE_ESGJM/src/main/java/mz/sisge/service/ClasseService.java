package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.Classe;
import mz.sisge.repositorio.ClasseRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class ClasseService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClasseRepo classeRepo;

	@Transactional
	public Classe guardar(Classe classe) {
		Classe classeExiste = classeRepo.classeUnica(classe.getDescricao(),
				classe.getNivel());

		if (classeExiste != null && !classeExiste.equals(classe)) {
			throw new ExcepcaoServico("A " + classeExiste.getDescricao()
					+ " para o nivel " + classeExiste.getNivel() + " já existe");
		}

		return classeRepo.guardar(classe);
	}

}
