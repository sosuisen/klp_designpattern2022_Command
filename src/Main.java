import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main extends JFrame {

	public Main(String title) {
		super(title);

		var calc = Calc.getInstance();
		
		// 加算
		var plusBox = new Box(BoxLayout.X_AXIS);
		var plusField = new JTextField();
		plusField.setHorizontalAlignment(JTextField.CENTER);
		plusBox.add(plusField);
		var plusBtn = new JButton("+");
		plusBtn.setPreferredSize(new Dimension(50, 30));
		plusBtn.addActionListener(e -> {
			if (plusField.getText().matches("\\d+")) {
				calc.enqueue(new PlusCommand(Integer.parseInt(plusField.getText())));
				calc.execLastCommand();				
				plusField.setText("");
			}
		});
		plusBox.add(plusBtn);
		
		// 減算
		var minusBox = new Box(BoxLayout.X_AXIS);
		var minusField = new JTextField();
		minusField.setHorizontalAlignment(JTextField.CENTER);
		minusBox.add(minusField);
		var minusBtn = new JButton("-");
		minusBtn.setPreferredSize(new Dimension(50, 30));
		minusBtn.addActionListener(e -> {
			if (minusField.getText().matches("\\d+")) {
				calc.enqueue(new MinusCommand(Integer.parseInt(minusField.getText())));
				calc.execLastCommand();
				minusField.setText("");
			}
		});
		minusBox.add(minusBtn);

		// 乗算は基本課題
		var mulBox = new Box(BoxLayout.X_AXIS);
		var mulField = new JTextField();
		mulField.setHorizontalAlignment(JTextField.CENTER);
		mulBox.add(mulField);
		var mulBtn = new JButton("x");
		mulBtn.setPreferredSize(new Dimension(50, 30));
		mulBtn.addActionListener(e -> {
			if (mulField.getText().matches("\\d+")) {
				// calc.enqueue(new MulCommand(Integer.parseInt(mulField.getText())));
				calc.execLastCommand();
				mulField.setText("");
			}
		});
		mulBox.add(mulBtn);

		// 除算は基本課題
		
		
		// Undo
		var undoBtn = new JButton("undo");
		undoBtn.addActionListener(e -> {
			calc.undo();
		});

		var macroBtn = new JButton("macro");
		macroBtn.addActionListener(e -> {
			(new MacroCommand()).exec(calc);
		});

		// コマンド履歴
		var historyBox = new Box(BoxLayout.X_AXIS);
		var historyTitleLabel = new JLabel("履歴");
		historyBox.add(historyTitleLabel);
		var historyField = new JTextField("0");
		historyField.setPreferredSize(new Dimension(150, 30));
		historyField.setEditable(false);
		historyBox.add(historyField);

		// 計算結果
		var resultBox = new Box(BoxLayout.X_AXIS);
		var resultTitleLabel = new JLabel("結果");
		resultBox.add(resultTitleLabel);
		var resultField = new JTextField("0.0");
		resultField.setPreferredSize(new Dimension(150, 30));
		resultField.setEditable(false);
		resultBox.add(resultField);

		// クリアボタン
		var clearBtn = new JButton("clear");
		clearBtn.addActionListener(e -> {
			calc.clear();
		});

		// Calcの初期化
		calc.setField(historyField, resultField);
		
		// GUIのレイアウト
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		mainBox.add(plusBox);
		mainBox.add(minusBox);
		mainBox.add(mulBox);
		mainBox.add(undoBtn);
		mainBox.add(macroBtn);
		mainBox.add(historyBox);
		mainBox.add(resultBox);
		mainBox.add(clearBtn);
		getContentPane().add(mainBox);

		
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 200, 300);

		setVisible(true);
	}

	public static void main(String[] args) {
		new Main("Command Pattern Sample");
	}
}
