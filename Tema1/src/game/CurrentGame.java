package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;
import fileio.Input;
import elements.Card;
import elements.EnvironmentCard;
import elements.MinionCard;
import elements.Player;

import java.util.ArrayList;

/**
 * This class contains all the functionality for the current game,
 * displaying, playing, functions, statistics. It is the most important
 * class in this project and I didn't find a way to break it down into
 * smaller classes because all the content inside here is related and
 * wouldn't function separately.
 */
public final class CurrentGame {
    private Player playerOne;
    private Player playerTwo;
    private int startingPlayer;
    private int playerTurn;
    private int shuffleSeed;
    private ArrayList<ArrayList<MinionCard>> gameTable;
    private ArrayList<Action> actions;
    private int manaForEachRound = 1;
    private int roundNumber = 1;
    private boolean isEnded = false;
    private int gamesPlayedUntilNow;

    //current game constructor, getting all the info from the input
    public CurrentGame(
            final Input inputData,
            final int gameIndex,
            final int gamesPlayedUntilNow,
            final int playerOneNumberOfWins,
            final int playerTwoNumberOfWins
    ) {
        this.setStartingPlayer(
                inputData.getGames().get(gameIndex).getStartGame().getStartingPlayer()
        );
        this.setShuffleSeed(inputData.getGames().get(gameIndex).getStartGame().getShuffleSeed());
        this.setPlayerOne(new Player());
        this.getPlayerOne().setDecks(inputData.getPlayerOneDecks().getDecks());
        this.setPlayerTwo(new Player());
        this.getPlayerTwo().setDecks(inputData.getPlayerTwoDecks().getDecks());

        this.getPlayerOne().setCurrentDeckAtIndex(
                inputData.getGames().get(gameIndex).getStartGame().getPlayerOneDeckIdx(),
                this.getShuffleSeed()
        );
        this.getPlayerTwo().setCurrentDeckAtIndex(
                inputData.getGames().get(gameIndex).getStartGame().getPlayerTwoDeckIdx(),
                this.getShuffleSeed()
        );
        this.getPlayerOne().setHeroCard(
                inputData.getGames().get(gameIndex).getStartGame().getPlayerOneHero()
        );
        this.getPlayerTwo().setHeroCard(
                inputData.getGames().get(gameIndex).getStartGame().getPlayerTwoHero()
        );

        this.setGameTable(new ArrayList<ArrayList<MinionCard>>());
        for (int counter = 0; counter < 4; counter++) {
            this.getGameTable().add(new ArrayList<MinionCard>());
        }
        this.setActions(new ArrayList<Action>());
        for (ActionsInput actionInput : inputData.getGames().get(gameIndex).getActions()) {
            this.actions.add(new Action(actionInput));
        }

        this.gamesPlayedUntilNow = gamesPlayedUntilNow;
        this.getPlayerOne().setNumberOfWins(playerOneNumberOfWins);
        this.getPlayerTwo().setNumberOfWins(playerTwoNumberOfWins);
    }

    public CurrentGame() {
    }

    public ArrayList<ArrayList<MinionCard>> getGameTable() {
        return this.gameTable;
    }

    public void setGameTable(final ArrayList<ArrayList<MinionCard>> gameTable) {
        this.gameTable = gameTable;
    }

    public int getShuffleSeed() {
        return this.shuffleSeed;
    }

    public void setShuffleSeed(final int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public int getPlayerTurn() {
        return this.playerTurn;
    }

    public void setPlayerTurn(final int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Player getPlayerOne() {
        return this.playerOne;
    }

    public void setPlayerOne(final Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return this.playerTwo;
    }

    public void setPlayerTwo(final Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public int getStartingPlayer() {
        return this.startingPlayer;
    }

    /**
     * setting the starting player and also setting the player's turn.
     * @param startingPlayer which player starts the game.
     */
    public void setStartingPlayer(final int startingPlayer) {
        this.startingPlayer = startingPlayer;
        this.playerTurn = startingPlayer;
    }

    public ArrayList<Action> getActions() {
        return this.actions;
    }

    public void setActions(final ArrayList<Action> actions) {
        this.actions = actions;
    }

    public int getManaForEachRound() {
        return this.manaForEachRound;
    }

    public void setManaForEachRound(final int manaForEachRound) {
        this.manaForEachRound = manaForEachRound;
    }

    public int getRoundNumber() {
        return this.roundNumber;
    }

    public void setRoundNumber(final int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public boolean isEnded() {
        return this.isEnded;
    }

    public void setEnded(final boolean ended) {
        this.isEnded = ended;
    }

    public int getGamesPlayedUntilNow() {
        return this.gamesPlayedUntilNow;
    }

    public void setGamesPlayedUntilNow(final int gamesPlayedUntilNow) {
        this.gamesPlayedUntilNow = gamesPlayedUntilNow;
    }

    /**
     * Function that places a card on the game table.
     * Implemented here because it is related to the current game's table and
     * the current player.
     * @param output the principal ObjectNode used for output.
     * @param action the current action of the game.
     * @param player the player that places the card.
     */
    private void placeCard(final ArrayNode output, final Action action, final Player player) {
        Card cardToBePlaced = player.getHand().get(action.getHandIdx());
        if (cardToBePlaced.getType() == Card.Type.ENVIRONMENT) {
            output.add(ErrorOutput.environmentCardPlace(action));
            return;
        }

        //cast the card to minion type
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

            if (minionToBePlaced.isTank()) {
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

    /**
     * Function that uses an environment card on the game table.
     * Implemented here because it is related to the current game's table and
     * the attacked player.
     * @param output the principal ObjectNode used for output.
     * @param action the current action of the game.
     * @param player the player that places the card.
     */
    private void useEnvironmentCard(
            final ArrayNode output,
            final Action action,
            final Player player
    ) {
        Card cardToBeUsed = player.getHand().get(action.getHandIdx());
        if (cardToBeUsed.getType() == Card.Type.MINION) {
            output.add(ErrorOutput.cardNotEnvironment(action));
            return;
        }

        //cast the card to environment type
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

        int isUsed = environmentToBeUsed.useEnvironmentAbility(
                output,
                action,
                this.getGameTable()
        );
        if (isUsed == 0) {
            player.setMana(player.getMana() - environmentToBeUsed.getMana());
            player.getHand().remove(action.getHandIdx());
        }
    }

    /**
     * Function that ends this player turn, unfreezes all the cards and resets card attacks.
     */
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
            this.getPlayerOne().getHeroCard().setHasAttackedThisTurn(false);
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
            this.getPlayerTwo().getHeroCard().setHasAttackedThisTurn(false);
        }
    }

    /**
     * Function that uses a card's attack on another card.
     * Implemented here because it is related to the current game's table and
     * both players.
     * @param output the principal ObjectNode used for output.
     * @param action the current action of the game.
     * @param attackedPlayer the player which is attacked.
     */
    private void cardUsesAttack(
            final ArrayNode output,
            final Action action,
            final Player attackedPlayer
    ) {
        int frontRow = this.getPlayerTurn() == 1 ? 2 : 1;
        int backRow = this.getPlayerTurn() == 1 ? 3 : 0;

        MinionCard cardAttacked = this.getGameTable()
                .get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY());
        MinionCard cardAttacker = this.getGameTable()
                .get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY());

        if (action.getCardAttacked().getX() == frontRow
                || action.getCardAttacked().getX() == backRow) {
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

    /**
     * Function that uses a card's ability on another card.
     * Implemented here because it is related to the current game's table and
     * both players.
     * @param output the principal ObjectNode used for output.
     * @param action the current action of the game.
     * @param attackedPlayer the player which is attacked.
     */
    private void cardUsesAbility(
            final ArrayNode output,
            final Action action,
            final Player attackedPlayer
    ) {
        int frontRow = this.getPlayerTurn() == 1 ? 2 : 1;
        int backRow = this.getPlayerTurn() == 1 ? 3 : 0;

        MinionCard cardAttacked = this.getGameTable()
                .get(action.getCardAttacked().getX()).get(action.getCardAttacked().getY());
        MinionCard cardAttacker = this.getGameTable()
                .get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY());

        if (cardAttacker.isFrozen()) {
            output.add(ErrorOutput.cardIsFrozen(action));
            return;
        }

        if (cardAttacker.hasAttackedThisTurn()) {
            output.add(ErrorOutput.cardAlreadyAttacked(action));
            return;
        }

        if (cardAttacker.getName().equals("Disciple")) {
            if (action.getCardAttacked().getX() != frontRow
                    && action.getCardAttacked().getX() != backRow) {
                output.add(ErrorOutput.attackedEnemyCard(action));
                return;
            }
        }

        if (!(cardAttacker.getName().equals("Disciple"))) {
            if (action.getCardAttacked().getX() == frontRow
                    || action.getCardAttacked().getX() == backRow) {
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

    /**
     * Function that uses a card's attack on the other player's hero.
     * Implemented here because it is related to the current game's table and
     * the attacked player.
     * @param output the principal ObjectNode used for output.
     * @param action the current action of the game.
     * @param attackedPlayer the player which is attacked.
     */
    private void useAttackHero(
            final ArrayNode output,
            final Action action,
            final Player attackedPlayer
    ) {
        MinionCard cardAttacker = this.getGameTable()
                .get(action.getCardAttacker().getX()).get(action.getCardAttacker().getY());

        if (cardAttacker.isFrozen()) {
            output.add(ErrorOutput.attackHeroCardIsFrozen(action));
            return;
        }

        if (cardAttacker.hasAttackedThisTurn()) {
            output.add(ErrorOutput.attackHeroCardAlreadyAttacked(action));
            return;
        }

        if (attackedPlayer.hasTankOnTable()) {
            output.add(ErrorOutput.attackHeroCardNotTank(action));
            return;
        }

        attackedPlayer.getHeroCard().setHealth(
                attackedPlayer.getHeroCard().getHealth() - cardAttacker.getAttackDamage());
        cardAttacker.setHasAttackedThisTurn(true);
        if (attackedPlayer.getHeroCard().getHealth() <= 0) {
            this.setEnded(true);
            if (this.getPlayerTurn() == 1) {
                output.add(OutputHelper.playerOneWin());
                this.getPlayerOne().setNumberOfWins(this.getPlayerOne().getNumberOfWins() + 1);
                this.setGamesPlayedUntilNow(this.getGamesPlayedUntilNow() + 1);
            } else {
                output.add(OutputHelper.playerTwoWin());
                this.getPlayerTwo().setNumberOfWins(this.getPlayerTwo().getNumberOfWins() + 1);
                this.setGamesPlayedUntilNow(this.getGamesPlayedUntilNow() + 1);
            }
        }
    }

    /**
     * Function that uses a hero's ability on the game table.
     * Implemented here because it is related to the current game's table and
     * the attacked player.
     * @param output the principal ObjectNode used for output.
     * @param action the current action of the game.
     * @param attackingPlayer the player which uses his hero's ability on the other player.
     */
    private void useHeroAbility(
            final ArrayNode output,
            final Action action,
            final Player attackingPlayer
    ) {
        int frontRow = this.getPlayerTurn() == 1 ? 2 : 1;
        int backRow = this.getPlayerTurn() == 1 ? 3 : 0;

        if (attackingPlayer.getMana() < attackingPlayer.getHeroCard().getMana()) {
            output.add(ErrorOutput.heroAbilityNotEnoughMana(action));
            return;
        }

        if (attackingPlayer.getHeroCard().hasAttackedThisTurn()) {
            output.add(ErrorOutput.heroAbilityAlreadyAttacked(action));
            return;
        }

        if (attackingPlayer.getHeroCard().getName().equals("Lord Royce")
                || attackingPlayer.getHeroCard().getName().equals("Empress Thorina")) {
            if (action.getAffectedRow() == frontRow || action.getAffectedRow() == backRow) {
                output.add(ErrorOutput.heroAbilityNotEnemyRow(action));
                return;
            }
        }

        if (attackingPlayer.getHeroCard().getName().equals("General Kocioraw")
                || attackingPlayer.getHeroCard().getName().equals("King Mudface")) {
            if (action.getAffectedRow() != frontRow && action.getAffectedRow() != backRow) {
                output.add(ErrorOutput.heroAbilityNotOwnRow(action));
                return;
            }
        }

        attackingPlayer.getHeroCard().useAbility(
                this.getGameTable().get(action.getAffectedRow()));
        attackingPlayer.getHeroCard().setHasAttackedThisTurn(true);
        attackingPlayer.setMana(
                attackingPlayer.getMana() - attackingPlayer.getHeroCard().getMana());
    }

    /**
     * The brain of the project, the parsing and executing commands, output
     * and most of the functionality.
     * @param output the principal ObjectNode used for output.
     * @param action the current action of the game.
     */
    private void executeCommand(final ArrayNode output, final Action action) {
        switch (action.getCommand()) {
            case "getPlayerDeck":
                if (action.getPlayerIdx() == 1) {
                    output.add(OutputHelper.getPlayerDeck(
                            action, this.getPlayerOne().getCurrentDeck()));
                } else {
                    output.add(OutputHelper.getPlayerDeck(
                            action, this.getPlayerTwo().getCurrentDeck()));
                }
                break;
            case "getPlayerHero":
                if (action.getPlayerIdx() == 1) {
                    output.add(OutputHelper.getPlayerHero(
                            action, this.getPlayerOne().getHeroCard()));
                } else {
                    output.add(OutputHelper.getPlayerHero(
                            action, this.getPlayerTwo().getHeroCard()));
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
                    output.add(OutputHelper.getCardsInHand(
                            action, this.getPlayerOne().getHand()));
                } else {
                    output.add(OutputHelper.getCardsInHand(
                            action, this.getPlayerTwo().getHand()));
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
                    output.add(OutputHelper.getEnvironmentCardsInHand(
                            action, this.getPlayerOne().getHand()));
                } else {
                    output.add(OutputHelper.getEnvironmentCardsInHand(
                            action, this.getPlayerTwo().getHand()));
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
            case "useAttackHero":
                if (this.getPlayerTurn() == 1) {
                    this.useAttackHero(output, action, this.getPlayerTwo());
                } else {
                    this.useAttackHero(output, action, this.getPlayerOne());
                }
                break;
            case "useHeroAbility":
                if (this.getPlayerTurn() == 1) {
                    this.useHeroAbility(output, action, this.getPlayerOne());
                } else {
                    this.useHeroAbility(output, action, this.getPlayerTwo());
                }
                break;
            case "getTotalGamesPlayed":
                output.add(OutputHelper.getTotalGamesPlayed(this.getGamesPlayedUntilNow()));
                break;
            case "getPlayerOneWins":
                output.add(OutputHelper.getPlayerOneWins(this.getPlayerOne().getNumberOfWins()));
                break;
            case "getPlayerTwoWins":
                output.add(OutputHelper.getPlayerTwoWins(this.getPlayerTwo().getNumberOfWins()));
                break;
            default:
                break;
        }
    }

    /**
     * Function to determine if the round is over.
     * If it is, give each player mana and one more card in hand.
     */
    private void nextRound() {
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
                if (this.getPlayerOne().getCurrentDeck().get(0).getType()
                        == Card.Type.ENVIRONMENT) {
                    this.getPlayerOne().getHand().add(
                            new EnvironmentCard(this.getPlayerOne().getCurrentDeck().get(0)));
                    this.getPlayerOne().getCurrentDeck().remove(0);
                } else {
                    this.getPlayerOne().getHand().add(
                            new MinionCard(this.getPlayerOne().getCurrentDeck().get(0)));
                    this.getPlayerOne().getCurrentDeck().remove(0);
                }
            }
            if (this.getPlayerTwo().getCurrentDeck().size() != 0) {
                if (this.getPlayerTwo().getCurrentDeck().get(0).getType()
                        == Card.Type.ENVIRONMENT) {
                    this.getPlayerTwo().getHand().add(
                            new EnvironmentCard(this.getPlayerTwo().getCurrentDeck().get(0)));
                    this.getPlayerTwo().getCurrentDeck().remove(0);
                } else {
                    this.getPlayerTwo().getHand().add(
                            new MinionCard(this.getPlayerTwo().getCurrentDeck().get(0)));
                    this.getPlayerTwo().getCurrentDeck().remove(0);
                }
            }
        }
    }

    /**
     * Function that is the entry point for all the other ones, this one starts
     * the current game.
     * @param output the principal ObjectNode used for output.
     */
    public void startCurrentGame(final ArrayNode output) {
        for (Action action : this.getActions()) {
            this.executeCommand(output, action);
            if (!(this.isEnded())) {
                this.nextRound();
            }
        }
    }
}
