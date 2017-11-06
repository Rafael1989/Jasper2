package br.com.caelum.relatorio.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.relatorio.ConnectionFactory;
import br.com.caelum.relatorio.gerador.GeradorRelatorio;

@WebServlet("/movimentacoes")
public class RelatorioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Connection connection = new ConnectionFactory().getConnection();
			String tipo = request.getParameter("tipo");
			String nomeArquivo = request.getServletContext().getRealPath("/WEB-INF/jasper/movimentacoes.jasper");
			Map<String, Object> parametros = new HashMap<>();
			parametros.put("TIPO", tipo);
			GeradorRelatorio geradorRelatorio = new GeradorRelatorio(nomeArquivo, parametros, connection);
			geradorRelatorio.geraPDFParaOutputStream(response.getOutputStream());
			
			
			connection.close();
		} catch (SQLException e) {
			throw new ServletException(e);
		} 
	}
}
