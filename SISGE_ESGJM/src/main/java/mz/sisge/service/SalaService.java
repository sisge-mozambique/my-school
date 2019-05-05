package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.Sala;
import mz.sisge.repositorio.SalaRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class SalaService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SalaRepo salaRepo;

	@Transactional
	public Sala guardar(Sala sala) {

		Sala salaUnica = salaRepo.salaUnica(sala.getNome(),
				sala.getLocalizacao());
		Sala acronimoUnico = salaRepo.acronimoUnico(sala.getAcronimo());

		if (salaUnica != null && !salaUnica.equals(sala)) {
			throw new ExcepcaoServico("A sala '" + salaUnica.getNome()
					+ "' ja foi informada");
		}

		if (acronimoUnico != null && !acronimoUnico.equals(sala)) {
			throw new ExcepcaoServico("O acronimo '"
					+ acronimoUnico.getAcronimo()
					+ "' ja foi utilizadp para a sala '"
					+ acronimoUnico.getNome() + "'");
		}

		if (sala.getLote() <= 0) {
			throw new ExcepcaoServico("A lotação deve ser maior que Zero '(0)'");
		}
		return salaRepo.guardar(sala);

	}

}
