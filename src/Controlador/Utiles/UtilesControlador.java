
package Controlador.Utiles;

import Controlador.TDA.ListaDinamica.Excepcion.ListaVacia;
import Controlador.TDA.ListaDinamica.Excepcion.PosicionNoEncontrada;
import Controlador.TDA.ListaDinamica.ListaDinamica;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JTextField;

/**
 *
 * @author Victor
 */
public class UtilesControlador {
    
    public static boolean validarFecha(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
        sdf.setLenient(false);

        try {
            sdf.parse(fecha);
            return true;
        } 
        catch (ParseException e) {
            System.out.println("Error al parsear la fecha: " + e.getMessage());
            return false;
        }
    }
    
    public static Date setDateFormat(JTextField texto) throws ParseException{
        return new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).parse(texto.getText());
    }
    
    public static String obtenerFechaHoraActualFormateada() {
        Date fechaHoraActual = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH);
        return formateador.format(fechaHoraActual);
    }
    
    private static Field getField(Class<?> clazz, String fieldName) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredField(fieldName);
            } 
            catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            }
        }
        return null;
    }
    
//    @SuppressWarnings("unchecked")
//    private static <T> boolean comparar(T elemento1, T elemento2, String campo, Integer orden) {
//        try {
//            Field field = getField(elemento1.getClass(), campo);
//            field.setAccessible(true);
//
//            if (Comparable.class.isAssignableFrom(field.getType())) {
//                Comparable<Object> valor1 = (Comparable<Object>) field.get(elemento1);
//                Comparable<Object> valor2 = (Comparable<Object>) field.get(elemento2);
//                int resultadoComparacion = valor1.compareTo(valor2);
//                return orden == 1 ? resultadoComparacion > 0 : resultadoComparacion < 0;
//            } 
//            else {
//                throw new IllegalArgumentException("El campo no es comparable");
//            }
//        } 
//        catch (IllegalAccessException e) {
//            
//            return false;
//        }
//    }
    
    @SuppressWarnings({ "unchecked", "unused" })
    private static <T> boolean comparar(T elemento1, T elemento2, String campo, Integer orden) {
        try {
            String[] campos = campo.split("\\.");
            Object valor1 = obtenerValorCampo(elemento1, campos);
            Object valor2 = obtenerValorCampo(elemento2, campos);
            if (valor1 instanceof Comparable && valor2 instanceof Comparable) {
                Comparable<Object> comparable1 = (Comparable<Object>) valor1;
                Comparable<Object> comparable2 = (Comparable<Object>) valor2;
                int resultadoComparacion = comparable1.compareTo(valor2);
                return orden == 1 ? resultadoComparacion > 0 : resultadoComparacion < 0;
            } 
            else {
                throw new IllegalArgumentException("El campo no es comparable");
            }
        } 
        catch (Exception e) {
            return false;
        }
    }

    private static Object obtenerValorCampo(Object objeto, String[] campos) throws IllegalAccessException, NoSuchFieldException {
        Object valor = objeto;
        for (String campo : campos) {
            Field field = valor.getClass().getDeclaredField(campo);
            field.setAccessible(true);
            valor = field.get(valor);
        }
        return valor;
    }

    public static <T> ListaDinamica<T> SelectSort(ListaDinamica<T> lista, Integer Orden, String Campo) throws ListaVacia, Exception {
        Integer n = lista.getLongitud();
        T[] elementos = lista.toArray();
        Field atributo = getField(elementos[0].getClass(), Campo);

        if (atributo != null) {
            for (int i = 0; i < n - 1; i++) {
                int k = i;
                T elementoOrden = elementos[i];

                for (int j = i + 1; j < n; j++) {
                    if (comparar(elementos[j], elementoOrden, Campo, Orden)) {
                        elementoOrden = elementos[j];
                        k = j;
                    }
                }
                elementos[k] = elementos[i];
                elementos[i] = elementoOrden;
            }
        } 
        else {
            throw new Exception("No existe el criterio de búsqueda");
        }

        return lista.toList(elementos);
    }

    public static <T> ListaDinamica<T> ShellSort(ListaDinamica<T> lista, Integer Orden, String Campo) {
        int n = lista.getLongitud();
        T[] elementos = lista.toArray();

        for (int intervalo = n / 2; intervalo > 0; intervalo /= 2) {
            for (int i = intervalo; i < n; i++) {
                T ayuda = elementos[i];
                int j;
                for (j = i; j >= intervalo && comparar(elementos[j - intervalo], ayuda, Campo, Orden); j -= intervalo) {
                    elementos[j] = elementos[j - intervalo];
                }
                elementos[j] = ayuda;
            }
        }
        return lista.toList(elementos);
    }

    public static <T> ListaDinamica<T> QuickSort(ListaDinamica<T> lista, Integer Orden, String Campo) throws ListaVacia, PosicionNoEncontrada {
        if (lista == null || lista.getLongitud() <= 1) {
            return lista;
        }
        QuickSortRecursivo(lista, 0, lista.getLongitud() - 1, Orden, Campo);
        return lista;
    }

    private static <T> void QuickSortRecursivo(ListaDinamica<T> lista, int inicio, int fin, Integer orden, String Campo) throws ListaVacia, PosicionNoEncontrada {
        if (inicio < fin) {
            int indiceParticion = Particionar(lista, inicio, fin, orden, Campo);
            QuickSortRecursivo(lista, inicio, indiceParticion - 1, orden, Campo);
            QuickSortRecursivo(lista, indiceParticion + 1, fin, orden, Campo);
        }
    }

    private static <T> int Particionar(ListaDinamica<T> lista, int inicio, int fin, Integer orden, String Campo) throws ListaVacia, PosicionNoEncontrada {
        T pivote = lista.getInfo(fin);
        int i = inicio - 1;

        for (int j = inicio; j < fin; j++) {
            if (comparar(pivote, lista.getInfo(j), Campo, orden)) {
                i++;
                Intercambiar(lista, i, j);
            }
        }
        Intercambiar(lista, i + 1, fin);
        return i + 1;
    }

    private static <T> void Intercambiar(ListaDinamica<T> lista, int i, int j) throws ListaVacia, PosicionNoEncontrada {
        T ayuda = lista.getInfo(i);
        lista.modificarPosicion(lista.getInfo(j), i);
        lista.modificarPosicion(ayuda, j);
    }

    @SuppressWarnings("unused")
    public static <T> ListaDinamica<T> BusquedaBinaria(ListaDinamica<T> lista, String Busqueda, String Campo) throws ListaVacia, PosicionNoEncontrada {
        ListaDinamica<T> listaOrdenada = QuickSort(lista, 1, Campo);
        ListaDinamica<T> ListaElementos = new ListaDinamica<>();

        boolean encontrado = false;
        int inicio = 0;
        int fin = listaOrdenada.getLongitud() - 1;

        while (inicio <= fin) {
            int medio = (inicio + fin) / 2;
            T Mitad = listaOrdenada.getInfo(medio);
            Field campo = getField(Mitad.getClass(), Campo);

            try {
                campo.setAccessible(true);
                Object ObjetoMitad = campo.get(Mitad);
                String valorMedio = (ObjetoMitad != null) ? ObjetoMitad.toString() : "";
                if (valorMedio.toLowerCase().contains(Busqueda.trim().toLowerCase())) {
                    encontrado = true;
                    ListaElementos.AgregarFinal(Mitad);
                    int j = medio - 1;
                    while (j >= 0) {
                        T elementoAnterior = listaOrdenada.getInfo(j);
                        Object valorAnteriorObj = campo.get(elementoAnterior);
                        String valorAnterior = (valorAnteriorObj != null) ? valorAnteriorObj.toString() : "";

                        if (valorAnterior.toLowerCase().contains(Busqueda.trim().toLowerCase())) {
                            ListaElementos.AgregarFinal(elementoAnterior);
                            j--;
                        } 
                        else {
                            break;
                        }
                    }
                    int i = medio + 1;
                    while (i < listaOrdenada.getLongitud()) {
                        T elementoSiguiente = listaOrdenada.getInfo(i);
                        Object valorSiguienteObj = campo.get(elementoSiguiente);
                        String valorSiguiente = (valorSiguienteObj != null) ? valorSiguienteObj.toString() : "";

                        if (valorSiguiente.toLowerCase().contains(Busqueda.trim().toLowerCase())) {
                            ListaElementos.AgregarFinal(elementoSiguiente);
                            i++;
                        } 
                        else {
                            break;
                        }
                    }
                    break;
                } 
                else {
                    String valorABuscar = Busqueda;
                    if (valorMedio.compareToIgnoreCase(valorABuscar) > 0) {
                        fin = medio - 1;
                    } 
                    else {
                        inicio = medio + 1;
                    }
                }
            } 
            catch (IllegalAccessException e) {
                
            }
        }
        return ListaElementos;
    }
    
    public static <T> ListaDinamica<T> BusquedaLineal(ListaDinamica<T> lista, String busqueda, String campo) throws ListaVacia, PosicionNoEncontrada {
        ListaDinamica<T> resultado = new ListaDinamica<>();
        Integer ultimaPosicionOcupada = lista.getLongitud();

        for (int i = 0; i < ultimaPosicionOcupada; i++) {
            T elemento = lista.getInfo(i);
            try {
                Field campoObjeto = getField(elemento.getClass(), campo);

                if (campoObjeto != null) {
                    campoObjeto.setAccessible(true);
                    Object valorObj = campoObjeto.get(elemento);
                    String valorCampo = (valorObj != null) ? valorObj.toString() : "";
                    if (buscarTipoEspecifico(valorCampo, busqueda)) {
                        resultado.AgregarFinal(elemento);
                        continue;
                    }
                }

                String[] subcampos = campo.split("\\.");
                Object objetoActual = elemento;

                for (String subcampo : subcampos) {
                    Field subcampoObjeto = getField(objetoActual.getClass(), subcampo);

                    if (subcampoObjeto != null) {
                        subcampoObjeto.setAccessible(true);
                        objetoActual = subcampoObjeto.get(objetoActual);
                    } 
                    else {
                        objetoActual = null;
                        break;
                    }
                }
                if (objetoActual != null) {
                    String valorCampo = objetoActual.toString();

                    if (buscarTipoEspecifico(valorCampo, busqueda)) {
                        resultado.AgregarFinal(elemento);
                    }
                }
            } 
            catch (IllegalAccessException e) {
                
            }
        }
        return resultado;
    }
    
    private static boolean buscarTipoEspecifico(String texto, String busqueda) {
        String textoSinEspacios = texto.replaceAll("\\s", "").replaceAll("[^a-zA-Z0-9]", "");
        String busquedaSinEspacios = busqueda.replaceAll("\\s", "").replaceAll("[^a-zA-Z0-9]", "");
        return textoSinEspacios.toLowerCase().startsWith(busquedaSinEspacios.toLowerCase());
    }
    
    private static Boolean stringLength(String string, int length) {
        if (string.length() == length)
            return true;
        return false;
    }

    private static byte sumDigits(byte[] digits) {
        byte verifier;
        byte sum = 0;
        for (byte i = 0; i < digits.length; i = (byte) (i + 2)) {
            verifier = (byte) (digits[i] * 2);
            if (verifier > 9)
                verifier = (byte) (verifier - 9);
            sum = (byte) (sum + verifier);
        }
        for (byte i = 1; i < digits.length; i = (byte) (i + 2)) {
            verifier = (byte) (digits[i] * 1);
            sum = (byte) (sum + verifier);
        }
        return (byte) ((sum - (sum % 10) + 10) - sum);
    }

    public static Boolean idCardEcuador(String idCard) {
        try {
            if (stringLength(idCard, 10)) {
                String[] data = idCard.split("");
                byte verifier = Byte.parseByte(data[0] + data[1]);
                byte[] digits = new byte[9];
                for (byte i = 0; i < 9; i++)
                    digits[i] = Byte.parseByte(data[i]);        
                if (verifier >= 1 && verifier <= 24) {
                    verifier = digits[2];
                    if (verifier <= 6) {
                        if (sumDigits(digits) == Byte.parseByte(data[9]))
                            return true;
                    }
                }
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static String getDirPoject() {
        return System.getProperty("user.dir");
    }
    
    public static String getOS() {
        return System.getProperty("os.name");
    }
    
    public static void abrirNavegadorPredeterminadorWindows(String url) throws Exception{
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
    }
    
    public static void abrirNavegadorPredeterminadorLinux(String url) throws Exception{
        Runtime.getRuntime().exec("xdg-open " + url);
    }
    
    public static void abrirNavegadorPredeterminadorMacOsx(String url) throws Exception{
        Runtime.getRuntime().exec("open " + url);
    }
    
}
