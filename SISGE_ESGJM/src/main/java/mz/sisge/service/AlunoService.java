package mz.sisge.service;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import mz.sisge.modelo.Aluno;
import mz.sisge.repositorio.AlunoRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class AlunoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private AlunoRepo alunoRepo;

	@Transactional
	public Aluno guardar(Aluno aluno) {
		Aluno bilheteUnico = alunoRepo.bilheteUnico(aluno.getBilhete());
		List<Aluno> vagasControle = alunoRepo.controleVagas(aluno.getVaga()
				.getCodigo());

		Aluno alunoUnico = alunoRepo.matriculaUnica(aluno.getBilhete(), aluno
				.getVaga().getAnoLectivo(), aluno.getVaga().getClasse()
				.getCodigo());

		if (aluno.getMedia_8a().doubleValue() != 0
				&& aluno.getMedia_9a().doubleValue() != 0
				&& aluno.getMedia_11a().doubleValue() != 0) {
			throw new ExcepcaoServico(
					"Impossivel adicionar as notas, o aluno pertence a 10a ou 12a?");
		}

		if (bilheteUnico != null && !bilheteUnico.equals(aluno)) {
			throw new ExcepcaoServico(
					"O bilhete informado já foi utilizado, contacte ao proprietário '"
							+ aluno.getNome() + "', com o contacto: "
							+ aluno.getNumero_1());
		}
		if (alunoUnico != null && !alunoUnico.equals(aluno)) {
			throw new ExcepcaoServico("O aluno informado, já foi inscrito...");
		}

		if (vagasControle.size() > aluno.getVaga().getQuantidade()) {
			throw new ExcepcaoServico(
					"A vaga selecionada já excedeu o limite de ingresso de '"
							+ aluno.getVaga().getQuantidade() + "' alunos.");
		}

		return alunoRepo.guardar(aluno);
	}
}
