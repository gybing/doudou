package doudou.vo.type;

public enum TodoType {
	Friend, Comment, 
	NewPicture, NewEvent, NewMessage, 
	ModPicture, ModEvent, ModMessage,
	DelPicture, DelEvent, DelMessage;
	
	public static boolean isNewType(TodoType type) {
		if (null != type && type.name().startsWith("New")) {
			return true;
		} else {
			return false;
		}
	}
}
