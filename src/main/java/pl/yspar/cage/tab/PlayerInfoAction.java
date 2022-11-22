package pl.yspar.cage.tab;

public enum PlayerInfoAction {
    ADD_PLAYER("ADD_PLAYER", 0, 0),
    UPDATE_DISPLAY_NAME("UPDATE_DISPLAY_NAME", 1, 3);

    private final int actionID;

    PlayerInfoAction(final String s, final int n, final int actionID) {
        this.actionID = actionID;
    }

    public int getActionID() {
        return this.actionID;
    }
}
