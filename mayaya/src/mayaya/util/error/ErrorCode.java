package mayaya.util.error;

public enum ErrorCode {
	NotAuthed(-1,"非法用户"), SomeThingElse(0,"asdf");
	
	
	private int value;
	private String text;
	
	
	private ErrorCode(int value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public int toValue() {
		return value;
	}
	
	public String toText() {
		return text;
	}
	
}
