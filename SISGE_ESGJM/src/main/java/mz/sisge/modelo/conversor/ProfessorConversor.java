package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Professor;
import mz.sisge.repositorio.ProfessorRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Professor.class)
public class ProfessorConversor implements Converter {

	private ProfessorRepo professorRepo;

	public ProfessorConversor() {
		this.professorRepo = (ProfessorRepo) CDIServiceLocator
				.getBean(ProfessorRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Professor retorno = null;

		if (value != null) {
			retorno = this.professorRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Professor professor = (Professor) value;
			return professor.getCodigo() == null ? null : professor.getCodigo()
					.toString();

		}
		return "";
	}

}
