package enums;

public enum RequestType {
	Edit(1), Create(2), Delete(3), View(4);

	private final int value;

	RequestType(int valueOption) {
		value = valueOption;
	}

	public int getValue() {
		return value;
	}
}
