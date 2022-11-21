package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Helper class to help me display errors.
 */
public class ErrorOutput {
    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode environmentCardPlace(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("error", "Cannot place environment card on table.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode notEnoughMana(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("error", "Not enough mana to place card on table.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode rowIsFull(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("error", "Cannot place card on table since row is full.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode noCardAtPosition(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("x", action.getX());
        err.put("y", action.getY());
        err.put("output", "No card available at that position.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode cardNotEnvironment(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Chosen card is not of type environment.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode notEnoughManaEnvironment(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Not enough mana to use environment card.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode notEnemyRow(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Chosen row does not belong to the enemy.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode cannotSteal(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Cannot steal enemy card since the player's row is full.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode attackedOwnCard(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        ObjectNode coordsAttacker = objectMapper.createObjectNode();
        coordsAttacker.put("x", action.getCardAttacker().getX());
        coordsAttacker.put("y", action.getCardAttacker().getY());
        err.set("cardAttacker", coordsAttacker);

        ObjectNode coordsAttacked = objectMapper.createObjectNode();
        coordsAttacked.put("x", action.getCardAttacked().getX());
        coordsAttacked.put("y", action.getCardAttacked().getY());
        err.set("cardAttacked", coordsAttacked);
        err.put("error", "Attacked card does not belong to the enemy.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode cardAlreadyAttacked(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        ObjectNode coordsAttacker = objectMapper.createObjectNode();
        coordsAttacker.put("x", action.getCardAttacker().getX());
        coordsAttacker.put("y", action.getCardAttacker().getY());
        err.set("cardAttacker", coordsAttacker);

        ObjectNode coordsAttacked = objectMapper.createObjectNode();
        coordsAttacked.put("x", action.getCardAttacked().getX());
        coordsAttacked.put("y", action.getCardAttacked().getY());
        err.set("cardAttacked", coordsAttacked);
        err.put("error", "Attacker card has already attacked this turn.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode cardIsFrozen(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        ObjectNode coordsAttacker = objectMapper.createObjectNode();
        coordsAttacker.put("x", action.getCardAttacker().getX());
        coordsAttacker.put("y", action.getCardAttacker().getY());
        err.set("cardAttacker", coordsAttacker);

        ObjectNode coordsAttacked = objectMapper.createObjectNode();
        coordsAttacked.put("x", action.getCardAttacked().getX());
        coordsAttacked.put("y", action.getCardAttacked().getY());
        err.set("cardAttacked", coordsAttacked);
        err.put("error", "Attacker card is frozen.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode cardNotTank(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        ObjectNode coordsAttacker = objectMapper.createObjectNode();
        coordsAttacker.put("x", action.getCardAttacker().getX());
        coordsAttacker.put("y", action.getCardAttacker().getY());
        err.set("cardAttacker", coordsAttacker);

        ObjectNode coordsAttacked = objectMapper.createObjectNode();
        coordsAttacked.put("x", action.getCardAttacked().getX());
        coordsAttacked.put("y", action.getCardAttacked().getY());
        err.set("cardAttacked", coordsAttacked);
        err.put("error", "Attacked card is not of type 'Tank'.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode attackedEnemyCard(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        ObjectNode coordsAttacker = objectMapper.createObjectNode();
        coordsAttacker.put("x", action.getCardAttacker().getX());
        coordsAttacker.put("y", action.getCardAttacker().getY());
        err.set("cardAttacker", coordsAttacker);

        ObjectNode coordsAttacked = objectMapper.createObjectNode();
        coordsAttacked.put("x", action.getCardAttacked().getX());
        coordsAttacked.put("y", action.getCardAttacked().getY());
        err.set("cardAttacked", coordsAttacked);
        err.put("error", "Attacked card does not belong to the current player.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode attackHeroCardIsFrozen(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        ObjectNode coordsAttacker = objectMapper.createObjectNode();
        coordsAttacker.put("x", action.getCardAttacker().getX());
        coordsAttacker.put("y", action.getCardAttacker().getY());
        err.set("cardAttacker", coordsAttacker);

        err.put("error", "Attacker card is frozen.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode attackHeroCardAlreadyAttacked(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        ObjectNode coordsAttacker = objectMapper.createObjectNode();
        coordsAttacker.put("x", action.getCardAttacker().getX());
        coordsAttacker.put("y", action.getCardAttacker().getY());
        err.set("cardAttacker", coordsAttacker);

        err.put("error", "Attacker card has already attacked this turn.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode attackHeroCardNotTank(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        ObjectNode coordsAttacker = objectMapper.createObjectNode();
        coordsAttacker.put("x", action.getCardAttacker().getX());
        coordsAttacker.put("y", action.getCardAttacker().getY());
        err.set("cardAttacker", coordsAttacker);

        err.put("error", "Attacked card is not of type 'Tank'.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode heroAbilityNotEnoughMana(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Not enough mana to use hero's ability.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode heroAbilityAlreadyAttacked(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Hero has already attacked this turn.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode heroAbilityNotEnemyRow(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Selected row does not belong to the enemy.");

        return err;
    }

    /**
     * @param action Current action of the game, given as input, used for displaying parameters.
     * @return the ObjectNode that will be added to output.
     */
    public static ObjectNode heroAbilityNotOwnRow(final Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Selected row does not belong to the current player.");

        return err;
    }
}
