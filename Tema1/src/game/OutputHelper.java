package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class OutputHelper {
    static public ObjectNode getPlayerDeck(Action action, ArrayList<Card> currentDeck) {
        //make new object here so i dont have to pass it by parameter and it is collected by the garbage collector
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("playerIdx", action.getPlayerIdx());
        ArrayNode deck = objectMapper.createArrayNode();
        for (Card card : currentDeck) {
            deck.add(card.cardOutput(objectMapper));
        }
        node.set("output", deck);

        return node;
    }

    static public ObjectNode getPlayerHero(Action action, HeroCard heroCard) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("playerIdx", action.getPlayerIdx());
        node.set("output", heroCard.cardOutput(objectMapper));

        return node;
    }

    static public ObjectNode getPlayerTurn(Action action, int playerTurn) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("output", playerTurn);

        return node;
    }

    static public ObjectNode getCardsInHand(Action action, ArrayList<Card> hand) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("playerIdx", action.getPlayerIdx());
        ArrayNode cardsInHand = objectMapper.createArrayNode();
        for (Card card : hand) {
            cardsInHand.add(card.cardOutput(objectMapper));
        }
        node.set("output", cardsInHand);

        return node;
    }

    static public ObjectNode getPlayerMana(Action action, int mana) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("playerIdx", action.getPlayerIdx());
        node.put("output", mana);

        return node;
    }

    static public ObjectNode getCardsOnTable(Action action, ArrayList<ArrayList<MinionCard>> gameTable) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        ArrayNode table = objectMapper.createArrayNode();
        node.put("command", action.getCommand());
        ArrayList<ArrayNode> rows = new ArrayList<ArrayNode>();
        for (int counter = 0; counter < 4; counter++) {
            rows.add(objectMapper.createArrayNode());
            for (Card card : gameTable.get(counter)) {
                rows.get(counter).add(card.cardOutput(objectMapper));
            }
            table.add(rows.get(counter));
        }
        node.set("output", table);

        return node;
    }

    static public ObjectNode getEnvironmentCardsInHand(Action action, ArrayList<Card> hand) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("playerIdx", action.getPlayerIdx());
        ArrayNode environmentCards = objectMapper.createArrayNode();
        for (Card card : hand) {
            if (card.getType() == Card.Type.ENVIRONMENT) {
                environmentCards.add(card.cardOutput(objectMapper));
            }
        }
        node.set("output", environmentCards);

        return node;
    }

    static public ObjectNode getCardAtPosition(Action action, ArrayList<ArrayList<MinionCard>> gameTable) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("x", action.getX());
        node.put("y", action.getY());
        node.set("output", gameTable.get(action.getX()).get(action.getY()).cardOutput(objectMapper));

        return node;
    }

    static public ObjectNode getFrozenCardsOnTable(Action action, ArrayList<ArrayList<MinionCard>> gameTable) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        ArrayNode frozenCards = objectMapper.createArrayNode();
        node.put("command", action.getCommand());
        for (int counter = 0; counter < 4; counter++) {
            for (Card card : gameTable.get(counter)) {
                if (card.isFrozen()) {
                    frozenCards.add(card.cardOutput(objectMapper));
                }
            }
        }
        node.set("output", frozenCards);

        return node;
    }

    static public ObjectNode playerOneWin() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("gameEnded", "Player one killed the enemy hero.");
        return node;
    }

    static public ObjectNode playerTwoWin() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("gameEnded", "Player two killed the enemy hero.");
        return node;
    }
}
