package entidad.bancaria.tests;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import entidad.bancaria.banco.*;
import entidad.bancaria.clientes.*;
import entidad.bancaria.cuentas.*;
import entidad.bancaria.excepciones.*;

public class TestBancoJava {

	private static Domicilio domicilio;

	/*
	 * Pre de cada test
	 */
	@BeforeClass
	public static void fixture() throws CUITInvalidoException,
			CUITYaAsignadoException, DepositoInicialInvalidoException,
			SinClientesException, ClienteInexistenteException,
			TasaDeInteresNegativaException {

		/* Creamos un Domicilio */
		domicilio = new Domicilio("42 Wallaby", "2222", "P Sherman", "Sidney");

		/* Creamos una Persona Fisica */
		Banco.agregarPersonaFisica("20000000001", "Roberto Gomez Bola�os",
				domicilio, "0303456", "DNI", "00000000", "Soltero",
				"Psic�logo", "Do�a Florinda");

		/* Creamos una Persona Jur�dica */
		Banco.agregarPersonaJuridica("20000000002", "Noganet", domicilio,
				"52753700", "01/1996");

		/* Creamos una Caja de Ahorro en PESOS */
		String[] clientesCajaDeAhorro = new String[] { "20000000001" };
		Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 5000.0, 0.1,
				TipoDeMoneda.PESO);

		/* Creamos una Caja de Ahorro en DOLARES */
		Banco.crearCajaDeAhorro(clientesCajaDeAhorro, 500.0, 0.1,
				TipoDeMoneda.DOLAR);

		/* Creamos una Cuenta Corriente */
		String[] clientesCuentaCorriente = new String[] { "20000000002" };
		Banco.crearCuentaCorriente(clientesCuentaCorriente, 10000.0, 10000.0);
	}

	/*
	 * Prueba error debitar saldo insuficiente
	 */

	@Test
	public void metodo() {
		
	}
}