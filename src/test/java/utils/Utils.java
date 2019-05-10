package utils;

/**
 * Clase con utilerías genéricas
 * 
 * @author Ricardo Romero
 * @version 1.0.0
 * @date 17/01/19
 */
public class Utils {

	public static int extraerNumero(String numero) {
		numero = numero.replaceAll("[^0-9]", "");
		if (numero.equals("")) {
			numero = "0";
		}
		return Integer.parseInt(numero);
	}

	public static String extraerTexto(String cadena) {
		String resultado = null;
		Integer index = cadena.indexOf(".");
		if (index > 0) {
			index += 1;
			resultado = cadena.substring(index);
		} else {
			resultado = cadena;
		}
		return resultado;
	}
}
