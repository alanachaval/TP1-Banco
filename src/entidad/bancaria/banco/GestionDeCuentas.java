package entidad.bancaria.banco;

import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.clientes.PersonaFisica;
import entidad.bancaria.cuentas.CajaDeAhorro;
import entidad.bancaria.cuentas.CuentaCorriente;
import entidad.bancaria.cuentas.TipoDeMoneda;
import entidad.bancaria.excepciones.CBUInexistenteException;
import entidad.bancaria.excepciones.CUITInvalidoException;
import entidad.bancaria.excepciones.ClienteInexistenteException;
import entidad.bancaria.excepciones.DepositoInicialInvalidoException;
import entidad.bancaria.excepciones.SinClientesException;
import entidad.bancaria.excepciones.SobregiroNegativoException;
import entidad.bancaria.excepciones.TasaDeInteresNegativaException;

public class GestionDeCuentas {

	/**
	 * Abre una Caja De Ahorro asociada al banco
	 * 
	 * @param clientes
	 *            : arreglo de CUITs de los titulares de la cuenta.
	 * @param saldo
	 *            : Deposito inicial para crear la cuenta.
	 * @param tasaDeInteres
	 *            : �?
	 * @param tipoDeMoneda
	 *            : Tipo de moneda de la cuenta creada, puede ser PESO o DOLAR.
	 * @return : Numero de CBU de la cuenta creada. Devuelve 0 en caso de no
	 *         poder crear la cuenta.
	 * @throws CUITInvalidoException
	 * @throws TasaDeInteresNegativaException
	 */

	public static CajaDeAhorro crearCajaDeAhorro(String[] cuits, Double saldo,
			Double tasaDeInteres, TipoDeMoneda tipoDeMoneda)
			throws DepositoInicialInvalidoException, SinClientesException,
			ClienteInexistenteException, CUITInvalidoException,
			TasaDeInteresNegativaException {
		PersonaFisica[] titulares = new PersonaFisica[cuits.length];
		for (int i = 0; i < cuits.length; i++) {
			titulares[i] = Banco.buscarPersonaFisica(cuits[i]);
		}
		CajaDeAhorro cuenta = new CajaDeAhorro(titulares, saldo, tasaDeInteres,
				tipoDeMoneda);
		return cuenta;
	}

	/**
	 * Abre una Cuenta Corriente asociada al banco
	 * 
	 * @param cuits
	 *            : Arreglo de CUITs de los titulares de la cuenta. Deben ser
	 *            personas fisicas
	 * @param saldo
	 *            : Deposito inicial para crear la cuenta.
	 * @param sobregiro
	 *            : Valor de descubierto que puede tener la cuenta.
	 * @return : Numero de CBU de la cuenta creada. Devuelve 0 en caso de no
	 *         poder crear la cuenta.
	 * @throws DepositoInicialInvalidoException
	 * @throws ClienteInexistenteException
	 * @throws SinClientesException
	 * @throws CUITInvalidoException
	 * @throws SobregiroNegativoException
	 */

	public static CuentaCorriente crearCuentaCorriente(String[] cuits,
			Double saldo, Double sobregiro)
			throws DepositoInicialInvalidoException,
			ClienteInexistenteException, SinClientesException,
			CUITInvalidoException, SobregiroNegativoException {
		Cliente[] titulares = new Cliente[cuits.length];
		for (int i = 0; i < cuits.length; i++) {
			titulares[i] = Banco.buscarCliente(cuits[i]);
		}
		CuentaCorriente cuenta = new CuentaCorriente(titulares, saldo,
				sobregiro);
		return cuenta;
	}

	/**
	 * Inhabilita una cuenta para impedir que realize movimientos.
	 * 
	 * @param cbu
	 *            : CBU de la cuenta que se desea inhabilitar.
	 * @throws CBUInexistenteException
	 */

	public static void inhabilitarCuenta(int cbu)
			throws CBUInexistenteException {
		Banco.buscarCuenta(cbu).setHabilitada(false);
	}

	/**
	 * Habilita una cuenta para permitir que realize movimientos. Activa los
	 * titulares que hayan sido marcados como inactivos.
	 * 
	 * @param cbu
	 *            : CBU de la cuenta que se desea habilitar.
	 * @throws CBUInexistenteException
	 */

	public static void habilitarCuenta(int cbu) throws CBUInexistenteException {
		Banco.buscarCuenta(cbu).setHabilitada(true);
	}
}
