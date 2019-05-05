package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.Pauta;
import mz.sisge.repositorio.PautaRepo;
import mz.sisge.utilitario.Transactional;

public class PautaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PautaRepo pautaRepo;

	@Transactional
	public Pauta guardar(Pauta pauta) {

		return pautaRepo.guardar(pauta);

	}

}
