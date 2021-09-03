package pt.tooyummytogo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pt.tooyummytogo.exceptions.ComercianteJaExisteException;
import pt.tooyummytogo.exceptions.UtilizadorJaExisteException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

class TestRegistar {
	
	TooYummyToGo ty2g = new TooYummyToGo();
	RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
	RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();
	PosicaoCoordenadas coordenadas = new PosicaoCoordenadas(35.4, 35.6);

	@Test
	void testUtilizadorJaEstaRegistado() {
		  Assertions.assertThrows(UtilizadorJaExisteException.class, () -> {
			  
				regHandler.registarUtilizador("Felismina", "hortadafcul");
				regHandler.registarUtilizador("Felismina", "hortadafcul");	
			  });
		
	}
	
	@Test
	void tesComercianteJaEstaRegistadoComoUtilizador() {
		  Assertions.assertThrows(UtilizadorJaExisteException.class, () -> {
			  
			  regHandler.registarUtilizador("Felismina", "hortadafcul");
			  regComHandler.registarComerciante("Felismina", "hortadafcul", coordenadas);
			  });
		
	}
	
	@Test
	void tesComercianteJaEstaRegistado() {
		  Assertions.assertThrows(ComercianteJaExisteException.class, () -> {
			  
			  regComHandler.registarComerciante("Felismina", "hortadafcul", coordenadas);
			  regComHandler.registarComerciante("Felismina", "hortadafcul", coordenadas);
			  });
		
	}
}
