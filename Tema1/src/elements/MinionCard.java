package elements;

import fileio.CardInput;

import java.util.ArrayList;

/**
 * Card of type minion, which is placed on the game table.
 */
public final class MinionCard extends Card {
    //a place to keep information about the row that a specific minion should be placed on
    public enum Row {
        FRONT,
        BACK
    }
    private Type type = Type.MINION;
    private Row row;
    private boolean isTank = false;
    private boolean hasAttackedThisTurn = false;

    public MinionCard() {
    }

    public MinionCard(final Card card) {
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
        return this.type;
    }

    @Override
    public void setType(final Type type) {
        this.type = type;
    }

    public Row getRow() {
        return this.row;
    }

    public void setRow(final Row row) {
        this.row = row;
    }

    public boolean isTank() {
        return this.isTank;
    }

    public void setTank(final boolean tank) {
        this.isTank = tank;
    }

    /**
     * getter for hasAttackedThisTurn member, only it is boolean, so the name is changed
     * from a traditional getter.
     * @return true if this card has already attacked this turn, false otherwise.
     */
    public boolean hasAttackedThisTurn() {
        return this.hasAttackedThisTurn;
    }

    public void setHasAttackedThisTurn(final boolean hasAttackedThisTurn) {
        this.hasAttackedThisTurn = hasAttackedThisTurn;
    }

    /**
     * copy all the data about this card from the card given as input.
     * @param card the card given as input.
     */
    @Override
    public void copyFromCardInput(final CardInput card) {
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

    /**
     * function that implements how a card of type minion should use its ability.
     * @param card the card affected by this card's ability.
     */
    public void useAbility(final MinionCard card) {
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
            default:
                break;
        }
    }
}
