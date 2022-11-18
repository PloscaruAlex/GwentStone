package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.Input;

import java.util.ArrayList;

public class CurrentGame {
    private Player playerOne;
    private Player playerTwo;
    private int startingPlayer;
    private int playerTurn;
    private int shuffleSeed;
    private ArrayList<ArrayList<MinionCard>> gameTable;
    private ArrayList<Action> actions;
    private int manaForEachRound = 1;
    private int roundNumber = 1;

    public CurrentGame(Input inputData) {
        this.setStartingPlayer(inputData.getGames().get(0).getStartGame().getStartingPlayer());
        this.setShuffleSeed(inputData.getGames().get(0).getStartGame().getShuffleSeed());
        this.setPlayerOne(new Player());
        this.getPlayerOne().setDecks(inputData.getPlayerOneDecks().getDecks());
        this.setPlayerTwo(new Player());
        this.getPlayerTwo().setDecks(inputData.getPlayerTwoDecks().getDecks());
        this.getPlayerOne().setCurrentDeckAtIndex(inputData.getGames().get(0).getStartGame().getPlayerOneDeckIdx(), this.getShuffleSeed());
        this.getPlayerTwo().setCurrentDeckAtIndex(inputData.getGames().get(0).getStartGame().getPlayerTwoDeckIdx(), this.getShuffleSeed());
        this.getPlayerOne().setHeroCard(inputData.getGames().get(0).getStartGame().getPlayerOneHero());
        this.getPlayerTwo().setHeroCard(inputData.getGames().get(0).getStartGame().getPlayerTwoHero());
        this.setGameTable(new ArrayList<ArrayList<MinionCard>>());
        for (int counter = 0; counter < 4; counter++) {
            this.getGameTable().add(new ArrayList<MinionCard>());
        }
        this.setActions(new ArrayList<Action>());
        for (ActionsInput actionInput : inputData.getGames().get(0).getActions()) {
            this.actions.add(new Action(actionInput));
        }
    }

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

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
        this.playerTurn = startingPlayer;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public int getManaForEachRound() {
        return manaForEachRound;
    }

    public void setManaForEachRound(int manaForEachRound) {
        this.manaForEachRound = manaForEachRound;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    private void placeCard(ArrayNode output, Action action, Player player) {
        Card cardToBePlaced = player.getHand().get(action.getHandIdx());
        if (cardToBePlaced.getType() == Card.Type.ENVIRONMENT) {
            output.add(ErrorOutput.environmentCardPlace(action));
            return;
        }

        MinionCard minionToBePlaced = (MinionCard) cardToBePlaced;
        if (minionToBePlaced.getMana() > player.getMana()) {
            output.add(ErrorOutput.notEnoughMana(action));
            return;
        }

        if (minionToBePlaced.getRow() == MinionCard.Row.FRONT) {
            int rowToAddTo = this.getPlayerTurn() == 1 ? 2 : 1;
            if (this.getGameTable().get(rowToAddTo).size() == 5) {
                output.add(ErrorOutput.rowIsFull(action));
                return;
            }

            if(minionToBePlaced.isTank()) {
                player.setHasTankOnTable(true);
            }
            player.setMana(player.getMana() - minionToBePlaced.getMana());
            this.getGameTable().get(rowToAddTo).add(new MinionCard(minionToBePlaced));
            player.getHand().remove(action.getHandIdx());
        } else {
            int rowToAddTo = this.getPlayerTurn() == 1 ? 3 : 0;
            if (this.getGameTable().get(rowToAddTo).size() == 5) {
                output.add(ErrorOutput.rowIsFull(action));
                return;
            }

            player.setMana(player.getMana() - minionToBePlaced.getMana());
            this.getGameTable().get(rowToAddTo).add(new MinionCard(minionToBePlaced));
            player.getHand().remove(action.getHandIdx());
        }
    }

    private void useEnvironmentCard(ArrayNode output, Action action, Player player) {
        Card cardToBeUsed = player.getHand().get(action.getHandIdx());
        if (cardToBeUsed.getType() == Card.Type.MINION) {
            output.add(ErrorOutput.cardNotEnvironment(action));
            return;
        }
        EnvironmentCard environmentToBeUsed = (EnvironmentCard) cardToBeUsed;
        if (environmentToBeUsed.getMana() > player.getMana()) {
            output.add(ErrorOutput.notEnoughManaEnvironment(action));
            return;
        }

        int frontRow = this.getPlayerTurn() == 1 ? 2 : 1;
        int backRow = this.getPlayerTurn() == 1 ? 3 : 0;
        if (action.getAffectedRow() == frontRow || action.getAffectedRow() == backRow) {
            output.add(ErrorOutput.notEnemyRow(action));
            return;
        }

        int isUsed = environmentToBeUsed.useEnvironmentAbility(output, action, this.getGameTable());
        if (isUsed == 0) {
            player.setMana(player.getMana() - environmentToBeUsed.getMana());
            player.getHand().remove(action.getHandIdx());
        }
    }

    private void endPlayerTurn() {
        if (this.getPlayerTurn() == 1) {
            this.getPlayerOne().setTurnEnded(true);
            this.setPlayerTurn(2);
            for (MinionCard card : this.getGameTable().get(2)) {
                card.setFrozen(false);
                card.setHasAttackedThisTurn(false);
            }
            for (MinionCard card : this.getGameTable().get(3)) {
                card.setFrozen(false);
                card.setHasAttackedThisTurn(false);
            }
        } else {
            this.getPlayerTwo().setTurnEnded(true);
            this.setPlayerTurn(1);
            for (MinionCard card : this.getGameTable().get(0)) {
                card.setFrozen(false);
                card.setHasAttackedThisTurn(false);
            }
            for (MinionCard card : this.getGameTable().get(1)) {
                card.setFrozen(false);
                card.setHasAttackedThisTurn(false);
            }
        }
    }

    private void cardUsesAttack(ArrayNode output, Action action, Player attackedPlayer) {
        int frontRow = this.getPlayerTurn() == 1 ? 2 : 1;
        int backRow = this.getPlayerTurn() == 1 ? 3 : 0;

        MinionCard cardAttacked = this.getGameTable().get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY());
        MinionCard cardAttacker = this.getGameTable().get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY());

        if (action.getCardAttacked().getX() == frontRow || action.getCardAttacked().getX() == backRow) {
            output.add(ErrorOutput.attackedOwnCard(action));
            return;
        }

        if (cardAttacker.hasAttackedThisTurn()) {
            output.add(ErrorOutput.cardAlreadyAttacked(action));
            return;
        }

        if (cardAttacker.isFrozen()) {
            output.add(ErrorOutput.cardIsFrozen(action));
            return;
        }

        if (attackedPlayer.hasTankOnTable()) {
            if (!(cardAttacked.isTank())) {
                output.add(ErrorOutput.cardNotTank(action));
                return;
            }
        }

        cardAttacked.setHealth(cardAttacked.getHealth() - cardAttacker.getAttackDamage());
        if (cardAttacked.getHealth() <= 0) {
            this.getGameTable().get(action.getCardAttacked().getX()).remove(cardAttacked);
        }

        cardAttacker.setHasAttackedThisTurn(true);
    }

    private void cardUsesAbility(ArrayNode output, Action action, Player attackedPlayer) {
        int frontRow = this.getPlayerTurn() == 1 ? 2 : 1;
        int backRow = this.getPlayerTurn() == 1 ? 3 : 0;

        MinionCard cardAttacked = this.getGameTable().get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY());
        MinionCard cardAttacker = this.getGameTable().get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY());

        if (cardAttacker.isFrozen()) {
            output.add(ErrorOutput.cardIsFrozen(action));
            return;
        }

        if (cardAttacker.hasAttackedThisTurn()) {
            output.add(ErrorOutput.cardAlreadyAttacked(action));
            return;
        }

        if (cardAttacker.getName().equals("Disciple")) {
            if (action.getCardAttacked().getX() != frontRow && action.getCardAttacked().getX() != backRow) {
                output.add(ErrorOutput.attackedEnemyCard(action));
                return;
            }
        }

        if (!(cardAttacker.getName().equals("Disciple"))) {
            if (action.getCardAttacked().getX() == frontRow || action.getCardAttacked().getX() == backRow) {
                output.add(ErrorOutput.attackedOwnCard(action));
                return;
            }

            if (attackedPlayer.hasTankOnTable()) {
                if (!(cardAttacked.isTank())) {
                    output.add(ErrorOutput.cardNotTank(action));
                    return;
                }
            }
        }

        cardAttacker.useAbility(cardAttacked);
        if (cardAttacked.getHealth() <= 0) {
            this.getGameTable().get(action.getCardAttacked().getX()).remove(cardAttacked);
        }
        cardAttacker.setHasAttackedThisTurn(true);
    }

    private void executeCommand(ArrayNode output, Action action) {
        switch (action.getCommand()) {
            case "getPlayerDeck":
                if (action.getPlayerIdx() == 1) {
                    output.add(OutputHelper.getPlayerDeck(action, this.getPlayerOne().getCurrentDeck()));
                } else {
                    output.add(OutputHelper.getPlayerDeck(action, this.getPlayerTwo().getCurrentDeck()));
                }
                break;
            case "getPlayerHero":
                if (action.getPlayerIdx() == 1) {
                    output.add(OutputHelper.getPlayerHero(action, this.getPlayerOne().getHeroCard()));
                } else {
                    output.add(OutputHelper.getPlayerHero(action, this.getPlayerTwo().getHeroCard()));
                }
                break;
            case "getPlayerTurn":
                output.add(OutputHelper.getPlayerTurn(action, this.getPlayerTurn()));
                break;
            case "endPlayerTurn":
                this.endPlayerTurn();
                break;
            case "placeCard":
                if (this.getPlayerTurn() == 1) {
                    this.placeCard(output, action, this.getPlayerOne());
                } else {
                    this.placeCard(output, action, this.getPlayerTwo());
                }
                break;
            case "getCardsInHand":
                if (action.getPlayerIdx() == 1) {
                    output.add(OutputHelper.getCardsInHand(action, this.getPlayerOne().getHand()));
                } else {
                    output.add(OutputHelper.getCardsInHand(action, this.getPlayerTwo().getHand()));
                }
                break;
            case "getPlayerMana":
                if (action.getPlayerIdx() == 1) {
                    output.add(OutputHelper.getPlayerMana(action, this.getPlayerOne().getMana()));
                } else {
                    output.add(OutputHelper.getPlayerMana(action, this.getPlayerTwo().getMana()));
                }
                break;
            case "getCardsOnTable":
                output.add(OutputHelper.getCardsOnTable(action, this.getGameTable()));
                break;
            case "getEnvironmentCardsInHand":
                if (action.getPlayerIdx() == 1) {
                    output.add(OutputHelper.getEnvironmentCardsInHand(action, this.getPlayerOne().getHand()));
                } else {
                    output.add(OutputHelper.getEnvironmentCardsInHand(action, this.getPlayerTwo().getHand()));
                }
                break;
            case "useEnvironmentCard":
                if (this.getPlayerTurn() == 1) {
                    this.useEnvironmentCard(output, action, this.getPlayerOne());
                } else {
                    this.useEnvironmentCard(output, action, this.getPlayerTwo());
                }
                break;
            case "getCardAtPosition":
                if (this.getGameTable().get(action.getX()).size() <= action.getY()) {
                    output.add(ErrorOutput.noCardAtPosition(action));
                    break;
                }
                output.add(OutputHelper.getCardAtPosition(action, this.getGameTable()));
                break;
            case "getFrozenCardsOnTable":
                output.add(OutputHelper.getFrozenCardsOnTable(action, this.getGameTable()));
                break;
            case "cardUsesAttack":
                if (this.getPlayerTurn() == 1) {
                    this.cardUsesAttack(output, action, this.getPlayerTwo());
                } else {
                    this.cardUsesAttack(output, action, this.getPlayerOne());
                }
                break;
            case "cardUsesAbility":
                if (this.getPlayerTurn() == 1) {
                    this.cardUsesAbility(output, action, this.getPlayerTwo());
                } else {
                    this.cardUsesAbility(output, action, this.getPlayerOne());
                }
                break;
        }
    }

    private void nextRound(ArrayNode output) {
        if (this.getPlayerOne().isTurnEnded() && this.getPlayerTwo().isTurnEnded()) {
            this.setRoundNumber(this.getRoundNumber() + 1);
            if (this.getManaForEachRound() < 10) {
                this.setManaForEachRound(this.getManaForEachRound() + 1);
            }
            this.getPlayerOne().setMana(this.getPlayerOne().getMana() + manaForEachRound);
            this.getPlayerTwo().setMana(this.getPlayerTwo().getMana() + manaForEachRound);
            this.getPlayerOne().setTurnEnded(false);
            this.getPlayerTwo().setTurnEnded(false);

            if (this.getPlayerOne().getCurrentDeck().size() != 0) {
                if (this.getPlayerOne().getCurrentDeck().get(0).getType() == Card.Type.ENVIRONMENT) {
                    this.getPlayerOne().getHand().add(new EnvironmentCard(this.getPlayerOne().getCurrentDeck().get(0)));
                    this.getPlayerOne().getCurrentDeck().remove(0);
                } else {
                    this.getPlayerOne().getHand().add(new MinionCard(this.getPlayerOne().getCurrentDeck().get(0)));
                    this.getPlayerOne().getCurrentDeck().remove(0);
                }
            }
            if (this.getPlayerTwo().getCurrentDeck().size() != 0) {
                if (this.getPlayerTwo().getCurrentDeck().get(0).getType() == Card.Type.ENVIRONMENT) {
                    this.getPlayerTwo().getHand().add(new EnvironmentCard(this.getPlayerTwo().getCurrentDeck().get(0)));
                    this.getPlayerTwo().getCurrentDeck().remove(0);
                } else {
                    this.getPlayerTwo().getHand().add(new MinionCard(this.getPlayerTwo().getCurrentDeck().get(0)));
                    this.getPlayerTwo().getCurrentDeck().remove(0);
                }
            }
        }
    }

    public void startCurrentGame(ArrayNode output) {
        for (Action action : this.getActions()) {
            this.executeCommand(output, action);

            this.nextRound(output);
        }
    }
}
