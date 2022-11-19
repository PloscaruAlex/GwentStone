package game;

import fileio.ActionsInput;
import fileio.Coordinates;

public final class Action {
    private String command;
    private int playerIdx;
    private int handIdx;
    private int x;
    private int y;
    private int affectedRow;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;

    public Action(final ActionsInput action) {
        this.command = new String(action.getCommand());
        this.playerIdx = action.getPlayerIdx();
        this.handIdx = action.getHandIdx();
        this.x = action.getX();
        this.y = action.getY();
        this.affectedRow = action.getAffectedRow();
        this.cardAttacked = action.getCardAttacked();
        this.cardAttacker = action.getCardAttacker();
    }

    public int getHandIdx() {
        return handIdx;
    }

    public void setHandIdx(final int handIdx) {
        this.handIdx = handIdx;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(final int affectedRow) {
        this.affectedRow = affectedRow;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(final Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public void setCardAttacked(final Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }
}
