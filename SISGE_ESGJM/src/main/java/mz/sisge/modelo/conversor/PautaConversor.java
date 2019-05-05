package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Pauta;
import mz.sisge.repositorio.PautaRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Pauta.class)
public class PautaConversor implements Converter {

	private PautaRepo pautaRepo;

	public PautaConversor() {
		this.pautaRepo = (PautaRepo) CDIServiceLocator.getBean(PautaRepo.class);
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component,
			String value) {
		Pauta retorno = null;

		if (value != null) {
			retorno = this.pautaRepo.buscar(new Long(value));
		}

		return retorno;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component,
			Object value) {
		if (value != null) {
			Pauta pauta = (Pauta) value;
			return pauta.getCodigo() == null ? null : pauta.getCodigo()
					.toString();

		}
		return "";
	}

}
