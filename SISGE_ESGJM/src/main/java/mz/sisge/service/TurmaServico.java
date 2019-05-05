package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.Turma;
import mz.sisge.repositorio.TurmaRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class TurmaServico implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TurmaRepo turmaRepo;

	@Transactional
	public Turma guardar(Turma turma) {

		Turma turmaUnica = turmaRepo.turmaUnica(turma.getClasse().getCodigo(),
						turma.getNomeTurma(), turma.getPeriodo(), turma.getAnoLectivo());

		Turma salaOcupada = turmaRepo.salaOcupada(turma.getSala().getCodigo(),
				turma.getPeriodo(), turma.getAnoLectivo());

		if (turma.getAnoLectivo() == 0) {
			throw new ExcepcaoServico(
					"Informe um ano lectivo diferente de ('0' Zero)");
		}
		if (turmaUnica != null && !turmaUnica.equals(turma)) {
			throw new ExcepcaoServico(
					"A turma informada ja foi criada. Tente trocar o periodo e se nao funcionar, verifique na lista.");
		}
		if (salaOcupada != null && !salaOcupada.equals(turma)) {
			throw new ExcepcaoServico("A sala " + turma.getSala().getNome()
					+ ", se encontra ocupada no periodo selecionado, pela "
					+ salaOcupada.getClasse().getDescricao() + " - "
					+ salaOcupada.getNomeTurma());
		}

		return turmaRepo.guardar(turma);

	}

}
