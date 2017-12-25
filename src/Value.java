public class Value {
    private Board state;
    private int h;

    public Value(Board state, int h) {
        this.state = state;
        this.h = h;
    }

    public Board getState() {
        return state;
    }

    public int getH() {
        return h;
    }

    public void setState(Board state) {
        this.state = state;
    }

    public void setH(int h) {
        this.h = h;
    }
}
