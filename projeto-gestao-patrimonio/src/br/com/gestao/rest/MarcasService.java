package br.com.gestao.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.gestao.dao.MarcaDAO;
import br.com.gestao.dao.PatrimonioDAO;
import br.com.gestao.entity.Marca;
import br.com.gestao.entity.Patrimonio;


@Path("/marcas")
public class MarcasService {
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	private MarcaDAO marcaDAO;
	private PatrimonioDAO patrDAO;
	
	@PostConstruct
	private void init() {
		this.marcaDAO = new MarcaDAO();
	}
	
	
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Marca> listMarcas() {
		List<Marca> lista = null;
		try {
			lista = marcaDAO.listMarcas();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String addMarca(Marca marca) throws Exception {
		String msg = "";
		if (!marcaDAO.existMarca(marca.getNome())) {
			try {
				int idResult = marcaDAO.addMarca(marca);
				msg = String.valueOf(idResult);
			} catch (Exception e) {
				msg = "{ \"msg\": \"Erro ao adicionar marca.\"}";
				e.printStackTrace();
			}
		} else {
			msg = "{ \"msg\": \"Erro, marca já existente, tente outro nome.\"}";
		}
		return msg;
	}
	
	
	@GET
	@Path("/get/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public Marca findById(@PathParam("id") int idMarca) {
		Marca marca = null;
		try {
			marca = marcaDAO.findMarcaById(idMarca);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return marca;
	}
	
	
	@PUT
	@Path("/edit/{id}")
	@Consumes(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String updateMarca(Marca marca, @PathParam("id") int idMarca) {
		String msg = "";
		try {
			marcaDAO.updateMarca(marca, idMarca);
			msg = "{ \"msg\": \"Marca editada com sucesso!\"}";
		} catch (Exception e) {
			msg = "{ \"msg\": \"Erro ao editar marca!\"}";
			e.printStackTrace();
		}

		return msg;
	}
	
	

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public String deleteMarca(@PathParam("id") int idMarca) {
		String msg = "";
		try {
			marcaDAO.deleteMarca(idMarca);
			msg = "{ \"msg\": \"Marca removida com sucesso!\"}";
		} catch (Exception e) {
			msg = "{ \"msg\": \"Erro ao remover marca!\"}";
			e.printStackTrace();
		}

		return msg;
	}
	
	
	@GET
	@Path("/{id}/patrimonios")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON + CHARSET_UTF8)
	public List<Patrimonio> findPatrimonioByMarca(@PathParam("id") int idMarca) {
		this.patrDAO = new PatrimonioDAO();
		List<Patrimonio> lista = null;
		try {
			lista = patrDAO.findPatrimonioByMarcas(idMarca);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return lista;
	}
	
}
