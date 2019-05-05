package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Classe;
import mz.sisge.repositorio.ClasseRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Classe.class)
public class ClasseConversor implements Converter {

	private ClasseRepo classeRepo;

	public ClasseConversor() {
		this.classeRepo = (ClasseRepo) CDIServiceLocator
				.getBean(ClasseRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Classe retorno = null;

		if (value != null) {
			retorno = this.classeRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Classe classe = (Classe) value;
			return classe.getCodigo() == null ? null : classe.getCodigo()
					.toString();

		}
		return "";
	}

}
