package mayaya.util.dao;

public interface EntityDao<T, K> extends CRUDDao<T, K> {

	String getNamespace();
	
}
