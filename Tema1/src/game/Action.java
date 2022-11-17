package game;

import fileio.ActionsInput;

public class Action {
    private String command;
    private int playerIdx;
    private int handIdx;
    private int x;
    private int y;
    private int affectedRow;

    public Action(ActionsInput action) {
        this.command = new String(action.getCommand());
        this.playerIdx = action.getPlayerIdx();
        this.handIdx = action.getHandIdx();
        this.x = action.getX();
        this.y = action.getY();
        this.affectedRow = action.getAffectedRow();
    }

    public int getHandIdx() {
        return handIdx;
    }

    public void setHandIdx(int handIdx) {
        this.handIdx = handIdx;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(int affectedRow) {
        this.affectedRow = affectedRow;
    }
}
