package game;

import fileio.ActionsInput;
import fileio.Coordinates;

/**
 * My own class for handling the actions from the input.
 */
public final class Action {
    private String command;
    private int playerIdx;
    private int handIdx;
    private int x;
    private int y;
    private int affectedRow;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;

    //action constructor, copy all the data from the input class
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
        return this.handIdx;
    }

    public void setHandIdx(final int handIdx) {
        this.handIdx = handIdx;
    }

    public String getCommand() {
        return this.command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return this.playerIdx;
    }

    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public int getX() {
        return this.x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public int getAffectedRow() {
        return this.affectedRow;
    }

    public void setAffectedRow(final int affectedRow) {
        this.affectedRow = affectedRow;
    }

    public Coordinates getCardAttacker() {
        return this.cardAttacker;
    }

    public void setCardAttacker(final Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return this.cardAttacked;
    }

    public void setCardAttacked(final Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }
}
