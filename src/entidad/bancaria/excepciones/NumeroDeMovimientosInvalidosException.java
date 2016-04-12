package entidad.bancaria.excepciones;

public class NumeroDeMovimientosInvalidosException extends Exception {

	private static final long serialVersionUID = -1180750427636356356L;

	public NumeroDeMovimientosInvalidosException() {
		super("El numero de ultimos movimientos ingresado es incorrecto");
	}

}
