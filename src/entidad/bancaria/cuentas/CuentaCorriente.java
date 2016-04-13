package entidad.bancaria.cuentas;

import entidad.bancaria.banco.Banco;
import entidad.bancaria.clientes.Cliente;
import entidad.bancaria.excepciones.SaldoInsuficienteException;
import entidad.bancaria.excepciones.SinClientesException;

public class CuentaCorriente extends Cuenta {

	private Cliente[] clientes;
	private Double sobregiro;
	private Integer cbu;
	private static Double COMISION = 0.03;

	public CuentaCorriente(Cliente[] clientes, Double saldo, Double sobregiro)
			throws SaldoInsuficienteException, SinClientesException {
		super();
		if (saldo <= 10000) {
			throw new SaldoInsuficienteException();
		} else if (clientes == null) {
			throw new SinClientesException();
		}
		this.clientes = clientes;
		this.saldo = saldo;
		this.sobregiro = sobregiro;
	}

	public Integer getCBU() {
		return cbu;
	}

	public Double getSobregiro() {
		return sobregiro;
	}

	public Cliente[] getClientes() {
		return clientes;
	}

	public static Double getComision() {
		return COMISION;
	}

	public static void setComision(Double comision) {
		COMISION = comision;
	}

	public void acreditar(Double monto, String fecha, MotivoDeTransaccion motivo) {

		transacciones.add(new Transaccion(TipoDeMovimiento.CREDITO, monto, motivo, ""));
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto * COMISION,
				MotivoDeTransaccion.RETENCION_DE_IMPUESTOS, ""));
		this.saldo += monto * (1 - COMISION);
		Banco.cobrarRetenciones(monto * (COMISION));
	}

	public void debitar(Double monto, String fecha, MotivoDeTransaccion motivo) throws SaldoInsuficienteException {

		if ((monto * (1 + COMISION)) > (this.saldo + this.sobregiro)) {
			throw new SaldoInsuficienteException();
		}
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto, motivo, ""));
		transacciones.add(new Transaccion(TipoDeMovimiento.DEBITO, monto * COMISION,
				MotivoDeTransaccion.RETENCION_DE_IMPUESTOS, ""));
		this.saldo -= monto * (1 + COMISION);
		Banco.cobrarRetenciones(monto * (COMISION));
	}

}