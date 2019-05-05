package com.mz.sgfv.seguranca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mz.sisge.modelo.Grupo;
import mz.sisge.modelo.Usuario;
import mz.sisge.repositorio.UsuariosRepo;
import mz.sisge.utilitario.CDIServiceLocator;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AppUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		UsuariosRepo usuarios = CDIServiceLocator.getBean(UsuariosRepo.class);
		Usuario usuario = usuarios.porEmail(email);

		UsuarioSistema user = null;

		if (usuario != null) {
			user = new UsuarioSistema(usuario, getGrupos(usuario));
		}

		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (Grupo grupo : usuario.getGrupos()) {
			authorities.add(new SimpleGrantedAuthority(grupo.getNome()
					.toUpperCase()));
		}

		return authorities;
	}

}
