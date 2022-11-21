package elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

/**
 * Card of type hero, special and unique for each player.
 */
public final class HeroCard extends Card {
    private int health = 30;
    private Type type = Type.HERO;
    private boolean hasAttackedThisTurn = false;

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public void setHealth(final int health) {
        this.health = health;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public void setType(final Type type) {
        this.type = type;
    }

    /**
     * getter for hasAttackedThisTurn member, only it is boolean, so the name is changed
     * from a traditional getter.
     * @return true if this hero has used his ability this turn, false otherwise.
     */
    public boolean hasAttackedThisTurn() {
        return this.hasAttackedThisTurn;
    }

    public void setHasAttackedThisTurn(final boolean hasAttackedThisTurn) {
        this.hasAttackedThisTurn = hasAttackedThisTurn;
    }

    /**
     * function to override the output from the main card class.
     * output like the main card class, without the attackDamage attribute.
     * @param objectMapper for creating the ObjectNode given to the output.
     * @return the node which will be added to the final output.
     */
    @Override
    public ObjectNode cardOutput(final ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.getMana());
        objectNode.put("health", this.getHealth());
        objectNode.put("description", this.getDescription());
        ArrayNode colorsArray = objectMapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsArray.add(color);
        }
        objectNode.set("colors", colorsArray);
        objectNode.put("name", this.getName());

        return objectNode;
    }

    /**
     * function that specifies how a hero's ability is used.
     * @param row the row on the game table which this hero's ability will be used upon.
     */
    public void useAbility(final ArrayList<MinionCard> row) {
        switch (this.getName()) {
            case "Lord Royce":
                int maxAttackOnRow = 0;
                int maxAttackIndex = 0;

                for (MinionCard card : row) {
                    if (card.getAttackDamage() > maxAttackOnRow) {
                        maxAttackOnRow = card.getAttackDamage();
                        maxAttackIndex = row.indexOf(card);
                    }
                }

                row.get(maxAttackIndex).setFrozen(true);
                break;
            case "Empress Thorina":
                int maxHealthOnRow = 0;
                int maxHealthIndex = 0;

                for (MinionCard card : row) {
                    if (card.getHealth() > maxHealthOnRow) {
                        maxHealthOnRow = card.getHealth();
                        maxHealthIndex = row.indexOf(card);
                    }
                }

                row.remove(maxHealthIndex);
                break;
            case "King Mudface":
                for (MinionCard card : row) {
                    card.setHealth(card.getHealth() + 1);
                }
                break;
            case "General Kocioraw":
                for (MinionCard card : row) {
                    card.setAttackDamage(card.getAttackDamage() + 1);
                }
                break;
            default:
                break;
        }
    }
}
