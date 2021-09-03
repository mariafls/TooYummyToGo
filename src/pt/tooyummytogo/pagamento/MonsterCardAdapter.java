package pt.tooyummytogo.pagamento;

import com.monstercard.Card;
import com.monstercard.MonsterCardAPI;

public class MonsterCardAdapter implements MetodoDePagamento {
	
	public boolean pagamento(String cartao, String validade, String ccv, Double total) {
		String[] val = validade.split("/");
		Card card = new Card(cartao, ccv, val[0], "20"+val[1]);
		MonsterCardAPI mc = new MonsterCardAPI();
		return mc.isValid(card) && mc.block(card, total) && mc.charge(card, total);
	}
}
