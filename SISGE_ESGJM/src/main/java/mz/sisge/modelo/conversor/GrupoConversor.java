package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Grupo;
import mz.sisge.repositorio.GruposRepo;
import mz.sisge.utilitario.CDIServiceLocator;

@FacesConverter(forClass = Grupo.class)
public class GrupoConversor implements Converter{

	//@Inject
		private GruposRepo grupos;
		
		public GrupoConversor(){
			this.grupos = (GruposRepo) CDIServiceLocator.getBean(GruposRepo.class);
		}
		
		@Override
		public Object getAsObject(FacesContext context, UIComponent component, String value) {
			Grupo retorno = null;
			
			if(value != null){
				Long id = new Long(value);
				retorno = grupos.porId(id);
			}
			
			return retorno;
	}
		@Override
		public String getAsString(FacesContext context, UIComponent component, Object value) {
			
			if(value != null){
				Grupo grupo = (Grupo) value;
				return grupo.getId() == null ? null : grupo.getId().toString();
			}
			
			return "";
		}

}
