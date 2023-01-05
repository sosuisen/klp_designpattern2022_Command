import java.util.ArrayDeque;

import javax.swing.JTextField;

public class Calc {
	// Singleton
	private static final Calc calc = new Calc();
	// 現在の結果
	public double currentResult = 0;
	// コマンド列（キュー）
	private ArrayDeque<Command> queue = new ArrayDeque<>();

	private JTextField historyField;
	private JTextField resultField;

	// 必須の初期化
	public void setField(JTextField historyField, JTextField resultField) {
		this.historyField = historyField;
		this.resultField = resultField;
	}

	// コマンド履歴を表示
	private void showHistory() {
		// queue内のコマンドの文字表現を取り出して、つなげて、historyFieldに表示
		var historyStr = "";
		var ite = queue.iterator();
		while (ite.hasNext())
			historyStr += ite.next().getText() + ",";
		historyField.setText(historyStr);

		// Stream を使って書きかえると例えばこうです。
		// historyField.setText(String.join(",", queue.stream().map(cmd -> cmd.getText()).toList()));
	}

	// 再計算
	public void recalc() {
		// queue内のコマンドを全て取り出して再計算
		currentResult = 0;
		
		// Iterator を使った書き方
		var ite = queue.iterator();
		while (ite.hasNext())
			ite.next().exec(this);

		// 拡張for文を使った書き方
//		for (var cmd : queue) 
//			cmd.exec(this);
		 
		// Stream を使って書きかえると例えばこうです。
//		queue.stream().forEach(cmd -> cmd.exec(this));
	}

	// コマンドを追加
	public void enqueue(Command com) {
		queue.addLast(com);
		showHistory();
	}

	// 最後に追加されたコマンドを実行
	public void execLastCommand() {
		if (queue.size() == 0)
			return;
		queue.getLast().exec(calc);
		resultField.setText(String.valueOf(currentResult));
	}

	public void clear() {
		currentResult = 0;
		queue.clear();
		showHistory();
		resultField.setText("0.0");
	}
	
	public void undo() {
		if (queue.size() == 0)
			return;
		Command last = queue.removeLast();
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
