package enums;

public enum TipoUsuario {
	Administrador(1), Funcionario(2);

	private final int value;

	TipoUsuario(int valueOption) {
		value = valueOption;
	}

	public int getValue() {
		return value;
	}
}
