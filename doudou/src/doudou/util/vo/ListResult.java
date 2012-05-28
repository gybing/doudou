package doudou.util.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class ListResult<T extends Serializable> implements Serializable {

	private  List<T> entities;
	private  int size;
	
	public ListResult(List<T> entities, int size) {
		this.entities = entities;
		this.size = size;
	}
	
	public ListResult() {
		this(new ArrayList<T>(), 0);
	}
	
	public List<T> getEntities() {
		return entities;
	}

	public int getSize() {
		return size;
	}

    @SuppressWarnings("rawtypes")
    public final static ListResult EMPTY = new ListResult();
	
    @SuppressWarnings("unchecked")
	public static final <X extends Serializable> ListResult<X>  emptyResult(){
    	return (ListResult<X>)EMPTY;
    }
}
