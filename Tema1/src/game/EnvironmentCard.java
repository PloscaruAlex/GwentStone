package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;
import java.util.Iterator;

public class EnvironmentCard extends Card {
    private String type = "environment";

    public EnvironmentCard() {}

    public EnvironmentCard(Card card) {
        this.setName(new String(card.getName()));
        this.setDescription(new String(card.getDescription()));
        this.setColors(new ArrayList<String>(card.getColors()));
        this.setMana(card.getMana());
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public ObjectNode cardOutput(ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.getMana());
        objectNode.put("description", this.getDescription());
        ArrayNode colorsArray = objectMapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsArray.add(color);
        }
        objectNode.put("colors", colorsArray);
        objectNode.put("name", this.getName());

        return objectNode;
    }

    public void useWinterfell(ArrayList<MinionCard> row) {
        for (MinionCard card : row) {
            card.setFrozen(true);
        }
    }

    public void useFirestorm(ArrayList<MinionCard> row) {
        for (MinionCard card : row) {
            card.setHealth(card.getHealth() - 1);
        }
    }

    public int useHeartHound(ObjectMapper objectMapper, ArrayNode output, Action action, ArrayList<ArrayList<MinionCard>> gameTable) {
        if (gameTable.get(3 - action.getAffectedRow()).size() == 5)  {
            output.add(ErrorOutput.cannotSteal(objectMapper, action));
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
            gameTable.get(3 - action.getAffectedRow()).add(new MinionCard(gameTable.get(action.getAffectedRow()).get(maxHealthIndex)));
            gameTable.get(action.getAffectedRow()).remove(maxHealthIndex);
            return 0;
        }
    }
}
