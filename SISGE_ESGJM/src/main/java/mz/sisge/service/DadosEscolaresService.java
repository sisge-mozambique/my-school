package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.DadosEscolares;
import mz.sisge.repositorio.DadosEscolaresRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class DadosEscolaresService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private DadosEscolaresRepo dadosEscolaresRepo;

	@Transactional
	public DadosEscolares guardar(DadosEscolares dadosEscolares) {

		DadosEscolares dadosEscolares2 = dadosEscolaresRepo
				.DadosEscolaresUnicos(dadosEscolares.getNomeDirector(),
						dadosEscolares.getNomeEscola(),
						dadosEscolares.getChefeSecretaria(),
						dadosEscolares.getNivelChefeSecretaria());

		if (dadosEscolares2 != null && !dadosEscolares2.equals(dadosEscolares)) {
			throw new ExcepcaoServico("O dados informados existem.");
		}
		return dadosEscolaresRepo.guardar(dadosEscolares);

	}

}
