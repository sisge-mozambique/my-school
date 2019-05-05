package mz.sisge.repositorio;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;







import mz.sisge.modelo.Usuario;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mz.sgfv.repositorio.filtro.UsuarioFilter;

public class UsuariosRepo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Usuario porId(Long id){
		return this.manager.find(Usuario.class, id);
	}
	
	public List<Usuario> vendedores() {
		//filtra apenas os funcionarios por um grupo especifico
		return this.manager.createQuery("from Usuario", Usuario.class)
				.getResultList();
		
	}

	public Usuario porEmail(String email) {
		Usuario usuario = null;
		
		try{
			usuario = this.manager.createQuery("from Usuario where lower(email) = :email", Usuario.class)
				.setParameter("email",email.toLowerCase()).getSingleResult();
		}catch(NoResultException e) {
			//nenhum usuario encontrado com o nome informado
		}
		return usuario;
	}
	
	@SuppressWarnings("unchecked")
	public List<Usuario> filtrados(UsuarioFilter filtro){
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Usuario.class);
		
		if(StringUtils.isNotBlank(filtro.getNome())){
			criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
		}
		
		if(StringUtils.isNotBlank(filtro.getEmail())){
			criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
		}
		return criteria.addOrder(Order.asc("id")).list();
	}

	public Usuario guardar(Usuario usuario) {
		return usuario = manager.merge(usuario);
	}
	
	@Transactional
	public void remover(Usuario usuario){
		usuario = porId(usuario.getId());
		try {
			manager.remove(usuario);
			
			manager.flush();
		} catch (PersistenceException e) {
			throw new ExcepcaoServico("Usuario nao pode ser excluido");
		}
	}

}
