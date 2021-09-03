package pt.tooyummytogo.tests;

import static org.junit.Assert.assertFalse;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exceptions.ComercianteJaExisteException;
import pt.tooyummytogo.exceptions.UtilizadorJaExisteException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

class TestLogin {
	
	TooYummyToGo ty2g = new TooYummyToGo();
	RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
	RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();
	PosicaoCoordenadas coordenadas = new PosicaoCoordenadas(35.4, 35.6);
	boolean expected;
	boolean actual;
	
	@Test
	void testUsernameErradoComerciante() throws ComercianteJaExisteException, UtilizadorJaExisteException {

			  expected = false;
			  regComHandler.registarComerciante("Felismina", "hortadafcul", coordenadas);
			  Optional<Sessao> talvezSessao = ty2g.autenticar("Felismino", "hortadafcul");
				talvezSessao.ifPresent( (Sessao s) -> {

					expected = true;

				});
				assertFalse(expected);
		  
		
	}
	
	@Test
	void testPasswordErradaomerciante() throws ComercianteJaExisteException, UtilizadorJaExisteException {

			  expected = false;
			  regComHandler.registarComerciante("Felismina", "hortadafcul", coordenadas);
			  Optional<Sessao> talvezSessao = ty2g.autenticar("Felismina", "hortadafcl");
				talvezSessao.ifPresent( (Sessao s) -> {

					expected = true;
					System.out.print("ola");

				});
				assertFalse(expected);
		  
		
	}
	
	@Test
	void testUsernameErradoUtilizador() throws ComercianteJaExisteException, UtilizadorJaExisteException {

			  expected = false;
			  regHandler.registarUtilizador("Felismina", "hortadafcul");
			  Optional<Sessao> talvezSessao = ty2g.autenticar("Felismino", "hortadafcul");
				talvezSessao.ifPresent( (Sessao s) -> {

					expected = true;

				});
				assertFalse(expected);
		  
		
	}
	
	@Test
	void testPasswordErradaUtilizador() throws ComercianteJaExisteException, UtilizadorJaExisteException {

			  expected = false;
			  regHandler.registarUtilizador("Felismina", "hortadafcul");
			  Optional<Sessao> talvezSessao = ty2g.autenticar("Felismina", "hortadafcl");
				talvezSessao.ifPresent( (Sessao s) -> {

					expected = true;
					System.out.print("ola");

				});
				assertFalse(expected);
		  
		
	}
	

}
