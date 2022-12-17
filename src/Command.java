public interface Command {
    public abstract void exec(Calc calc);
    public abstract void undo(Calc calc);
    public abstract String getText(); 
}
