package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.Disciplina;
import mz.sisge.repositorio.DisciplinaRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class DisciplinaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DisciplinaRepo disciplinaRepo;

	@Transactional
	public Disciplina guardar(Disciplina disciplina) {
		Disciplina disciplinaExiste = disciplinaRepo.disciplinaUnica(
				disciplina.getNome(), disciplina.getGrupo());
		Disciplina siglaUnica = disciplinaRepo
				.siglaUnica(disciplina.getSigla());

		if (disciplinaExiste != null && !disciplinaExiste.equals(disciplina)) {
			throw new ExcepcaoServico("A disciplina '"
					+ disciplinaExiste.getNome() + "' já existe");
		}

		if (siglaUnica != null && !siglaUnica.equals(disciplina)) {
			throw new ExcepcaoServico("A sigla '" + siglaUnica.getSigla()
					+ "' já está a ser utilizada para a disciplina '"
					+ siglaUnica.getNome() + "'");
		}

		return disciplinaRepo.guardar(disciplina);

	}

}
