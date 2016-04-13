package entidad.bancaria.banco;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import entidad.bancaria.clientes.*;
import entidad.bancaria.cuentas.*;
import entidad.bancaria.excepciones.*;

public class Banco {

	private static HashSet<Cuenta> cuentas;
	private static HashSet<Cliente> clientes;
	private static CuentaRetenciones retenciones;
	private static CuentaMantenimiento mantenimiento;
	private static double cotizacionDolar = 14.8;

	public Banco() {
		retenciones = new CuentaRetenciones();
		mantenimiento = new CuentaMantenimiento();
	}

	/*
	 * Operatoria Bancaria
	 */

	/*
	 * Operaciones por ventanilla
	 */

	public void depositoEnEfectivo(Cuenta cuentaDestino, Double monto) {
		try {
			OperacionPorVentanilla.depositoEnEfectivo(cuentaDestino, monto);
		} catch (CuentaInhabilitadaException e) {
			System.out.println(e);
		}
	}

	public void extraccionEnEfectivo(Cuenta cuentaDestino, Double monto) {
		try {
			OperacionPorVentanilla.extraccionEnEfectivo(cuentaDestino, monto);
		} catch (CuentaInhabilitadaException | SaldoInsuficienteException
				| OperacionNoPermitidaException e) {
			System.out.println(e);
		}
	}

	// Gestion De Cuentas

	public int aperturaDeCajaDeAhorro(Cliente[] clientes, Double saldo,
			Double tasaDeInteres, TipoDeMoneda tipoDeMoneda) {
		int cbu = 0;
		try {
			CajaDeAhorro cuenta = new CajaDeAhorro(clientes, saldo,
					tasaDeInteres, tipoDeMoneda);
			cbu = cuenta.getCBU();
			Banco.cuentas.add(cuenta);
		} catch (SaldoInsuficienteException | SinClientesException e) {
			e.printStackTrace();
		}
		return cbu;
	}

	public int aperturaDeCuentaCorriente(Cliente[] clientes, Double saldo,
			Double sobregiro) {
		int cbu = 0;
		try {
			CuentaCorriente cuenta = new CuentaCorriente(clientes, saldo,
					sobregiro);
			cbu = cuenta.getCBU();
			Banco.cuentas.add(cuenta);
		} catch (SaldoInsuficienteException | SinClientesException e) {
			e.printStackTrace();
		}
		return cbu;
	}

	public boolean inhabilitarCuenta(int cbu) {
		try {
			buscarCuenta(cbu).setHabilitada(false);
			return true;
		} catch (CBUInexistenteException e) {
			return false;
		}
	}

	public boolean habilitarCuenta(int cbu) {
		try {
			buscarCuenta(cbu).setHabilitada(true);
			return true;
		} catch (CBUInexistenteException e) {
			return false;
		}
	}

	private Cuenta buscarCuenta(int cbu) throws CBUInexistenteException {
		if (cbu <= 0 || cuentas.size() < cbu) {
			throw new CBUInexistenteException();
		}
		Iterator<Cuenta> iterador = cuentas.iterator();
		Cuenta cuenta = iterador.next();
		while (iterador.hasNext() && cuenta.getCBU() != cbu) {
			cuenta = iterador.next();
		}
		return cuenta;
	}

	// Gestion de clientes

	public void agregarCliente(String CUIT, String nombreORazonSocial,
			Domicilio domicilio, String telefono, String tipoDeDocumento,
			String numeroDeDocumento, String estadoCivil, String profesion,
			String nombreYApellidoDelConyuge) {
		Banco.clientes.add(new PersonaFisica(CUIT, nombreORazonSocial,
				domicilio, telefono, tipoDeDocumento, numeroDeDocumento,
				estadoCivil, profesion, nombreYApellidoDelConyuge));
	}

	public void agregarCliente(String CUIT, String nombreORazonSocial,
			Domicilio domicilio, String telefono, String fechaDelContratoSocial) {
		Banco.clientes.add(new PersonaJuridica(CUIT, nombreORazonSocial,
				domicilio, telefono, fechaDelContratoSocial));
	}

	public void bajaCliente(Cliente cliente) {
		cliente.deshabilitar();
	}

	public void activarCliente(Cliente cliente) {
		cliente.habilitar();
	}

	// Proceso Bach

	public void cobroDeMantenimientos() {
		try {
			mantenimiento.cobroDeMantenimientos();
		} catch (IOException e) {
			// TODO Bloque catch generado autom�ticamente
			e.printStackTrace();
		}
	}

	// Cobrar Retenciones

	public static void cobrarRetenciones(double monto){
		try {
			Banco.retenciones.acreditar(monto);
		} catch (CuentaInhabilitadaException e) {

		}
	}

	public static double getCotizacion(){
		return cotizacionDolar;
	}
}