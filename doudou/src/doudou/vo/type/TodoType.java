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
	
	public static boolean isModType(TodoType type) {
		if (null != type && type.name().startsWith("Mod")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isDelType(TodoType type) {
		if (null != type && type.name().startsWith("Del")) {
			return true;
		} else {
			return false;
		}
	}
}
