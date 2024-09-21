
package Controlador.Dao.BaseDatos;

import Controlador.TDA.ListaDinamica.ListaDinamica;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Victor
 * @param <T>
 */
public class AdaptadorDaoBD<T> implements InterfazDaoBD<T> {

    /**
     * Obejto Conexion
     */
    private ConexionBD conexion;
    /**
     * Class del modelo a usar
     */
    @SuppressWarnings("rawtypes")
    private Class clazz;

    /**
     * Constructor de la clase
     *
     * @param clazz El objeto de la clase del modelo Ejemplo: Persona.class
     */
    @SuppressWarnings("rawtypes")
    public AdaptadorDaoBD(Class clazz) {
        this.conexion = new ConexionBD();
        this.clazz = clazz;
    }

    /**
     * Metodo que permite guardar
     *
     * @param obj El objeto del modelo lleno
     * @return La llave primaria generada por el motor de base de datos (se
     * sugiere construir la tabla de base de datos con la generacion de id auto
     * incementable)
     * @throws Exception Cuando no se puede guardar en la base de datos
     */
    public Integer guardar(T obj, String sequenceName) throws Exception {
        String query = queryInsert(obj);
        Integer idGenerado = -1;

        try (PreparedStatement statement = conexion.getConnection().prepareStatement(query)) {
            statement.executeUpdate();

            // Recupera el valor de la secuencia después de la inserción
            try (Statement seqStatement = conexion.getConnection().createStatement()) {
                ResultSet resultSet = seqStatement.executeQuery("SELECT " + sequenceName + ".CURRVAL FROM dual");
                if (resultSet.next()) {
                    idGenerado = resultSet.getInt(1);
                }
            }
        } finally {
            conexion.getConnection().close();
            conexion.setConnection(null);
        }
        return idGenerado;
    }
    
    public Boolean guardarb(T obj) throws Exception {
        String query = queryInsert(obj);
        Boolean band = false;

        try (PreparedStatement statement = conexion.getConnection().prepareStatement(query)) {
            int affectedRows = statement.executeUpdate();
            band = affectedRows > 0;
        } finally {
            conexion.getConnection().close();
            conexion.setConnection(null);
        }
        return band;
    }


    /**
     * Metodo que permite modificar un registro en la base de datos, para
     * modificar se debe primero consultar el Objeto haciendo uso del metodo
     * Obtener
     *
     * @param obj El objeto del modelo a modificar
     * @throws Exception Alguna Excepcion si no modifica
     */
    @Override
    public void modificar(T obj) throws Exception {
        String query = queryUpdate(obj);
        Statement st = conexion.getConnection().createStatement();
        st.executeUpdate(query);
        conexion.getConnection().close();
        conexion.setConnection(null);
    }

    /**
     * Metodo que permite sacar los datos de la base de datos
     *
     * @return Un Objeto de la ListaEnlazada con los datos llenos
     */
    @Override
    public ListaDinamica<T> listar() {

        ListaDinamica<T> lista = new ListaDinamica<>();
        try {
            Statement stmt = conexion.getConnection().createStatement();
            String query = "SELECT * FROM " + clazz.getSimpleName().toLowerCase();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                lista.Agregar(llenarObjeto(rs));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return lista;
    }

    /**
     * Permite obtener un objeto de la base de datos a travez del Id
     *
     * @param id El id a buscar en la base de datos
     * @return El objeto buscado, es null si no esxiste el objeto
     */
    @Override
    public T obtener(Integer id) {
        T data = null;
        try {
            Statement stmt = conexion.getConnection().createStatement();
            String query = "select * from " + clazz.getSimpleName().toLowerCase() + " where id = " + id;
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                data = llenarObjeto(rs);
            }
        } catch (Exception e) {
        }
        return data;
    }
    
    @Override
    public Boolean eliminar(Integer id) {
        try {
            Statement stmt = conexion.getConnection().createStatement();
            String query = "DELETE FROM " + clazz.getSimpleName().toLowerCase() + " WHERE id = " + id;
            stmt.executeUpdate(query);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //--------------ESTO ES DEL CRUD NO MODIFICAR AL MENOS QUE LO AMERITE------
    @SuppressWarnings("unchecked")
    private T llenarObjeto(ResultSet rs) {
        T data = null;
        try {
            data = (T) clazz.getDeclaredConstructor().newInstance();
            for (Field f : clazz.getDeclaredFields()) {
                String atributo = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                fijarDatos(f, rs, data, atributo);
            }
            for (Field f : clazz.getSuperclass().getDeclaredFields()) {
                String atributo = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                fijarDatos(f, rs, data, atributo);
            }

        } 
        catch (Exception e) {
            System.out.println("error " + e);
        }
        return data;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void fijarDatos(Field f, ResultSet rs, T data, String atributo) {
        try {
            Method m = null;

            if (f.getType().getSimpleName().equalsIgnoreCase("String")) {
                m = clazz.getMethod("set" + atributo, String.class);
                m.invoke(data, rs.getString(atributo));
            }

            if (f.getType().getSimpleName().equalsIgnoreCase("Integer")) {
                m = clazz.getMethod("set" + atributo, Integer.class);
                m.invoke(data, rs.getInt(atributo));
            }

            if (f.getType().getSimpleName().equalsIgnoreCase("Double")) {
                m = clazz.getMethod("set" + atributo, Double.class);
                m.invoke(data, rs.getDouble(atributo));
            }

            if (f.getType().getSimpleName().equalsIgnoreCase("Boolean")) {
                m = clazz.getMethod("set" + atributo, Boolean.class);
                m.invoke(data, rs.getBoolean(atributo));
            }

            if (f.getType().getSimpleName().equalsIgnoreCase("Date")) {
                m = clazz.getMethod("set" + atributo, Date.class);
                m.invoke(data, rs.getDate(atributo));
            }

            if (f.getType().isEnum()) {

                m = clazz.getMethod("set" + atributo, (Class<Enum>) f.getType());
                m.invoke(data, Enum.valueOf((Class<Enum>) f.getType(), rs.getString(atributo)));
            }
        } 
        catch (Exception e) {
            System.out.println("Error al fijar datos " + e);
        }
    }

    @SuppressWarnings("unchecked")
    private HashMap<String, Object> obtenerObjeto(T obj) {
        HashMap<String, Object> mapa = new HashMap<>();
        try {
            for (Field f : clazz.getDeclaredFields()) {
                Method m = null;
                String atributo = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                m = clazz.getMethod("get" + atributo);
                Object aux = m.invoke(obj);
                if (aux != null) {
                    mapa.put(atributo.toLowerCase(), aux);
                }
            }

            for (Field f : clazz.getSuperclass().getDeclaredFields()) {
                Method m = null;
                String atributo = f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1);
                m = clazz.getMethod("get" + atributo);
                Object aux = m.invoke(obj);
                if (aux != null) {
                    mapa.put(atributo.toLowerCase(), aux);
                }

            }
        } 
        catch (Exception e) {
            System.out.println("No se pudo tener dato");
        }
        return mapa;
    }

    private String queryInsert(T obj) {
        HashMap<String, Object> mapa = obtenerObjeto(obj);
        String query = "INSERT INTO " + clazz.getSimpleName().toLowerCase() + " (";
        for (Map.Entry<String, Object> entry : mapa.entrySet()) {
            query += entry.getKey() + ",";

        }
        query = query.substring(0, query.length() - 1);
        query += ") VALUES (";
        for (Map.Entry<String, Object> entry : mapa.entrySet()) {

            if (entry.getValue().getClass().getSuperclass().getSimpleName().equalsIgnoreCase("Number") || entry.getValue().getClass().getSimpleName().equalsIgnoreCase("Boolean")) {
                query += entry.getValue() + ", ";
            }
            if (entry.getValue().getClass().getSimpleName().equalsIgnoreCase("Date")) {
//                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                query += '"' + formato.format(entry.getValue()) + '"' + ", ";
            }
            if (entry.getValue().getClass().isEnum() || entry.getValue().getClass().getSimpleName().equalsIgnoreCase("String")) {
                query += '"' + entry.getValue().toString() + '"' + ", ";
            }
        }
        query = query.substring(0, query.length() - 2);
        query += ")";
        return query;
    }

    private String queryUpdate(T obj) {
        HashMap<String, Object> mapa = obtenerObjeto(obj);
        String query = "UPDATE " + clazz.getSimpleName().toLowerCase() + " SET ";
        Integer id = 0;
        for (Map.Entry<String, Object> entry : mapa.entrySet()) {
            if (entry.getKey().toString().equalsIgnoreCase("id")) {
                id = (Integer) entry.getValue();
            } 
            else {
                query += entry.getKey() + " = ";
                if (entry.getValue().getClass().getSuperclass().getSimpleName().equalsIgnoreCase("Number") || entry.getValue().getClass().getSimpleName().equalsIgnoreCase("Boolean")) {
                    query += entry.getValue() + ", ";
                }
                if (entry.getValue().getClass().getSimpleName().equalsIgnoreCase("Date")) {
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                    query += '"' + formato.format(entry.getValue()) + '"' + ", ";
                }
                if (entry.getValue().getClass().isEnum() || entry.getValue().getClass().getSimpleName().equalsIgnoreCase("String")) {
                    query += '"' + entry.getValue().toString() + '"' + ", ";
                }
            }
        }

        query += "";

        query = query.substring(0, query.length() - 2);
        query += " WHERE id = " + id;
        return query;
    }

    
}

