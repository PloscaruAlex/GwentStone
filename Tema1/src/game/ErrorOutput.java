package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ErrorOutput {
    static public ObjectNode environmentCardPlace(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("error", "Cannot place environment card on table.");

        return err;
    }

    static public ObjectNode notEnoughMana(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("error", "Not enough mana to place card on table.");

        return err;
    }

    static public ObjectNode rowIsFull(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("error", "Cannot place card on table since row is full.");

        return err;
    }

    static public ObjectNode noCardAtPosition(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("x", action.getX());
        err.put("y", action.getY());
        err.put("output", "No card available at that position.");

        return err;
    }

    static public ObjectNode cardNotEnvironment(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Chosen card is not of type environment.");

        return err;
    }

    static public ObjectNode notEnoughManaEnvironment(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Not enough mana to use environment card.");

        return err;
    }

    static public ObjectNode notEnemyRow(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Chosen row does not belong to the enemy.");

        return err;
    }

    static public ObjectNode cannotSteal(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Cannot steal enemy card since the player's row is full.");

        return err;
    }

    static public ObjectNode attackedOwnCard(Action action) {
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

    static public ObjectNode cardAlreadyAttacked(Action action) {
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

    static public ObjectNode cardIsFrozen(Action action) {
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

    static public ObjectNode cardNotTank(Action action) {
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

    static public ObjectNode attackedEnemyCard(Action action) {
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

    static public ObjectNode attackHeroCardIsFrozen(Action action) {
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

    static public ObjectNode attackHeroCardAlreadyAttacked(Action action) {
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

    static public ObjectNode attackHeroCardNotTank(Action action) {
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

    static public ObjectNode heroAbilityNotEnoughMana(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Not enough mana to use hero's ability.");

        return err;
    }

    static public ObjectNode heroAbilityAlreadyAttacked(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Hero has already attacked this turn.");

        return err;
    }

    static public ObjectNode heroAbilityNotEnemyRow(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Selected row does not belong to the enemy.");

        return err;
    }

    static public ObjectNode heroAbilityNotOwnRow(Action action) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());

        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Selected row does not belong to the current player.");

        return err;
    }
}
