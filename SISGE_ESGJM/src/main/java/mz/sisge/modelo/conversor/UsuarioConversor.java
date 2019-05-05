package mz.sisge.modelo.conversor;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import mz.sisge.modelo.Usuario;
import mz.sisge.repositorio.UsuariosRepo;
import mz.sisge.utilitario.CDIServiceLocator;


@FacesConverter (forClass = Usuario.class)
public class UsuarioConversor implements Converter{

	
	private UsuariosRepo usuarios;
	
	public UsuarioConversor(){
		this.usuarios = (UsuariosRepo) CDIServiceLocator.getBean(UsuariosRepo.class);
	}
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		Usuario retorno = null;

		if (value != null) {
			retorno = this.usuarios.porId(new Long(value));
		}

		return retorno;
	}
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value != null) {
			Usuario usuario = (Usuario) value;
			return usuario.getId() == null ? null : usuario.getId().toString();
			// return ((Usuario) value).getId().toString();
		}
		return "";
	}
}
