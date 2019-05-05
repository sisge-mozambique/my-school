package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Sala;
import mz.sisge.repositorio.SalaRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Sala.class)
public class SalaConversor implements Converter {

	private SalaRepo salaRepo;

	public SalaConversor() {
		this.salaRepo = (SalaRepo) CDIServiceLocator.getBean(SalaRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Sala retorno = null;

		if (value != null) {
			retorno = this.salaRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Sala sala = (Sala) value;
			return sala.getCodigo() == null ? null : sala.getCodigo()
					.toString();

		}
		return "";
	}

}
