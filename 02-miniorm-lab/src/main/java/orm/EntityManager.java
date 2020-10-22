package orm;

import java.sql.Connection;

public class EntityManager implements DbContext {
    private Connection connection;

    public EntityManager(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean persist(Object entity) {
        return false;
    }

    @Override
    public Iterable find(Class table) {
        return null;
    }

    @Override
    public Iterable find(Class table, String where) {
        return null;
    }

    @Override
    public Object findFirst(Class table) {
        return null;
    }

    @Override
    public Object findFirst(Class table, String where) {
        return null;
    }
}
