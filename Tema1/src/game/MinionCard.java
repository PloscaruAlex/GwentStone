package game;

import fileio.CardInput;

import java.util.ArrayList;

public class MinionCard extends Card {
    public enum Row {
        FRONT,
        BACK
    }
    private Type type = Type.MINION;
    private Row row;
    private boolean isTank = false;
    private boolean hasAttackedThisTurn = false;

    public MinionCard() {}

    public MinionCard(Card card) {
        this.setName(new String(card.getName()));
        this.setDescription(new String(card.getDescription()));
        this.setColors(new ArrayList<String>(card.getColors()));
        this.setHealth(card.getHealth());
        this.setMana(card.getMana());
        this.setAttackDamage(card.getAttackDamage());

        if (this.getName().equals("The Ripper")
                || this.getName().equals("Miraj")
                || this.getName().equals("Goliath")
                || this.getName().equals("Warden")
        ) {
            this.setRow(Row.FRONT);
        } else {
            this.setRow(Row.BACK);
        }

        if (this.getName().equals("Goliath")
                || this.getName().equals("Warden")) {
            this.setTank(true);
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    public Row getRow() {
        return this.row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    public boolean isTank() {
        return this.isTank;
    }

    public void setTank(boolean tank) {
        this.isTank = tank;
    }

    public boolean hasAttackedThisTurn() {
        return hasAttackedThisTurn;
    }

    public void setHasAttackedThisTurn(boolean hasAttackedThisTurn) {
        this.hasAttackedThisTurn = hasAttackedThisTurn;
    }

    @Override
    public void copyFromCardInput(CardInput card) {
        this.setName(new String(card.getName()));
        this.setDescription(new String(card.getDescription()));
        this.setColors(new ArrayList<String>(card.getColors()));
        this.setHealth(card.getHealth());
        this.setMana(card.getMana());
        this.setAttackDamage(card.getAttackDamage());
        if (this.getName().equals("The Ripper")
                || this.getName().equals("Miraj")
                || this.getName().equals("Goliath")
                || this.getName().equals("Warden")
        ) {
            this.setRow(Row.FRONT);
        } else {
            this.setRow(Row.BACK);
        }

        if (this.getName().equals("Goliath")
                || this.getName().equals("Warden")) {
            this.setTank(true);
        }
    }

    public void useAbility(MinionCard card) {
        int temporary;
        switch (this.getName()) {
            case "Disciple":
                card.setHealth(card.getHealth() + 2);
                break;
            case "The Ripper":
                if (card.getAttackDamage() < 2) {
                    card.setAttackDamage(0);
                } else {
                    card.setAttackDamage(card.getAttackDamage() - 2);
                }
                break;
            case "Miraj":
                temporary = card.getHealth();
                card.setHealth(this.getHealth());
                this.setHealth(temporary);
                break;
            case "The Cursed One":
                temporary = card.getHealth();
                card.setHealth(card.getAttackDamage());
                card.setAttackDamage(temporary);
                break;
        }
    }
}
