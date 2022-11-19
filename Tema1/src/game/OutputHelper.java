package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import elements.Card;
import elements.HeroCard;
import elements.MinionCard;

import java.util.ArrayList;

public class OutputHelper {
    public static ObjectNode getPlayerDeck(final Action action,
                                           final ArrayList<Card> currentDeck) {
        //make new object here so i don't have to pass it by
        //parameter and it is collected by the garbage collector
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

    public static ObjectNode getPlayerHero(final Action action, final HeroCard heroCard) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("playerIdx", action.getPlayerIdx());
        node.set("output", heroCard.cardOutput(objectMapper));

        return node;
    }

    public static ObjectNode getPlayerTurn(final Action action, final int playerTurn) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("output", playerTurn);

        return node;
    }

    public static ObjectNode getCardsInHand(final Action action, final ArrayList<Card> hand) {
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

    public static ObjectNode getPlayerMana(final Action action, final int mana) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("playerIdx", action.getPlayerIdx());
        node.put("output", mana);

        return node;
    }

    public static ObjectNode getCardsOnTable(final Action action,
                                             final ArrayList<ArrayList<MinionCard>> gameTable) {
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

    public static ObjectNode getEnvironmentCardsInHand(final Action action,
                                                       final ArrayList<Card> hand) {
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

    public static ObjectNode getCardAtPosition(final Action action,
                                               final ArrayList<ArrayList<MinionCard>> gameTable) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("x", action.getX());
        node.put("y", action.getY());
        node.set("output",
                gameTable.get(action.getX()).get(action.getY()).cardOutput(objectMapper));

        return node;
    }

    public static ObjectNode getFrozenCardsOnTable(
            final Action action, final ArrayList<ArrayList<MinionCard>> gameTable) {
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

    public static ObjectNode playerOneWin() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("gameEnded", "Player one killed the enemy hero.");
        return node;
    }

    public static ObjectNode playerTwoWin() {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("gameEnded", "Player two killed the enemy hero.");
        return node;
    }

    public static ObjectNode getTotalGamesPlayed(final int gamesPlayedUntilNow) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getTotalGamesPlayed");
        node.put("output", gamesPlayedUntilNow);

        return node;
    }

    public static ObjectNode getPlayerOneWins(final int playerOneNumberOfWins) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerOneWins");
        node.put("output", playerOneNumberOfWins);

        return node;
    }

    public static ObjectNode getPlayerTwoWins(final int playerTwoNumberOfWins) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerTwoWins");
        node.put("output", playerTwoNumberOfWins);

        return node;
    }
}
