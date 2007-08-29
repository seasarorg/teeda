package examples.teeda.helper;

import java.util.ArrayList;
import java.util.List;

import examples.teeda.dto.ItemDto;

/**
 * @author yone
 */
public class SelectHelper {
	private static final String[] TYPE_ARRAY = { "仕事", "遊び", "友達", "趣味", "家族",
			"習い事", "スポーツ", "その他" };

	private static final String[] STATE_ARRAY = { "未着手", "対応中", "完了", "保留",
			"その他" };

	private static final String[] PRIORITY_ARRAY = { "低", "普通", "高", "その他" };

	private static final String[] PRIORITY_IMG_ARRAY = {
			"/todo-manage/img/low.gif", "/todo-manage/img/normal.gif",
			"/todo-manage/img/high.gif", "/todo-manage/img/other.gif" };

	private static List<ItemDto> TYPE_ITEMS = new ArrayList<ItemDto>(
			TYPE_ARRAY.length);

	private static List<ItemDto> STATUS_ITEMS = new ArrayList<ItemDto>(
			STATE_ARRAY.length);

	private static List<ItemDto> PRIORITY_ITEMS = new ArrayList<ItemDto>(
			PRIORITY_ARRAY.length);

	static {
		for (int i = 0; i < TYPE_ARRAY.length; i++) {
			TYPE_ITEMS.add(new ItemDto(new Integer(i + 1), TYPE_ARRAY[i]));
		}
		for (int i = 0; i < STATE_ARRAY.length; i++) {
			STATUS_ITEMS.add(new ItemDto(new Integer(i + 1), STATE_ARRAY[i]));
		}
		for (int i = 0; i < PRIORITY_ARRAY.length; i++) {
			PRIORITY_ITEMS.add(new ItemDto(new Integer(i + 1),
					PRIORITY_ARRAY[i]));
		}
	}

	public static String getType(int type) {
		return TYPE_ARRAY[type - 1];
	}

	public static String getState(int state) {
		return STATE_ARRAY[state - 1];
	}

	public static String getPriority(int priority) {
		return PRIORITY_ARRAY[priority - 1];
	}

	public static String getPriorityImg(int priority) {
		return PRIORITY_IMG_ARRAY[priority - 1];
	}

	public static List getTypeItems() {
		return TYPE_ITEMS;
	}

	public static List getStateItems() {
		return STATUS_ITEMS;
	}

	public static List getPriorityItems() {
		return PRIORITY_ITEMS;
	}
}