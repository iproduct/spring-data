package orm;

import orm.annotations.Column;
import orm.annotations.Entity;
import orm.annotations.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityManager<T> {
    private static final String SELECT_STAR_FROM = "SELECT * FROM ";
    private static final String INSERT_QUERY = "INSERT INTO %s (id, %s) VALUE (%s) ;";
    private static final String UPDATE_QUERY = "UPDATE %s SET %s WHERE %s ;";
    private static final String DELETE_QUERY = "DELETE FROM %s WHERE %s ;";
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean persist(T entity) throws IllegalAccessException, SQLException {
        Field primaryKey = getId(entity.getClass());
        primaryKey.setAccessible(true);
        int value = primaryKey.getInt(entity);

        return value > 0 ? doUpdate(entity, primaryKey) : doInsert(entity, primaryKey);
    }

    private boolean doInsert(T entity, Field primaryKey) throws SQLException {
        String tableName = getTableName(entity.getClass());

        List<String> fieldNames = getFieldNames(entity);

        List<String> fieldValues = getFieldValues(entity);

        String insertQuery = String.format(INSERT_QUERY,
                tableName, String.join(", ", fieldNames),
                String.join(", ", fieldValues));

        return executeQuery(insertQuery);
    }

    private <E> boolean doUpdate(T entity, Field primaryKey) throws IllegalAccessException, SQLException {
        String tableName = getTableName(entity.getClass());

        List<String> setFieldNameAndValues = Arrays.stream(entity.getClass()
                .getDeclaredFields())
                .map(getFieldNameAndValue(entity))
                .collect(Collectors.toList());

        String insertQuery = String.format(UPDATE_QUERY,
                tableName, String.join(", ", setFieldNameAndValues),
                " id = " + primaryKey.get(entity));

        return executeQuery(insertQuery);
    }

    // Utility methods
    private Field getId(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(x -> x.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have a primary key."));
    }

    private String getTableName(Class<?> entity) {
        Entity entityAnnotation = entity.getAnnotation(Entity.class);
        if (entityAnnotation != null && entityAnnotation.name() != null) {
            return entityAnnotation.name();
        } else {
            return entity.getClass().getSimpleName();
        }
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

    private boolean executeQuery(String query) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        return preparedStatement.execute();
    }

    private Function<Field, String> getFieldNameAndValue(T entity) {
        return x -> {
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
    }

}
