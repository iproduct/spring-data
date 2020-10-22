package orm;

import orm.annotation.Column;
import orm.annotation.Entity;
import orm.annotation.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

class EntityManagerUtils {

    static ResultSet executeQuery(Connection connection, String sql, Object... values) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        for(int i = 0; i < values.length; i++) {
            switch(values[i].getClass().getSimpleName()) {
                case "Integer": ps.setInt(i + 1, (int) values[i]); break;
                case "Double": ps.setDouble(i + 1, (double) values[i]); break;
                default : ps.setString(i + 1, values[i].toString()); break;
            }
        }
        return ps.executeQuery();
    }

    static int executeUpdate(Connection connection, String sql) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(sql);
        return ps.executeUpdate();
    }


    static Field getIdField(Class entity) {
        return Arrays.stream(entity.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("Entity does not have a primary key."));
    }

    static String getTableName(Class<?> entity) {
        Entity entityAnnotation = entity.getAnnotation(Entity.class);
        if (entityAnnotation != null && entityAnnotation.name().length() > 0) {
            return entityAnnotation.name();
        } else {
            return entity.getSimpleName();
        }
    }

    static List<String> getFieldNames(Object entity) {
        return Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .map(field -> {
                    field.setAccessible(true);
                    return field.getAnnotation(Column.class).name();
                })
                .collect(Collectors.toList());
    }

    static List<String> getFieldValues(Object entity) {
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

    static <T>void fillEntity(Class<T> table, ResultSet resultSet, T entity) throws SQLException, IllegalAccessException {

        Field[] declaredFields = table.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);

            fillField(field, entity, resultSet,
                    field.isAnnotationPresent(Id.class)
                            ? "id" : field.getAnnotation(Column.class).name());
        }
    }

    static <T>void fillField(Field field, T entity, ResultSet resultSet, String name) throws SQLException, IllegalAccessException {
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
