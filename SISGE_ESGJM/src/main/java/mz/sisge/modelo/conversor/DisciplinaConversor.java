package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Disciplina;
import mz.sisge.repositorio.DisciplinaRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Disciplina.class)
public class DisciplinaConversor implements Converter {

	private DisciplinaRepo disciplinaRepo;

	public DisciplinaConversor() {
		this.disciplinaRepo = (DisciplinaRepo) CDIServiceLocator
				.getBean(DisciplinaRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Disciplina retorno = null;

		if (value != null) {
			retorno = this.disciplinaRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Disciplina disciplina = (Disciplina) value;
			return disciplina.getCodigo() == null ? null : disciplina
					.getCodigo().toString();

		}
		return "";
	}

}
