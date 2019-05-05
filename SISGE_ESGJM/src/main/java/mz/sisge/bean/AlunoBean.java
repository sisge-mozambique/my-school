package mz.sisge.bean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;

import mz.sisge.modelo.Aluno;
import mz.sisge.modelo.Enumeracao.EnumEstado;
import mz.sisge.modelo.Enumeracao.EnumGrupo;
import mz.sisge.modelo.Enumeracao.EnumPeriodo;
import mz.sisge.modelo.Enumeracao.EnumSexo;
import mz.sisge.repositorio.AlunoRepo;
import mz.sisge.service.AlunoService;
import mz.sisge.service.exceptions.ExcepcaoServico;
import mz.sisge.utilitario.FacesUtil;

@Named
@ViewScoped
public class AlunoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Aluno alunoCadastro;
	@Inject
	private Aluno alunoSelecionado;

	@Inject
	private AlunoRepo alunoRepo;
	@Inject
	private AlunoService alunoService;

	// LISTAS
	private List<Aluno> alunosLista;
	private List<Aluno> alunosFiltro;

	private List<Aluno> listaImagem;

	// GETS E SETS
	public Aluno getAlunoCadastro() {
		return alunoCadastro;
	}

	public void setAlunoCadastro(Aluno alunoCadastro) {
		this.alunoCadastro = alunoCadastro;
	}

	public Aluno getAlunoSelecionado() {
		return alunoSelecionado;
	}

	public void setAlunoSelecionado(Aluno alunoSelecionado) {
		this.alunoSelecionado = alunoSelecionado;
	}

	public List<Aluno> getAlunosLista() {
		return alunosLista;
	}

	public void setAlunosLista(List<Aluno> alunosLista) {
		this.alunosLista = alunosLista;
	}

	public List<Aluno> getAlunosFiltro() {
		return alunosFiltro;
	}

	public void setAlunosFiltro(List<Aluno> alunosFiltro) {
		this.alunosFiltro = alunosFiltro;
	}

	public List<Aluno> getListaImagem() {
		return listaImagem;
	}

	public void setListaImagem(List<Aluno> listaImagem) {
		this.listaImagem = listaImagem;
	}

	// ENUMS
	public EnumSexo[] getEnumSexos() {
		return EnumSexo.values();
	}

	public EnumEstado[] getEstados() {
		return EnumEstado.values();
	}

	public EnumPeriodo[] getEnumPeriodos() {
		return EnumPeriodo.values();
	}

	public EnumGrupo[] getGrupos() {
		return EnumGrupo.values();
	}

	// METODOS
	public void guardar() {
		try {
			alunoService.guardar(alunoCadastro);
			alunoCadastro = new Aluno();
			FacesUtil.adicionarMensagemInfo("Registro salvo!!!");
		} catch (ExcepcaoServico e) {
			FacesUtil.adicionarMensagemWarn(e.getMessage());
		}
	}

	@PostConstruct
	public void listar() {
		alunosLista = alunoRepo.listarAlunos();
	}

	public void apagar() {
		try {
			alunoCadastro = alunoRepo.buscar(alunoCadastro.getCodigo());
			alunoRepo.apagar(alunoCadastro);
			FacesUtil.adicionarMensagemInfo("Registro removido");
		} catch (RuntimeException e) {
			FacesUtil.adicionarMensagemErro(e.getMessage());
		}
	}

	// SALVAR IMAGEM
	public void processoCarregamentoImg(FileUploadEvent uploadEvent) {
		try {
			alunoCadastro.setImagem(uploadEvent.getFile().getContents());
			FacesUtil.adicionarMensagemInfo("Imagem carregada");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<String> getImages() throws IOException {

		List<Aluno> listaLocais = (List<Aluno>) alunoRepo.buscar(alunoCadastro
				.getCodigo());
		List<String> images = new ArrayList<String>();

		String path = FacesContext.getCurrentInstance().getExternalContext()
				.getRealPath("/temp");

		for (Aluno local : listaLocais) {
			FileOutputStream fos = new FileOutputStream(path + "/"
					+ local.getNome() + ".jpg");
			fos.write(local.getImagem());
			fos.close();
			images.add(local.getNome() + ".jpg");
		}

		return images;
	}

	@SuppressWarnings("unchecked")
	public void listarImagem() {
		try {
			ServletContext context = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			listaImagem = (List<Aluno>) alunoRepo.buscar(alunoCadastro
					.getCodigo());
			File folder = new File(context.getRealPath("/temp"));
			if (!folder.exists())
				folder.mkdirs();
			for (Aluno aluno : listaImagem) {
				String nomeArquivo = aluno.getCodigo() + ".jpg";
				String arquivo = context.getRealPath("/temp") + File.separator
						+ nomeArquivo;
				criarArquivo(aluno.getImagem(), arquivo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void criarArquivo(byte[] bytes, String arquivo) {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(arquivo);
			fos.write(bytes);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
