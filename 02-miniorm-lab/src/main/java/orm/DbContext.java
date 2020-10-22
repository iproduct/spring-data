package orm;

public interface DbContext<E> {
    boolean persist(E entity) throws IllegalAccessException;
    Iterable<E> find(Class<E> table);
    Iterable<E> find(Class<E> table, String where);
    E findFirst(Class<E> table);
    E findFirst(Class<E> table, String where);
}
