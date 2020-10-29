package dbContext;

import annotation.Column;
import annotation.Entity;
import annotation.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class EntityManager<T> implements DbContext<T> {
    private static final String SELECT_STAR_FROM = "SELECT * FROM ";
    private static final String INSERT_QUERY = "INSERT INTO %s (id, %s) VALUE (%s) ;";
    private static final String UPDATE_QUERY = "UPDATE %s SET %s WHERE %s ;";
    private static final String DELETE_QUERY = "DELETE FROM %s WHERE %s ;";
    private final Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(T entity) throws IllegalAccessException, SQLException {
        Field primaryKey = getId(entity.getClass());
        primaryKey.setAccessible(true);
        int value = primaryKey.getInt(entity);

        return value > 0 ? doUpdate(entity, primaryKey) : doInsert(entity, primaryKey);
    }

    private <E> boolean doUpdate(T entity, Field primaryKey) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());

        List<String> setFieldNameAndValues = Arrays.stream(entity.getClass()
                .getDeclaredFields())
                .map(x->{
                    x.setAccessible(true);
                    try {
                        return String.format(" %s = %s",
                                x.isAnnotationPresent(Id.class)
                                    ? "id" :x.getAnnotation(Column.class).name(),
                                x.getType() == String.class || x.getType() == LocalDate.class
                                        ? "'" + x.get(entity) + "'"
                                        : x.get(entity).toString());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .collect(Collectors.toList());

        String insertQuery = String.format(UPDATE_QUERY,
                tableName, String.join(", ", setFieldNameAndValues),
                " id = " + primaryKey.get(entity));

        return executeQuery(insertQuery);
    }

    private String getTableName(Class<?> aClass) {
        return aClass.getAnnotation(Entity.class)
                .name();
    }

    private <E> boolean doInsert(E entity, Field primaryKey) throws SQLException {

        String tableName = getTableName(entity.getClass());

        List<String> fieldNames = getFieldNames(entity);

        List<String> fieldValues = getFieldValues(entity);

        String insertQuery = String.format(INSERT_QUERY,
                tableName, String.join(", ", fieldNames),
                String.join(", ", fieldValues));

        return executeQuery(insertQuery);
    }

    private boolean executeQuery(String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        return preparedStatement.execute();
    }

    private <E> List<String> getFieldValues(E entity) {
        return Arrays.stream(entity.getClass()
                .getDeclaredFields())
                .map(x -> {
                    x.setAccessible(true);

                    try {
                        return x.getType() == String.class || x.getType() == LocalDate.class
                                ? "'" + x.get(entity) + "'"
                                : x.get(entity).toString();

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return "";
                })
                .collect(Collectors.toList());
    }

    private <E> List<String> getFieldNames(E entity) {
       return Arrays.stream(entity.getClass()
                .getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Column.class))
                .map(x -> {
                    x.setAccessible(true);
                    return x.getAnnotation(Column.class).name();
                })
                .collect(Collectors.toList());
    }


    @Override
    public List<T> find(Class<T>table ,String where) throws SQLException, IllegalAccessException, InstantiationException {

        return null;
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


    @Override
    public T findById(Class<T> table, int id) throws IllegalAccessException, SQLException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        return findFirst(table, "WHERE id = " + id);
    }

    @Override
    public boolean delete(Class<T> table, int id) throws SQLException {
        String query = String.format(DELETE_QUERY, getTableName(table), "id = " +id);

        return executeQuery(query);
    }

    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity dose not have primary key."));
    }


}
