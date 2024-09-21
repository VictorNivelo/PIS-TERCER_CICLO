
package Controlador.Dao.BaseDatos;

import Controlador.TDA.ListaDinamica.ListaDinamica;

/**
 *
 * @author Victor
 * @param <T>
 */
public interface InterfazDaoBD<T> {

    /**
     * Metodo que permite realizar el guardaddo
     *
     * @param obj Objeto del modelo
     * @return El id generado producto del guardado
     * @throws java.lang.Exception
     */
    public Integer guardar(T objb, String sequenceName) throws Exception;

    /**
     * Permite modificar los datos en un repositorio de datos
     *
     * @param obj Objeto a modificar
     * @throws java.lang.Exception
     */
    public void modificar(T obj) throws Exception;

    /**
     * LIstado de objetos en la BD
     *
     * @return Una ListaEnlazada
     */
    public ListaDinamica<T> listar();

    public T obtener(Integer id);
    
    public Boolean eliminar(Integer id);
    
}
