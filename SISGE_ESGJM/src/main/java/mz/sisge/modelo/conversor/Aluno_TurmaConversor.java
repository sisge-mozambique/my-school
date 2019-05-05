package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Aluno_Turma;
import mz.sisge.repositorio.Aluno_TurmaRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Aluno_Turma.class)
public class Aluno_TurmaConversor implements Converter {

	private Aluno_TurmaRepo aluno_TurmaRepo;

	public Aluno_TurmaConversor() {
		this.aluno_TurmaRepo = (Aluno_TurmaRepo) CDIServiceLocator
				.getBean(Aluno_TurmaRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Aluno_Turma retorno = null;

		if (value != null) {
			retorno = this.aluno_TurmaRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Aluno_Turma aluno_Turma = (Aluno_Turma) value;
			return aluno_Turma.getCodigo() == null ? null : aluno_Turma
					.getCodigo().toString();

		}
		return "";
	}

}
