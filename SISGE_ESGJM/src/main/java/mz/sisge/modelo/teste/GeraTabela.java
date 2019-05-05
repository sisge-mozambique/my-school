package mz.sisge.modelo.teste;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import mz.sisge.modelo.Aluno;
import mz.sisge.modelo.Caderneta;
import mz.sisge.modelo.Disciplina;
import mz.sisge.modelo.Professor_Disciplina_Classe;
import mz.sisge.modelo.Enumeracao.EnumTrimestre;
import mz.sisge.utilitario.JpaUtil;

@SuppressWarnings("unused")
public class GeraTabela {

	public static void main(String[] args) {

		// Caderneta caderneta = new Caderneta();
		// caderneta.setAC1(new BigDecimal("15.75"));
		// caderneta.setAC2(new BigDecimal("12.50"));
		// caderneta.setAS1(new BigDecimal("5.00"));
		// caderneta.setAS2(new BigDecimal("15.00"));
		// caderneta.setAPT(new BigDecimal("20.00"));

		// caderneta.setAnoLectivo(2018);
		// caderneta.setFaltas(20);
		// caderneta.setTrimestre(EnumTrimestre.II);

		// Professor_Disciplina_Classe pDisciplina_Classe =
		// manager.getReference(Professor_Disciplina_Classe.class, 1L);

		// Aluno aluno = manager.getReference(Aluno.class, 1L);
		// caderneta.setAluno(aluno);

		// caderneta.setDisciplina(pDisciplina_Classe);

		// manager.merge(caderneta);
		// trx.commit();
		// manager.close();

		Persistence.createEntityManagerFactory("EscolaPU");

	}
}