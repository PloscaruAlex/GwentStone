package game;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

public class Card {
    public enum Type {
        HERO,
        MINION,
        ENVIRONMENT
    }
    private String name;
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private int attackDamage;
    private int health;
    private Type type;
    private boolean isFrozen = false;

    public Card() {}

    public Card(Card card) {
        this.name = new String(card.getName());
        this.description = new String(card.getDescription());
        this.colors = new ArrayList<String>(card.getColors());
        this.mana = card.getMana();
        this.health = card.getHealth();
        this.attackDamage = card.getAttackDamage();
    }

    public void copyFromCardInput(CardInput card) {
        this.name = new String(card.getName());
        this.description = new String(card.getDescription());
        this.colors = new ArrayList<String>(card.getColors());
        this.health = card.getHealth();
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isFrozen() {
        return isFrozen;
    }

    public void setFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public ObjectNode cardOutput(ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.getMana());
        objectNode.put("attackDamage", this.getAttackDamage());
        objectNode.put("health", this.getHealth());
        objectNode.put("description", this.getDescription());
        ArrayNode colorsArray = objectMapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsArray.add(color);
        }
        objectNode.put("colors", colorsArray);
        objectNode.put("name", this.getName());

        return objectNode;
    }
}
