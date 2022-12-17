public class MulCommand implements Command {
	private int value = 0;

	// コンストラクタ
	public MulCommand(int value) {
		this.value = value;
	}

	// 実行
	@Override
	public void exec(Calc calc) {
		calc.currentResult *= value;
	}

	// アンドゥ
	@Override
	public void undo(Calc calc) {
		calc.currentResult /= value;
	}

	// テキスト表現
	@Override
	public String getText() {
		return "*" + value;
	}

}
