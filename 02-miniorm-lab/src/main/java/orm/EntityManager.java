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
    public boolean persist(Object entity) throws IllegalAccessException, SQLException {
        Field primary = getIdField(entity.getClass());
        primary.setAccessible(true);
        Object value = primary.get(entity);

        return (value != null && (int) value > 0) ?
                doUpdate(entity, primary) :
                doInsert(entity, primary);
    }

    @Override
    public List<T> find(Class<T> table, String where) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Statement statement = connection.createStatement();
        String query = SELECT_STAR_FROM +  getTableName(table) +
                (where.equals("") ? "" : " " + where +";");

        ResultSet resultSet = statement.executeQuery(query);
        List<T> result = new ArrayList<>();
        while(resultSet.next()) {
            T entity = table.getConstructor().newInstance();
            this.fillEntity(table, resultSet, entity);
            result.add(entity);
        }
        return result;
    }

    @Override
    public T findFirst(Class<T> table, String where) throws SQLException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Statement statement = connection.createStatement();
        String query = SELECT_STAR_FROM +  getTableName(table) +
                (where.equals("") ? "" : " " + where + " LIMIT 1;");

        ResultSet resultSet = statement.executeQuery(query);
        T entity = table.getConstructor().newInstance();
        resultSet.next();
        this.fillEntity(table, resultSet, entity);
        return entity;
    }

    @Override
    public T findById(Class<T> table, int id) throws IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return findFirst(table, "WHERE id = " + id);
    }

    @Override
    public boolean delete(Class<T> table, int id) throws SQLException {
        String query = String.format(DELETE_QUERY, getTableName(table), "id = " +id);

        return executeQuery(query);
    }

    // Utility methods
    private boolean doInsert(Object entity, Field primary) throws SQLException {
        String tableName = getTableName(entity.getClass());
        String fieldNamesStr = getFieldNames(entity).stream().collect(Collectors.joining(", "));
        String fieldValuesStr = getFieldValues(entity).stream().collect(Collectors.joining(", "));
        String insertQuery = String.format(INSERT_QUERY, tableName, fieldNamesStr, fieldValuesStr);
        return executeQuery(insertQuery);
    }

    private boolean doUpdate(Object entity, Field primaryKey) throws IllegalAccessException, SQLException {
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

        return executeQuery(insertQuery);
    }


    private boolean executeQuery(String sql) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.execute();
    }


    private Field getIdField(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have a primary key."));
    }

    private String getTableName(Class<?> entity) {
        Entity entityAnnotation = entity.getAnnotation(Entity.class);
        if (entityAnnotation != null && entityAnnotation.name().length() > 0) {
            return entityAnnotation.name();
        } else {
            return entity.getSimpleName();
        }
    }

    private List<String> getFieldNames(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    field.setAccessible(true);
                    return field.getAnnotation(Column.class).name();
                })
                .collect(Collectors.toList());
    }

    private List<String> getFieldValues(Object entity) {
        Function<Field, String> getFieldValue = field -> {
            field.setAccessible(true);
            try {
                Object value = field.get(entity);
                return field.getType() == String.class || field.getType() == LocalDate.class ?
                        String.format("'%s'", value.toString()) :
                        value.toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return "";
        };
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(getFieldValue)
                .collect(Collectors.toList());
    }

    private void fillEntity(Class<T> table, ResultSet resultSet, T entity) throws SQLException, IllegalAccessException {

        Field[] declaredFields = table.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);

            fillField(field, entity, resultSet,
                    field.isAnnotationPresent(Id.class)
                            ? "id" : field.getAnnotation(Column.class).name());
        }
    }

    private void fillField(Field field, T entity, ResultSet resultSet, String name) throws SQLException, IllegalAccessException {
        field.setAccessible(true);
        switch (name) {
            case "id": field.set(entity, resultSet.getInt("id")); break;
            case "username": field.set(entity, resultSet.getString("username")); break;
            case "password": field.set(entity, resultSet.getString("password")); break;
            case "age": field.set(entity, resultSet.getInt("age")); break;
            case "registrationDate": field.set(entity, resultSet.getString("registration_date")); break;
        }
    }

}
