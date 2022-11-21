package elements;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.CardInput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.ArrayList;

/**
 * The main class for creating the card element of this game, with its attributes.
 */
public class Card {
    /*
    a variable that keeps track of this card's type;
    this is the way I avoided the use of instanceof;
    it is a way to hold the type of card for all subclasses of this.
     */
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

    public Card() {
    }

    /**
     * copy all the data about this card from the card given as input.
     * @param card the card given as input.
     */
    public void copyFromCardInput(final CardInput card) {
        this.name = new String(card.getName());
        this.description = new String(card.getDescription());
        this.colors = new ArrayList<String>(card.getColors());
        this.health = card.getHealth();
        this.mana = card.getMana();
        this.attackDamage = card.getAttackDamage();
    }

    /**
     * getter for name element.
     * @return name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * setter for name element.
     * @param name the name to be assigned to this card.
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * getter for mana element.
     * @return mana.
     */
    public int getMana() {
        return this.mana;
    }

    /**
     * setter for mana element.
     * @param mana amount of mana to be assigned to this card.
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     * getter for description element.
     * @return description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * setter for description element.
     * @param description the description to be assigned to this card.
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * getter for colors element.
     * @return colors.
     */
    public ArrayList<String> getColors() {
        return this.colors;
    }

    /**
     * setter for colors element.
     * @param colors the colors to be assigned to this card.
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     * getter for attackDamage element.
     * @return attackDamage.
     */
    public int getAttackDamage() {
        return this.attackDamage;
    }

    /**
     * setter for attackDamage element.
     * @param attackDamage the amount of attackDamage to be assigned to this card.
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * getter for health element.
     * @return health.
     */
    public int getHealth() {
        return this.health;
    }

    /**
     * setter for health element.
     * @param health the amount of health to be assigned to this card.
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     * getter for type element.
     * @return type.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * setter for type element.
     * @param type the type to be assigned to this card.
     */
    public void setType(final Type type) {
        this.type = type;
    }

    /**
     * getter for isFrozen element.
     * @return true if it is frozen, false otherwise.
     */
    public boolean isFrozen() {
        return this.isFrozen;
    }

    /**
     * setter for isFrozen element.
     * @param frozen the freeze state to be assigned to this card.
     */
    public void setFrozen(final boolean frozen) {
        this.isFrozen = frozen;
    }

    /**
     * function that helps me display this card
     * @param objectMapper for creating the ObjectNode given to the output.
     * @return the node which will be added to the final output.
     */
    public ObjectNode cardOutput(final ObjectMapper objectMapper) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("mana", this.getMana());
        objectNode.put("attackDamage", this.getAttackDamage());
        objectNode.put("health", this.getHealth());
        objectNode.put("description", this.getDescription());
        ArrayNode colorsArray = objectMapper.createArrayNode();
        for (String color : this.getColors()) {
            colorsArray.add(color);
        }
        objectNode.set("colors", colorsArray);
        objectNode.put("name", this.getName());

        return objectNode;
    }
}
