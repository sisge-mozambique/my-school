package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.Vaga;
import mz.sisge.repositorio.VagaRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class VagaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private VagaRepo vagaRepo;

	@Transactional
	public Vaga guardar(Vaga vaga) {

		Vaga vagaExistente = vagaRepo.vagaUnica(vaga.getAnoLectivo(), vaga
				.getClasse().getCodigo(), vaga.getPeriodo());
		if (vaga.getAnoLectivo() == 0) {
			throw new ExcepcaoServico(
					"Informe um ano lectivo diferente de ('0' - Zero)");
		}
		if (vaga.getQuantidade() == 0) {
			throw new ExcepcaoServico(
					"Informe uma quantidade diferente de ('0' - Zero)");
		}
		if (vagaExistente != null && !vagaExistente.equals(vaga)) {
			throw new ExcepcaoServico("A vaga indicada ja foi informada");
		}
		return vagaRepo.guardar(vaga);
	}

}
