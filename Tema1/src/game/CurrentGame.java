package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class CurrentGame {
    private Player player1;
    private Player player2;
    private int startingPlayer;
    private int playerTurn;
    private int shuffleSeed;
    private ArrayList<ArrayList<MinionCard>> gameTable;

    public ArrayList<ArrayList<MinionCard>> getGameTable() {
        return gameTable;
    }

    public void setGameTable(ArrayList<ArrayList<MinionCard>> gameTable) {
        this.gameTable = gameTable;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public int getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
        this.playerTurn = startingPlayer;
    }

    public ObjectNode playerTurnOutput(ObjectMapper objectMapper, Action action) {
        ObjectNode getPlayerTurnNode = objectMapper.createObjectNode();
        getPlayerTurnNode.put("command", action.getCommand());
        getPlayerTurnNode.put("output", this.getPlayerTurn());

        return getPlayerTurnNode;
    }

    public ObjectNode tableOutput(ObjectMapper objectMapper, Action action) {
        ObjectNode getCardsOnTableNode = objectMapper.createObjectNode();
        ArrayNode getCardsOnTableArray = objectMapper.createArrayNode();
        getCardsOnTableNode.put("command", action.getCommand());
        ArrayList<ArrayNode> table = new ArrayList<ArrayNode>();
        for (int counter = 0; counter < 4; counter++) {
            table.add(objectMapper.createArrayNode());
            for (Card card : this.getGameTable().get(counter)) {
                table.get(counter).add(card.cardOutput(objectMapper));
            }
            getCardsOnTableArray.add(table.get(counter));
        }
        getCardsOnTableNode.put("output", getCardsOnTableArray);

        return getCardsOnTableNode;
    }

    public ObjectNode cardAtPositionOutput(ObjectMapper objectMapper, Action action) {
        ObjectNode cardAtPositionNode = objectMapper.createObjectNode();
        cardAtPositionNode.put("command", action.getCommand());
        cardAtPositionNode.put("x", action.getX());
        cardAtPositionNode.put("y", action.getY());
        cardAtPositionNode.put("output", this.getGameTable().get(action.getX()).get(action.getY()).cardOutput(objectMapper));

        return cardAtPositionNode;
    }

    public ObjectNode frozenCardsOnTableOutput(ObjectMapper objectMapper, Action action) {
        ObjectNode frozenCardsNode = objectMapper.createObjectNode();
        ArrayNode frozenCardsArray = objectMapper.createArrayNode();
        frozenCardsNode.put("command", action.getCommand());
        for (int counter = 0; counter < 4; counter++) {
            for (Card card : this.getGameTable().get(counter)) {
                if (card.isFrozen()) {
                    frozenCardsArray.add(card.cardOutput(objectMapper));
                }
            }
        }
        frozenCardsNode.put("output", frozenCardsArray);

        return frozenCardsNode;
    }
}
