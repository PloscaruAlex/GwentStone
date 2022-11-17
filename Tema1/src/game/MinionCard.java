package game;

import java.util.ArrayList;

public class MinionCard extends Card {
    private String type = "minion";
    private String row;

    public MinionCard() {}

    public MinionCard(Card card) {
        this.setName(new String(card.getName()));
        this.setDescription(new String(card.getDescription()));
        this.setColors(new ArrayList<String>(card.getColors()));
        this.setHealth(card.getHealth());
        this.setMana(card.getMana());
        this.setAttackDamage(card.getAttackDamage());
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public String getRow() {
        if (this.getName().equals("The Ripper")
        || this.getName().equals("Miraj")
        || this.getName().equals("Goliath")
        || this.getName().equals("Warden")
        ) {
            this.setRow("front");
            return this.row;
        } else {
            this.setRow("back");
            return this.row;
        }
    }

    public void setRow(String row) {
        this.row = row;
    }
}
