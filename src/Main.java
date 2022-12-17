import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Main extends JFrame {
	private JTextField inputField = new JTextField();
	
	private void execCommand(String cmdStr) {
		var strValue = inputField.getText();
		inputField.setText("");
		inputField.requestFocus();
		if (!strValue.matches("\\d+")) return;
		var intValue = Integer.parseInt(strValue);
		
		Command cmd = null;
		switch (cmdStr) {
		case "PlusCommand":
			cmd = new PlusCommand(intValue);
			break;
		case "MinusCommand":
			cmd = new MinusCommand(intValue);
			break;
		case "MulCommand":
			cmd = new MulCommand(intValue);
			break;			
		default : 
			break;
		}

		/*
		 *  cmdStrに対応するクラスのインスタンスを作る方法
		 *  （別解）switch文ではなくリフレクションを使います
		 */
//		try {
//			// cmdStrという名前のクラスを取得
//			@SuppressWarnings("unchecked")
//			Class<Command> cmdClass = (Class<Command>) Class.forName(cmdStr);
//			// クラスのコンストラクタのうちintを引数に持つものを使ってインスタンス化。
//			cmd = cmdClass.getConstructor(int.class).newInstance(intValue);
//		} catch (ReflectiveOperationException e) {
//            System.out.println(cmdStr + "クラスは未実装です");
//		}
		
		
		if(cmd == null) return;		
		Calc.getInstance().enqueue(cmd);
		Calc.getInstance().execLastCommand();				
	}
		
	public Main(String title) {
		super(title);
	
		// 入力フィールド内をセンタリング
		inputField.setHorizontalAlignment(JTextField.CENTER);
		
		// 演算ボタン領域
		var calcBox = new Box(BoxLayout.X_AXIS);

		// 加算ボタン
		var plusBtn = new JButton("+");
		plusBtn.addActionListener(e -> execCommand("PlusCommand"));
		calcBox.add(plusBtn);
		
		// 減算ボタン
		var minusBtn = new JButton("-");
		minusBtn.addActionListener(e -> execCommand("MinusCommand"));
		calcBox.add(minusBtn);

		// 乗算ボタンは基本課題
		var mulBtn = new JButton("*");
		mulBtn.addActionListener(e -> execCommand("MulCommand"));
		calcBox.add(mulBtn);

		// 除算ボタンは発展課題

		
		Calc calc = Calc.getInstance();
		
		// Undoボタン
		var undoBtn = new JButton("undo");
		undoBtn.setAlignmentX(CENTER_ALIGNMENT);
		undoBtn.addActionListener(e -> calc.undo());

		// マクロボタン
		var macroBtn = new JButton("macro");
		macroBtn.setAlignmentX(CENTER_ALIGNMENT);
		macroBtn.addActionListener(e -> (new MacroCommand()).exec(calc));

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
		clearBtn.setAlignmentX(CENTER_ALIGNMENT);
		clearBtn.addActionListener(e -> {
			calc.clear();
			inputField.setText("");
		});

		// Calcの初期化
		calc.setField(historyField, resultField);
		
		// GUIのレイアウト
		Box mainBox = new Box(BoxLayout.Y_AXIS);
		mainBox.add(inputField);
		mainBox.add(calcBox);
		mainBox.add(Box.createRigidArea(new Dimension(10,10)));
		mainBox.add(undoBtn);
		mainBox.add(Box.createRigidArea(new Dimension(10,10)));
		mainBox.add(macroBtn);
		mainBox.add(historyBox);
		mainBox.add(resultBox);
		mainBox.add(clearBtn);
		getContentPane().add(mainBox);

		
		pack();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 300);

		setVisible(true);
	}

	public static void main(String[] args) {
		new Main("Command Pattern Sample");
	}
}
