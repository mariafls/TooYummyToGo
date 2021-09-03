package pt.tooyummytogo.main;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import pt.tooyummytogo.Sessao;
import pt.tooyummytogo.exceptions.CodigoNaoExisteException;
import pt.tooyummytogo.exceptions.ComercianteJaExisteException;
import pt.tooyummytogo.exceptions.ListaVaziaException;
import pt.tooyummytogo.exceptions.NaoEhComercianteException;
import pt.tooyummytogo.exceptions.NaoEhUtilizadorException;
import pt.tooyummytogo.exceptions.PagamentoRecusadoException;
import pt.tooyummytogo.exceptions.QuantidadeIndisponivelException;
import pt.tooyummytogo.exceptions.TipoDeProdutoJaExisteException;
import pt.tooyummytogo.exceptions.UtilizadorJaExisteException;
import pt.tooyummytogo.facade.TooYummyToGo;
import pt.tooyummytogo.facade.dto.ComercianteInfo;
import pt.tooyummytogo.facade.dto.PosicaoCoordenadas;
import pt.tooyummytogo.facade.dto.ProdutoInfo;
import pt.tooyummytogo.facade.handlers.AdicionarTipoDeProdutoHandler;
import pt.tooyummytogo.facade.handlers.ColocarProdutoHandler;
import pt.tooyummytogo.facade.handlers.EncomendarHandler;
import pt.tooyummytogo.facade.handlers.RegistarComercianteHandler;
import pt.tooyummytogo.facade.handlers.RegistarUtilizadorHandler;

public class ClienteExemplo {
	public static void main(String[] args)  {
		TooYummyToGo ty2g = new TooYummyToGo();

		// UC1 
		try {
			RegistarUtilizadorHandler regHandler = ty2g.getRegistarUtilizadorHandler();
			regHandler.registarUtilizador("Felismina", "hortadafcul");


			// UC3
			RegistarComercianteHandler regComHandler = ty2g.getRegistarComercianteHandler();

			regComHandler.registarComerciante("Silvino", "bardoc2", new PosicaoCoordenadas(34.5, 45.2));
			regComHandler.registarComerciante("Maribel", "torredotombo", new PosicaoCoordenadas(33.5, 45.2));

		} catch(ComercianteJaExisteException e) {
			System.out.println("Comerciante ja existe");
		} catch (UtilizadorJaExisteException e) {
			System.out.println("Utilizador ja existe");
		} catch (NullPointerException e) {
			System.out.println("Exception throw: " + e);
		}

		// UC4
		Optional<Sessao> talvezSessao = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao.ifPresent( (Sessao s) -> {

			try {
				AdicionarTipoDeProdutoHandler atp = s.adicionarTipoDeProdutoHandler();
				Random r = new Random();
				for (String tp : new String[] {"Pão", "Pão de Ló", "Mil-folhas"}) {
					atp.registaTipoDeProduto(tp, r.nextDouble() * 10);
				}
			} catch(TipoDeProdutoJaExisteException e) {
				System.out.println("Tipo de produto ja existe");
			} catch (NaoEhComercianteException e) {
				System.out.println("Nao eh comerciante");
			}

		});


		//fizemos uma segunda disponibilizacao para testar os produtos selecionados pelo periodo
		//alteramos horas para isso
		Optional<Sessao> talvezSessao2 = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao2.ifPresent( (Sessao s) -> {
			try {
				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();

				try {
					List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();
					cpv.indicaProduto(listaTiposDeProdutos.get(1), 10); // Pão
					cpv.indicaProduto(listaTiposDeProdutos.get(2), 5); // Mil-folhas
					cpv.indicaProduto("batata", 5);
					
				} catch(CodigoNaoExisteException e) {
					System.out.println("Codigo nao existe");
				} catch(IndexOutOfBoundsException e) {
					System.out.println("Exception throw: " + e);
				}
				cpv.confirma(LocalDateTime.now().plusHours(1), LocalDateTime.now().plusHours(2));
				System.out.println("Produtos disponíveis");
			} catch (NaoEhComercianteException e) {
				System.out.println("Nao eh comerciante");
			}
		});

		Optional<Sessao> talvezSessao4 = ty2g.autenticar("Silvino", "bardoc2");
		talvezSessao4.ifPresent( (Sessao s) -> {
			try {
				ColocarProdutoHandler cpv = s.getColocarProdutoHandler();

				try {
					List<String> listaTiposDeProdutos = cpv.inicioDeProdutosHoje();
					cpv.indicaProduto(listaTiposDeProdutos.get(1), 10); // Pão
					cpv.indicaProduto("batata", 5);

				} catch(CodigoNaoExisteException e) {
					System.out.println("Codigo nao existe");
				} catch(IndexOutOfBoundsException e) {
					System.out.println("Exception throw: " + e);
				}
				cpv.confirma(LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5));
				System.out.println("Produtos disponíveis");
			} catch (NaoEhComercianteException e) {
				System.out.println("Nao eh comerciante");
			}
		});

		// UC6 + UC7
		Optional<Sessao> talvezSessao3 = ty2g.autenticar("Felismina", "hortadafcul");
		talvezSessao3.ifPresent( (Sessao s) -> {

			try {
				EncomendarHandler lch = s.getEncomendarComerciantesHandler();
				List<ComercianteInfo> cs = lch.indicaLocalizacaoActual(new PosicaoCoordenadas(34.5, 45.2));

				for (ComercianteInfo i : cs) {
					System.out.println(i);
				}

				boolean redefineRaio = false;

				if (redefineRaio) {
					cs = lch.redefineRaio(100);
					for (ComercianteInfo i : cs) {
						System.out.println(i);
					}
				}

				boolean redefinePeriodo = true;
				if (redefinePeriodo) {
					cs = lch.redefinePeriodo(LocalDateTime.now().plusHours(4), LocalDateTime.now().plusHours(5));

					for (ComercianteInfo i : cs) {
						System.out.println(i);
					}
				}

				// A partir de agora Ã© UC7
				List<ProdutoInfo> ps = lch.escolheComerciante(cs.get(0));

				for (ProdutoInfo p : ps) {
					lch.indicaProduto(p, 10); // Um de cada
					System.out.println(p.toString());
				}

				String codigoReserva = lch.indicaPagamento("555782312312", "02/21", "766");	
				System.out.println("Reserva " + codigoReserva + " feita com sucesso");
			} catch(QuantidadeIndisponivelException e) {
				System.out.println("Produto: " + e.getProduto() + ", quantidade disponivel: " + e.getQuantidade());
			} catch(ListaVaziaException e) {
				System.out.println("Lista vazia");
			} catch(IndexOutOfBoundsException e) {
				System.out.println("Exception throw: " + e);
			} catch(PagamentoRecusadoException e) {
				System.out.println("Pagamento recusado");
			} catch(NullPointerException e) {
				System.out.println("Exception throw: " + e);
			} catch (NaoEhUtilizadorException e) {
				System.out.println("Nao eh utilizador");
			} catch (CloneNotSupportedException e) {
				System.out.println("Exception throw: " + e);
			}

		});

	}

}
	
	
