import java.util.ArrayList;

public class MacroCommand {
    // 命令の列
    private ArrayList<Command> commands = new ArrayList<>();

    // 実行
    public void exec(Calc calc) {
    	calc.enqueue(new PlusCommand(1));
		Calc.getInstance().execLastCommand();
    	calc.enqueue(new MinusCommand(2));
		Calc.getInstance().execLastCommand();
    	calc.enqueue(new PlusCommand(3));
		Calc.getInstance().execLastCommand();
    }

}
