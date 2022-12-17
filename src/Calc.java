import java.util.ArrayDeque;

import javax.swing.JTextField;

public class Calc {
	// Singleton
	private static final Calc calc = new Calc();
	// 現在の結果
	public double currentResult = 0;
	// コマンド履歴
	private ArrayDeque<Command> history = new ArrayDeque<>();

	// 表示
	private JTextField historyField;
	private JTextField resultField;

	public void setField(JTextField historyField, JTextField resultField) {
		this.historyField = historyField;
		this.resultField = resultField;
	}

	private void showHistory() {
		// history内のコマンドの文字表現を取り出して、つなげて、historyFieldに表示
		var historyStr = "";
		var ite = history.iterator();
		while (ite.hasNext())
			historyStr += ite.next().getText();
		historyField.setText("0" + historyStr);

		// Stream を使って書きかえると例えばこうです。
		// historyField.setText("0" + String.join("", history.stream().map(cmd -> cmd.getText()).toList()));
	}

	public void enqueue(Command com) {
		history.addLast(com);
		showHistory();
	}

	public void execLastCommand() {
		if (history.size() == 0)
			return;
		history.getLast().exec(calc);
		resultField.setText(String.valueOf(currentResult));
	}

	public void clear() {
		history.clear();
		showHistory();
		resultField.setText("0.0");
	}

	public void undo() {
		if (history.size() == 0)
			return;
		Command last = history.removeLast();
		last.undo(calc);
		resultField.setText(String.valueOf(currentResult));
		showHistory();
	}

	private Calc() {
	}

	public static Calc getInstance() {
		return calc;
	}

}
