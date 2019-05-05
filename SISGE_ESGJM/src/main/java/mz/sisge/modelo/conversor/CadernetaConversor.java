package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Caderneta;
import mz.sisge.repositorio.CadernetaRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Caderneta.class)
public class CadernetaConversor implements Converter {

	private CadernetaRepo cadernetaRepo;

	public CadernetaConversor() {
		this.cadernetaRepo = (CadernetaRepo) CDIServiceLocator
				.getBean(CadernetaRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Caderneta retorno = null;

		if (value != null) {
			retorno = this.cadernetaRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Caderneta caderneta = (Caderneta) value;
			return caderneta.getCodigo() == null ? null : caderneta.getCodigo()
					.toString();

		}
		return "";
	}

}
