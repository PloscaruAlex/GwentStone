package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class HeroCard extends Card {
    private int health = 30;
    private Type type = Type.HERO;
    private boolean hasAttackedThisTurn = false;

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    public boolean hasAttackedThisTurn() {
        return this.hasAttackedThisTurn;
    }

    public void setHasAttackedThisTurn(boolean hasAttackedThisTurn) {
        this.hasAttackedThisTurn = hasAttackedThisTurn;
    }

    @Override
    public ObjectNode cardOutput(ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.getMana());
        objectNode.put("health", this.getHealth());
        objectNode.put("description", this.getDescription());
        ArrayNode colorsArray = objectMapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsArray.add(color);
        }
        objectNode.put("colors", colorsArray);
        objectNode.put("name", this.getName());

        return objectNode;
    }

    public void useAbility(ArrayList<MinionCard> row) {
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
        }
    }
}
