package pt.tooyummytogo.pagamento;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Configuration {
	
	private static Configuration INSTANCE = new Configuration();
	
	public static Configuration getInstance() {
		return INSTANCE;
	}
	
	protected Configuration() {
		Properties metodo = new Properties();
		
		try {
			metodo.load(new FileInputStream(new File("metodosPagamento.properties")));

			String metodoPag = metodo.getProperty("cartaoAUsar");

			try {
				@SuppressWarnings("unchecked")
				Class<MetodoDePagamento> klass = (Class<MetodoDePagamento>) Class.forName(metodoPag);
				Constructor<MetodoDePagamento> cons = klass.getConstructor();
				metodoDePagamento = cons.newInstance();
			} catch (ClassNotFoundException e) {
				// Do nothing, just ignore
			} catch (NoSuchMethodException e) {
				// Do nothing, just ignore
			} catch (SecurityException e) {
				// Do nothing, just ignore
			} catch (InstantiationException e) {
				// Do nothing, just ignore
			} catch (IllegalAccessException e) {
				// Do nothing, just ignore
			} catch (IllegalArgumentException e) {
				// Do nothing, just ignore
			} catch (InvocationTargetException e) {
				// Do nothing, just ignore
			}

		} catch (FileNotFoundException e) {
			// Do nothing, just ignore
		} catch (IOException e) {
			// Do nothing, just ignore
		}
		
	}
	
	private MetodoDePagamento metodoDePagamento = new MonsterCardAdapter(); // default

	public MetodoDePagamento getMetodoDePagamento() {
		return metodoDePagamento;
	}

}
