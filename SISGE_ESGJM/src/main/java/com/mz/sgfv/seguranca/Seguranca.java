package com.mz.sgfv.seguranca;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Named
@RequestScoped
public class Seguranca {
	
	@Inject
	private ExternalContext externalContext;
	
	public String getNomeUsuario() {
		String nome = null;
		
		UsuarioSistema usuarioLogado = getUsuarioLogado();
	
		if(usuarioLogado != null) {
		nome = usuarioLogado.getUsuario().getNome();
		
		}
		return nome;
	}

	@Produces
	@UsuarioLogado
	public UsuarioSistema getUsuarioLogado() {
		UsuarioSistema usuario = null;
		
		UsernamePasswordAuthenticationToken auth = (UsernamePasswordAuthenticationToken)
				FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
		
		if(auth != null && auth.getPrincipal() != null) {
			usuario = (UsuarioSistema) auth.getPrincipal();
		}
		
		return usuario;
	}
	
	public boolean isEmitirVendaPermitida(){
		return externalContext.isUserInRole("ADMINISTRADOR")
				|| externalContext.isUserInRole("VENDEDOR");
	}
	
	public boolean isCancelarVendaPermitida(){
		return externalContext.isUserInRole("ADMINISTRADOR")
				|| externalContext.isUserInRole("VENDEDOR");
	}

}
