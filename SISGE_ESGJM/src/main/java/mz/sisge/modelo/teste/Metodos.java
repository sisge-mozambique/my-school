package mz.sisge.modelo.teste;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import mz.sisge.modelo.Disciplina;
import mz.sisge.repositorio.DisciplinaRepo;

public class Metodos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private DisciplinaRepo disciplinaRepo;

	private List<Disciplina> lista;

	public void listar() {
		this.lista = disciplinaRepo.listarDisciplinas();
	}

	public List<Disciplina> getLista() {
		return lista;
	}

}
