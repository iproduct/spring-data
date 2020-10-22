package orm;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface DbContext<T> {
    boolean persist(T entity) throws IllegalAccessException, SQLException;

    List<T> find(Class<T> table , String where) throws SQLException, IllegalAccessException, InstantiationException;

    T findFirst(Class<T> table, String where) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    T findById(Class<T> table, int id) throws IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException;

    boolean delete(Class<T> table, int id) throws SQLException;
}
