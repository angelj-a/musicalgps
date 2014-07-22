package compmovil.themebylocation.utils.exceptions;

public class NoRegionsException extends Exception {

	private static final long serialVersionUID = 1L;

		public NoRegionsException() {
			super("No hay regiones definidas");
		}
}
