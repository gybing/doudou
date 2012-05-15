package doudou.util.dao;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class BaseEntityDao<T extends Serializable, K> implements EntityDao<T , K> {

    private final DatabaseDao database;
    private final String nsName;

    public BaseEntityDao(DatabaseDao database) {
        this.database = database;
        String ns = getNamespace();
        if (ns == null || ns.isEmpty()) {
            this.nsName = "";
        } else {
            this.nsName = ns + ".";
        }
    }

    public final Object create(T newObject) {
        return create("create", newObject);
    }

    public final T read(K id) {
        return (T) readObject("read", id);
    }

    public final int update(T changedObject) {
        return update("update", changedObject);
    }

    public final int delete(K id) {
        return delete("delete", id);
    }

    protected final Object create(String id, Object parameterObject) {
        return database.getClient().insert(nsName + id, parameterObject);
    }

    protected final T read(String id, Object parameterObject) {
        return (T) readObject(id, parameterObject);
    }

    protected final List<T> reads(String id, Object parameterObject) {
        return (List<T>) readObjects(id, parameterObject);
    }

    protected final Object readObject(String id, Object parameterObject) {
        return database.getClient().queryForObject(nsName + id, parameterObject);
    }

    protected final List readObjects(String id, Object parameterObject) {
        return database.getClient().queryForList(nsName + id, parameterObject);
    }

    protected final int update(String id, Object parameterObject) {
        return database.getClient().update(nsName + id, parameterObject);
    }

    protected final int delete(String id, Object parameterObject) {
        return database.getClient().delete(nsName + id, parameterObject);
    }

    protected final int count(String id, Object parameterObject) {
        Integer count = (Integer) readObject(id, parameterObject);
        return count == null ? 0 : count;
    }
}