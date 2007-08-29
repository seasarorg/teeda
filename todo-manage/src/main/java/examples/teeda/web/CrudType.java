package examples.teeda.web;

public final class CrudType {

	public static final int CREATE = 0;

	public static final int READ = 1;

	public static final int UPDATE = 2;

	public static final int DELETE = 3;

	public static String toString(int type) {
		String result = "";
		switch (type) {
		case CREATE:
			result = "CREATE";
			break;
		case UPDATE:
			result = "UPDATE";
			break;
		case DELETE:
			result = "DELETE";
			break;
		default:
			break;
		}
		return result;
	}
}