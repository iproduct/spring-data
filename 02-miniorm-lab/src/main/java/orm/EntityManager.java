package orm;

import orm.annotation.Column;
import orm.annotation.Entity;
import orm.annotation.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static orm.EntityManagerUtils.*;

public class EntityManager<T> implements DbContext<T> {
    private static final String SELECT_STAR_FROM = "SELECT * FROM ";
    private static final String INSERT_QUERY = "INSERT INTO %s (%s) VALUE (%s) ;";
    private static final String UPDATE_QUERY = "UPDATE %s SET %s WHERE %s ;";
    private static final String DELETE_QUERY = "DELETE FROM %s WHERE %s ;";
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int persist(Object entity) throws IllegalAccessException, SQLException {
        Field primary = getIdField(entity.getClass());
        primary.setAccessible(true);
        Object value = primary.get(entity);

        return (value != null && (int) value > 0) ?
                doUpdate(entity, primary) :
                doInsert(entity, primary);
    }

    @Override
    public List<T> find(Class<T> table, String where, Object... values) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Statement statement = connection.createStatement();
        String query = SELECT_STAR_FROM +  getTableName(table) +
                (where.equals("") ? "" : " WHERE " + where + ";");

        ResultSet resultSet = executeQuery(connection, query, values);
        List<T> result = new ArrayList<>();
        while(resultSet.next()) {
            T entity = table.getConstructor().newInstance();
            fillEntity(table, resultSet, entity);
            result.add(entity);
        }
        return result;
    }

    @Override
    public T findFirst(Class<T> table, String where, Object... values) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Statement statement = connection.createStatement();
        String query = SELECT_STAR_FROM +  getTableName(table) +
                (where.equals("") ? "" : " WHERE " + where + " LIMIT 1;");

        ResultSet resultSet = executeQuery(connection, query, values);
        T entity = table.getConstructor().newInstance();
        if(resultSet.next()) {
            fillEntity(table, resultSet, entity);
            return entity;
        } else {
            return null;
        }
    }

    @Override
    public T findById(Class<T> table, int id) throws IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return findFirst(table, "id = ?", id);
    }

    @Override
    public int delete(Class<T> table, int id) throws SQLException {
        String query = String.format(DELETE_QUERY, getTableName(table), "id = " +id);
        return executeUpdate(connection, query);
    }


    // Utility methods
    private int doInsert(Object entity, Field primary) throws SQLException {
        String tableName = getTableName(entity.getClass());
        String fieldNamesStr = getFieldNames(entity).stream().collect(Collectors.joining(", "));
        String fieldValuesStr = getFieldValues(entity).stream().collect(Collectors.joining(", "));
        String insertQuery = String.format(INSERT_QUERY, tableName, fieldNamesStr, fieldValuesStr);
        return executeUpdate(connection, insertQuery);
    }

    private int doUpdate(Object entity, Field primaryKey) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());
        Function<Field, String> getFieldNameAndValue = (Field x) -> {
            x.setAccessible(true);
            try {
                return String.format(" %s = %s",
                        x.isAnnotationPresent(Id.class)
                                ? "id" : x.getAnnotation(Column.class).name(),
                        x.getType() == String.class || x.getType() == LocalDate.class
                                ? "'" + x.get(entity) + "'"
                                : x.get(entity).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return "";
        };
        List<String> setFieldNameAndValues = Arrays.stream(entity.getClass().getDeclaredFields())
                .map(getFieldNameAndValue)
                .collect(Collectors.toList());

        String insertQuery = String.format(UPDATE_QUERY,
                tableName, String.join(", ", setFieldNameAndValues),
                " id = " + primaryKey.get(entity));

        return executeUpdate(connection, insertQuery);
    }


}
