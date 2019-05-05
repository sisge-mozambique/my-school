package mz.sisge.service;

import java.io.Serializable;

import javax.inject.Inject;

import mz.sisge.modelo.Usuario;
import mz.sisge.repositorio.UsuariosRepo;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

public class CadastroUsuarioService implements Serializable {

private static final long serialVersionUID = 1L;

@Inject
private UsuariosRepo usuarios;

@Transactional
public Usuario salvar(Usuario usuario){
	Usuario usuarioExistente = usuarios.porEmail(usuario.getEmail());
	
	if (usuarioExistente != null && usuarioExistente.equals(usuario)){
		throw new ExcepcaoServico("Já existe um usuário com o e-mail informado");
	}
	
	return usuarios.guardar(usuario);
}

}
