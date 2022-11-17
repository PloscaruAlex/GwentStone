package main;

import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CardInput;
import fileio.Input;

import game.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        //TODO add here the entry point to your implementation

        //creating the current game
        CurrentGame currentGame = new CurrentGame();

        currentGame.setStartingPlayer(inputData.getGames().get(0).getStartGame().getStartingPlayer());
        currentGame.setShuffleSeed(inputData.getGames().get(0).getStartGame().getShuffleSeed());

        Player playerOne = new Player();
        Player playerTwo = new Player();

        playerOne.setDecks(inputData.getPlayerOneDecks().getDecks());
        playerTwo.setDecks(inputData.getPlayerTwoDecks().getDecks());

        int deckIndexPlayerOne = inputData.getGames().get(0).getStartGame().getPlayerOneDeckIdx();
        playerOne.setCurrentDeckAtIndex(deckIndexPlayerOne, currentGame.getShuffleSeed());
        int deckIndexPlayerTwo = inputData.getGames().get(0).getStartGame().getPlayerTwoDeckIdx();
        playerTwo.setCurrentDeckAtIndex(deckIndexPlayerTwo, currentGame.getShuffleSeed());

        CardInput heroOne = inputData.getGames().get(0).getStartGame().getPlayerOneHero();
        playerOne.setHeroCard(heroOne);
        CardInput heroTwo = inputData.getGames().get(0).getStartGame().getPlayerTwoHero();
        playerTwo.setHeroCard(heroTwo);

        currentGame.setPlayer1(playerOne);
        currentGame.setPlayer2(playerTwo);
        currentGame.setGameTable(new ArrayList<ArrayList<MinionCard>>());
        for (int counter = 0; counter < 4; counter++) {
            currentGame.getGameTable().add(new ArrayList<MinionCard>());
        }

        int round = 1;
        int manaForEachRound = 1;
        boolean isPlayerOneTurnEnded = false;
        boolean isPlayerTwoTurnEnded = false;
        for (int index = 0; index < inputData.getGames().get(0).getActions().size(); index++) {
            Action action = new Action(inputData.getGames().get(0).getActions().get(index));

            switch (action.getCommand()) {
                case "getPlayerDeck":
                    if (action.getPlayerIdx() == 1) {
                        output.add(playerOne.deckOutput(objectMapper, action));
                    } else {
                        output.add(playerTwo.deckOutput(objectMapper, action));
                    }
                    break;
                case "getPlayerHero":
                    if (action.getPlayerIdx() == 1) {
                        output.add(playerOne.heroOutput(objectMapper, action));
                    } else {
                        output.add(playerTwo.heroOutput(objectMapper, action));
                    }
                    break;
                case "getPlayerTurn":
                    output.add(currentGame.playerTurnOutput(objectMapper, action));
                    break;
                case "endPlayerTurn":
                    if (currentGame.getPlayerTurn() == 1) {
                        isPlayerOneTurnEnded = true;
                        currentGame.setPlayerTurn(2);
                        for (Card card : currentGame.getGameTable().get(2)) {
                            card.setFrozen(false);
                        }
                        for (Card card : currentGame.getGameTable().get(3)) {
                            card.setFrozen(false);
                        }
                    } else {
                        isPlayerTwoTurnEnded = true;
                        currentGame.setPlayerTurn(1);
                        for (Card card : currentGame.getGameTable().get(0)) {
                            card.setFrozen(false);
                        }
                        for (Card card : currentGame.getGameTable().get(1)) {
                            card.setFrozen(false);
                        }
                    }
                    break;
                case "placeCard":
                    if (currentGame.getPlayerTurn() == 1) {
                        Card cardToBePlaced = playerOne.getHand().get(action.getHandIdx());
                        if (cardToBePlaced.getType().equals("environment")) {
                            output.add(ErrorOutput.environmentCardPlace(objectMapper, action));
                            break;
                        }
                        MinionCard minionToBePlaced = (MinionCard) cardToBePlaced;
                        if (minionToBePlaced.getMana() > playerOne.getMana()) {
                            output.add(ErrorOutput.notEnoughMana(objectMapper, action));
                            break;
                        }
                        if (minionToBePlaced.getRow().equals("front")) {
                            if (currentGame.getGameTable().get(2).size() == 5) {
                                output.add(ErrorOutput.rowIsFull(objectMapper, action));
                                break;
                            }
                            playerOne.setMana(playerOne.getMana() - minionToBePlaced.getMana());
                            currentGame.getGameTable().get(2).add(new MinionCard(minionToBePlaced));
                            playerOne.getHand().remove(action.getHandIdx());
                        } else {
                            if (currentGame.getGameTable().get(3).size() == 5) {
                                output.add(ErrorOutput.rowIsFull(objectMapper, action));
                                break;
                            }
                            playerOne.setMana(playerOne.getMana() - minionToBePlaced.getMana());
                            currentGame.getGameTable().get(3).add(new MinionCard(minionToBePlaced));
                            playerOne.getHand().remove(action.getHandIdx());
                        }
                    } else {
                        Card cardToBePlaced = playerTwo.getHand().get(action.getHandIdx());
                        if (cardToBePlaced.getType().equals("environment")) {
                            output.add(ErrorOutput.environmentCardPlace(objectMapper, action));
                            break;
                        }
                        MinionCard minionToBePlaced = (MinionCard) cardToBePlaced;
                        if (minionToBePlaced.getMana() > playerTwo.getMana()) {
                            output.add(ErrorOutput.notEnoughMana(objectMapper, action));
                            break;
                        }
                        if (minionToBePlaced.getRow().equals("front")) {
                            if (currentGame.getGameTable().get(1).size() == 5) {
                                output.add(ErrorOutput.rowIsFull(objectMapper, action));
                                break;
                            }
                            playerTwo.setMana(playerTwo.getMana() - minionToBePlaced.getMana());
                            currentGame.getGameTable().get(1).add(new MinionCard(minionToBePlaced));
                            playerTwo.getHand().remove(action.getHandIdx());
                        } else {
                            if (currentGame.getGameTable().get(0).size() == 5) {
                                output.add(ErrorOutput.rowIsFull(objectMapper, action));
                                break;
                            }
                            playerTwo.setMana(playerTwo.getMana() - minionToBePlaced.getMana());
                            currentGame.getGameTable().get(0).add(new MinionCard(minionToBePlaced));
                            playerTwo.getHand().remove(action.getHandIdx());
                        }
                    }
                    break;
                case "getCardsInHand":
                    if (action.getPlayerIdx() == 1) {
                        output.add(playerOne.handOutput(objectMapper, action));
                    } else {
                        output.add(playerTwo.handOutput(objectMapper, action));
                    }
                    break;
                case "getPlayerMana":
                    if (action.getPlayerIdx() == 1) {
                        output.add(playerOne.manaOutput(objectMapper, action));
                    } else {
                        output.add(playerTwo.manaOutput(objectMapper, action));
                    }
                    break;
                case "getCardsOnTable":
                    output.add(currentGame.tableOutput(objectMapper, action));
                    break;
                case "getEnvironmentCardsInHand":
                    if (action.getPlayerIdx() == 1) {
                        output.add(playerOne.environmentInHandOutput(objectMapper, action));
                    } else {
                        output.add(playerTwo.environmentInHandOutput(objectMapper, action));
                    }
                    break;
                case "useEnvironmentCard":
                    if (currentGame.getPlayerTurn() == 1) {
                        Card cardToBeUsed = playerOne.getHand().get(action.getHandIdx());
                        if (cardToBeUsed.getType().equals("minion")) {
                            output.add(ErrorOutput.cardNotEnvironment(objectMapper, action));
                            break;
                        }
                        EnvironmentCard environmentToBeUsed = (EnvironmentCard) cardToBeUsed;
                        if (environmentToBeUsed.getMana() > playerOne.getMana()) {
                            output.add(ErrorOutput.notEnoughManaEnvironment(objectMapper, action));
                            break;
                        }
                        if (action.getAffectedRow() == 2 || action.getAffectedRow() == 3) {
                            output.add(ErrorOutput.notEnemyRow(objectMapper, action));
                            break;
                        }
                        if (environmentToBeUsed.getName().equals("Winterfell")) {
                            environmentToBeUsed.useWinterfell(currentGame.getGameTable().get(action.getAffectedRow()));
                            playerOne.setMana(playerOne.getMana() - environmentToBeUsed.getMana());
                            playerOne.getHand().remove(action.getHandIdx());
                        } else if (environmentToBeUsed.getName().equals("Firestorm")) {
                            environmentToBeUsed.useFirestorm(currentGame.getGameTable().get(action.getAffectedRow()));
                            playerOne.setMana(playerOne.getMana() - environmentToBeUsed.getMana());
                            playerOne.getHand().remove(action.getHandIdx());
                            currentGame.getGameTable().get(action.getAffectedRow()).removeIf(card -> (card.getHealth() == 0));
                        } else if (environmentToBeUsed.getName().equals("Heart Hound")) {
                            int isUsed = environmentToBeUsed.useHeartHound(objectMapper, output, action, currentGame.getGameTable());
                            if (isUsed == 0) {
                                playerOne.setMana(playerOne.getMana() - environmentToBeUsed.getMana());
                                playerOne.getHand().remove(action.getHandIdx());
                            }
                        }
                    } else {
                        Card cardToBeUsed = playerTwo.getHand().get(action.getHandIdx());
                        if (cardToBeUsed.getType().equals("minion")) {
                            output.add(ErrorOutput.cardNotEnvironment(objectMapper, action));
                            break;
                        }
                        EnvironmentCard environmentToBeUsed = (EnvironmentCard) cardToBeUsed;
                        if (environmentToBeUsed.getMana() > playerTwo.getMana()) {
                            output.add(ErrorOutput.notEnoughManaEnvironment(objectMapper, action));
                            break;
                        }
                        if (action.getAffectedRow() == 0 || action.getAffectedRow() == 1) {
                            output.add(ErrorOutput.notEnemyRow(objectMapper, action));
                            break;
                        }
                        if (environmentToBeUsed.getName().equals("Winterfell")) {
                            environmentToBeUsed.useWinterfell(currentGame.getGameTable().get(action.getAffectedRow()));
                            playerTwo.setMana(playerTwo.getMana() - environmentToBeUsed.getMana());
                            playerTwo.getHand().remove(action.getHandIdx());
                        } else if (environmentToBeUsed.getName().equals("Firestorm")) {
                            environmentToBeUsed.useFirestorm(currentGame.getGameTable().get(action.getAffectedRow()));
                            playerTwo.setMana(playerTwo.getMana() - environmentToBeUsed.getMana());
                            playerTwo.getHand().remove(action.getHandIdx());
                            currentGame.getGameTable().get(action.getAffectedRow()).removeIf(card -> (card.getHealth() == 0));
                        } else if (environmentToBeUsed.getName().equals("Heart Hound")) {
                            int isUsed = environmentToBeUsed.useHeartHound(objectMapper, output, action, currentGame.getGameTable());
                            if (isUsed == 0) {
                                playerTwo.setMana(playerTwo.getMana() - environmentToBeUsed.getMana());
                                playerTwo.getHand().remove(action.getHandIdx());
                            }
                        }
                    }
                    break;
                case "getCardAtPosition":
                    if (currentGame.getGameTable().get(action.getX()).size() <= action.getY()) {
                        output.add(ErrorOutput.noCardAtPosition(objectMapper, action));
                        break;
                    }
                    output.add(currentGame.cardAtPositionOutput(objectMapper, action));
                    break;
                case "getFrozenCardsOnTable":
                    output.add(currentGame.frozenCardsOnTableOutput(objectMapper, action));
                    break;
            }

            if (isPlayerOneTurnEnded && isPlayerTwoTurnEnded) {
                round++;
                if (manaForEachRound < 10) {
                    manaForEachRound++;
                }
                playerOne.setMana(playerOne.getMana() + manaForEachRound);
                playerTwo.setMana(playerTwo.getMana() + manaForEachRound);
                isPlayerOneTurnEnded = false;
                isPlayerTwoTurnEnded = false;

                if (playerOne.getCurrentDeck().size() != 0) {
                    if (playerOne.getCurrentDeck().get(0).getType().equals("environment")) {
                        playerOne.getHand().add(new EnvironmentCard(playerOne.getCurrentDeck().get(0)));
                        playerOne.getCurrentDeck().remove(0);
                    } else {
                        playerOne.getHand().add(new MinionCard(playerOne.getCurrentDeck().get(0)));
                        playerOne.getCurrentDeck().remove(0);
                    }
                }
                if (playerTwo.getCurrentDeck().size() != 0) {
                    if (playerTwo.getCurrentDeck().get(0).getType().equals("environment")) {
                        playerTwo.getHand().add(new EnvironmentCard(playerTwo.getCurrentDeck().get(0)));
                        playerTwo.getCurrentDeck().remove(0);
                    } else {
                        playerTwo.getHand().add(new MinionCard(playerTwo.getCurrentDeck().get(0)));
                        playerTwo.getCurrentDeck().remove(0);
                    }
                }
            }
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
