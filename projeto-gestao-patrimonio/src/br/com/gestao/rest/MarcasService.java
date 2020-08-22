package br.com.gestao.rest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import br.com.gestao.dao.MarcaDAO;
import br.com.gestao.dao.PatrimonioDAO;
import br.com.gestao.entity.Marca;
import br.com.gestao.entity.Patrimonio;
import br.com.gestao.utils.FileUtils;


@Path("/marcas")
public class MarcasService {
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	private static final String PATH_PDF = "C:\\Users\\herna\\Desktop\\hernan\\gestao_pdf_disponivel";
	
	private MarcaDAO marcaDAO;
	private PatrimonioDAO patrDAO;
	
	@PostConstruct
	private void init() {
		this.marcaDAO = new MarcaDAO();
	}
	
	
	@GET
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
	@Path("/{id}")
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
	@Path("/{id}")
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
	@Path("/{id}")
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
	
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream fileInputStream,
								@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws IOException  {
		
		if (fileInputStream == null || contentDispositionHeader == null) {
			return Response.status(400).entity("Arquivo invalido.").build();
		}
		try {
			FileUtils.createFolder(PATH_PDF);
		} catch (SecurityException se) {
			return Response.status(500).entity("Não foi possivel criar a pasta de destino.").build();
		}
		
		String pathNewFile = PATH_PDF + "\\" + contentDispositionHeader.getFileName();
		try {
			File file = new File(pathNewFile);
			org.apache.commons.io.FileUtils.copyInputStreamToFile(fileInputStream, file);
		} catch (IOException e) {
			return Response.status(500).entity("Não foi possivel salvar o arquivo no servidor").build();
		}
		
		return Response.status(200).entity("Arquivo salvo em " + pathNewFile).build();
	}
	
	
	
	@GET
	@Path("/download/{filename}")
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.MULTIPART_FORM_DATA)
	public Response getOrderFile(@PathParam("filename") String filename) {
		String filePath = PATH_PDF + "\\" + filename;
		File file = new File(filePath);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition",
		    "attachment; filename=" + filename);
		return response.build();
	}
}
