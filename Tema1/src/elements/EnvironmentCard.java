package elements;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Action;
import game.ErrorOutput;

import java.util.ArrayList;

/**
 * Card of type environment, with special abilities to be used on the game table.
 */
public final class EnvironmentCard extends Card {
    private Type type = Type.ENVIRONMENT;

    public EnvironmentCard() {
    }

    public EnvironmentCard(final Card card) {
        this.setName(new String(card.getName()));
        this.setDescription(new String(card.getDescription()));
        this.setColors(new ArrayList<String>(card.getColors()));
        this.setMana(card.getMana());
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
     * function to override the output from the main card class.
     * output like the main card class, without the attackDamage, attributes.
     * @param objectMapper for creating the ObjectNode given to the output.
     * @return the node which will be added to the final output.
     */
    @Override
    public ObjectNode cardOutput(final ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.getMana());
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
     * function that implements how a card of type environment should use its ability.
     * @param output the principal ObjectNode used for output.
     * @param action the current action of the game.
     * @param gameTable current game's table.
     * @return 0 if the card was used successfully, 1 if the card was not used.
     */
    public int useEnvironmentAbility(
            final ArrayNode output,
            final Action action,
            final ArrayList<ArrayList<MinionCard>> gameTable
    ) {
        switch (this.getName()) {
            case "Winterfell":
                for (MinionCard card : gameTable.get(action.getAffectedRow())) {
                    card.setFrozen(true);
                }
                return 0;
            case "Firestorm":
                for (MinionCard card : gameTable.get(action.getAffectedRow())) {
                    card.setHealth(card.getHealth() - 1);
                }
                gameTable.get(action.getAffectedRow()).removeIf(card -> (card.getHealth() == 0));
                return 0;
            case "Heart Hound":
                if (gameTable.get(3 - action.getAffectedRow()).size() == 5)  {
                    output.add(ErrorOutput.cannotSteal(action));
                    return 1;
                } else {
                    int maxHealthIndex = 0;
                    int maxHealth = 0;
                    for (MinionCard card : gameTable.get(action.getAffectedRow())) {
                        if (card.getHealth() > maxHealth) {
                            maxHealth = card.getHealth();
                            maxHealthIndex = gameTable.get(action.getAffectedRow()).indexOf(card);
                        }
                    }
                    gameTable.get(3 - action.getAffectedRow()).add(new MinionCard(
                            gameTable.get(action.getAffectedRow()).get(maxHealthIndex)));
                    gameTable.get(action.getAffectedRow()).remove(maxHealthIndex);
                    return 0;
                }
            default:
                break;
        }
        return 0;
    }
}
