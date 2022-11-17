package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ErrorOutput {
    static public ObjectNode environmentCardPlace(ObjectMapper objectMapper, Action action) {
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("error", "Cannot place environment card on table.");

        return err;
    }

    static public ObjectNode notEnoughMana(ObjectMapper objectMapper, Action action) {
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("error", "Not enough mana to place card on table.");

        return err;
    }

    static public ObjectNode rowIsFull(ObjectMapper objectMapper, Action action) {
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("error", "Cannot place card on table since row is full.");

        return err;
    }

    static public ObjectNode noCardAtPosition(ObjectMapper objectMapper, Action action) {
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("x", action.getX());
        err.put("y", action.getY());
        err.put("error", "No card at that position.");

        return err;
    }

    static public ObjectNode cardNotEnvironment(ObjectMapper objectMapper, Action action) {
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Chosen card is not of type environment.");

        return err;
    }

    static public ObjectNode notEnoughManaEnvironment(ObjectMapper objectMapper, Action action) {
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Not enough mana to use environment card.");

        return err;
    }

    static public ObjectNode notEnemyRow(ObjectMapper objectMapper, Action action) {
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Chosen row does not belong to the enemy.");

        return err;
    }

    static public ObjectNode cannotSteal(ObjectMapper objectMapper, Action action) {
        ObjectNode err = objectMapper.createObjectNode();
        err.put("command", action.getCommand());
        err.put("handIdx", action.getHandIdx());
        err.put("affectedRow", action.getAffectedRow());
        err.put("error", "Cannot steal enemy card since the player's row is full.");

        return err;
    }
}
