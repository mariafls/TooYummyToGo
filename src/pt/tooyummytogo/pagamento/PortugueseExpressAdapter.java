package pt.tooyummytogo.pagamento;

import pt.portugueseexpress.InvalidCardException;
import pt.portugueseexpress.PortugueseExpress;

public class PortugueseExpressAdapter implements MetodoDePagamento {

	@Override
	public boolean pagamento(String cartao, String validade, String ccv, Double total) {
		String[] val = validade.split("/");
		PortugueseExpress pe = new PortugueseExpress();
		String year = "20" + val[1];
		pe.setNumber(cartao);
		pe.setCcv(Integer.parseInt(ccv));
		pe.setMonth(Integer.parseInt(val[0]));
		pe.setYear(Integer.parseInt(year));
		if(pe.validate()) {
			try {
				pe.block(total);
				pe.charge(total);
			} catch (InvalidCardException e) {
				return false;
			}
			return true;
		}
		return false;
	}

}
