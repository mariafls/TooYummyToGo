package pt.tooyummytogo.tests;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exceptions.ComercianteJaExisteException;
import pt.tooyummytogo.exceptions.NaoEhComercianteException;
import pt.tooyummytogo.exceptions.NaoEhUtilizadorException;
import pt.tooyummytogo.exceptions.UtilizadorJaExisteException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

public class TestAcessoHandlers {

	TooYummyToGo ty2g = new TooYummyToGo();
	RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
	RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();
	PosicaoCoordenadas coordenadas = new PosicaoCoordenadas(35.4, 35.6);

	@Test
	void comercianteEncomendaProdutos() {

		try {
			regComHandler.registarComerciante("Felismina", "hortadafcul", coordenadas);
		} catch (ComercianteJaExisteException e) {
			// Do nothing
		} catch (UtilizadorJaExisteException e) {
			// Do nothing
		}
		Optional<Sessao> talvezSessao = ty2g.autenticar("Felismina", "hortadafcul");
		talvezSessao.ifPresent( (Sessao s) -> {
			Assertions.assertThrows(NaoEhUtilizadorException.class, () -> { 
				@SuppressWarnings("unused")
				EncomendarHandler lch = s.getEncomendarComerciantesHandler();
			}, "Expected lch to throw NaoEhUtilizadorException but it didnt");
		});
	}
	
	@Test
	void utilizadorDispTipoProdutos() {

		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul");
		} catch (ComercianteJaExisteException e) {
			// Do nothing
		} catch (UtilizadorJaExisteException e) {
			// Do nothing
		}
		Optional<Sessao> talvezSessao = ty2g.autenticar("Felismina", "hortadafcul");
		talvezSessao.ifPresent( (Sessao s) -> {
			Assertions.assertThrows(NaoEhComercianteException.class, () -> { 
				@SuppressWarnings("unused")
				AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();
			}, "Expected lch to throw NaoEhUtilizadorException but it didnt");
		});
	}
	
	@Test
	void utilizadorDispProdutos() {

		try {
			regHandler.registarUtilizador("Felismina", "hortadafcul");
		} catch (ComercianteJaExisteException e) {
			// Do nothing
		} catch (UtilizadorJaExisteException e) {
			// Do nothing
		}
		Optional<Sessao> talvezSessao = ty2g.autenticar("Felismina", "hortadafcul");
		talvezSessao.ifPresent( (Sessao s) -> {
			Assertions.assertThrows(NaoEhComercianteException.class, () -> { 
				@SuppressWarnings("unused")
				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();
			}, "Expected lch to throw NaoEhUtilizadorException but it didnt");
		});
	}

}
