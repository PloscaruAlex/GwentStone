package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import elements.Card;
import elements.HeroCard;
import elements.MinionCard;

import java.util.ArrayList;

/**
 * Helper class to help me display the output of the game.
 */
public class OutputHelper {
    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @param currentDeck the deck of the specified player.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getPlayerDeck(final Action action,
                                           final ArrayList<Card> currentDeck) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
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

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @param heroCard the hero card of the specified player.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getPlayerHero(final Action action, final HeroCard heroCard) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("playerIdx", action.getPlayerIdx());
        node.set("output", heroCard.cardOutput(objectMapper));

        return node;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @param playerTurn the current game player's turn.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getPlayerTurn(final Action action, final int playerTurn) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("output", playerTurn);

        return node;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @param hand the array of cards representing the player's hand.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getCardsInHand(final Action action, final ArrayList<Card> hand) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
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

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @param mana the mana of the specified player.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getPlayerMana(final Action action, final int mana) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("playerIdx", action.getPlayerIdx());
        node.put("output", mana);

        return node;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @param gameTable the current game's table.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getCardsOnTable(final Action action,
                                             final ArrayList<ArrayList<MinionCard>> gameTable) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
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

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @param hand the array of cards representing the player's hand.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getEnvironmentCardsInHand(final Action action,
                                                       final ArrayList<Card> hand) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
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

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @param gameTable the current game's table.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getCardAtPosition(final Action action,
                                               final ArrayList<ArrayList<MinionCard>> gameTable) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", action.getCommand());
        node.put("x", action.getX());
        node.put("y", action.getY());
        node.set("output",
                gameTable.get(action.getX()).get(action.getY()).cardOutput(objectMapper));

        return node;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @param gameTable the current game's table.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getFrozenCardsOnTable(
            final Action action, final ArrayList<ArrayList<MinionCard>> gameTable) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
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

    /**
     * display this message if player one wins.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode playerOneWin() {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("gameEnded", "Player one killed the enemy hero.");
        return node;
    }

    /**
     * display this message if player two wins.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode playerTwoWin() {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("gameEnded", "Player two killed the enemy hero.");
        return node;
    }

    /**
     * @param gamesPlayedUntilNow number of games played, used for statistics.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getTotalGamesPlayed(final int gamesPlayedUntilNow) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getTotalGamesPlayed");
        node.put("output", gamesPlayedUntilNow);

        return node;
    }

    /**
     * @param playerOneNumberOfWins how many times player one won.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getPlayerOneWins(final int playerOneNumberOfWins) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerOneWins");
        node.put("output", playerOneNumberOfWins);

        return node;
    }

    /**
     * @param playerTwoNumberOfWins how many times player two won.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode getPlayerTwoWins(final int playerTwoNumberOfWins) {
        /*
        make new object here so i don't have to pass it by
        parameter and it is collected by the garbage collector
        */
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();
        node.put("command", "getPlayerTwoWins");
        node.put("output", playerTwoNumberOfWins);

        return node;
    }
}
