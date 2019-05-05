package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.Professor;
import mz.sisge.repositorio.ProfessorRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class ProfessorService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProfessorRepo professorRepo;

	@Transactional
	public Professor guardar(Professor professor) {
		Professor biUnico = professorRepo.bilheteUnico(professor.getBilhete());
		if (biUnico != null && !biUnico.equals(professor)) {
			throw new ExcepcaoServico(
					"O bilhete informado já foi utilizado, contacte ao proprietário - '"
							+ biUnico.getNome() + "'");
		}
		return professorRepo.guardar(professor);

	}
}
