package elements;

import fileio.CardInput;

import java.util.ArrayList;
import java.util.Random;
import static java.util.Collections.shuffle;

/**
 * Class for creating the player element of this game, with its attributes.
 */
public final class Player {
    private HeroCard heroCard;
    private ArrayList<ArrayList<Card>> decks;
    private ArrayList<Card> currentDeck;
    private ArrayList<Card> hand;
    private int mana;
    private boolean isTurnEnded = false;
    private boolean hasTankOnTable = false;
    private int numberOfWins;

    /**
     * getter for hasTankOnTable member, only it is boolean, so the name is changed
     * from a traditional getter.
     * @return true if this player has a tank placed on the table, false otherwise.
     */
    public boolean hasTankOnTable() {
        return this.hasTankOnTable;
    }

    public void setHasTankOnTable(final boolean hasTankOnTable) {
        this.hasTankOnTable = hasTankOnTable;
    }

    public boolean isTurnEnded() {
        return this.isTurnEnded;
    }

    public void setTurnEnded(final boolean turnEnded) {
        this.isTurnEnded = turnEnded;
    }

    public int getMana() {
        return this.mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public HeroCard getHeroCard() {
        return this.heroCard;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return this.decks;
    }

    public ArrayList<Card> getCurrentDeck() {
        return this.currentDeck;
    }

    public int getNumberOfWins() {
        return this.numberOfWins;
    }

    public void setNumberOfWins(final int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    /**
     * untraditional setter, copy data about the hero card
     * @param hero hero card input
     */
    public void setHeroCard(final CardInput hero) {
        this.heroCard = new HeroCard();
        this.heroCard.copyFromCardInput(hero);
    }

    /**
     * untraditional setter, copy all the data about the player's decks
     * @param decks deck input
     */
    public void setDecks(final ArrayList<ArrayList<CardInput>> decks) {
        this.decks = new ArrayList<ArrayList<Card>>();
        for (ArrayList<CardInput> deck : decks) {
            ArrayList<Card> cards = new ArrayList<Card>();
            for (CardInput card : deck) {
                if (card.getName().equals("Winterfell")
                        || card.getName().equals("Firestorm")
                        || card.getName().equals("Heart Hound")) {
                    EnvironmentCard newCard = new EnvironmentCard();
                    newCard.copyFromCardInput(card);
                    cards.add(newCard);
                } else {
                    MinionCard newCard = new MinionCard();
                    newCard.copyFromCardInput(card);
                    cards.add(newCard);
                }
            }
            this.decks.add(cards);
        }
    }

    /**
     * function that sets the current deck from the list of all the player's decks.
     * @param index index of the current deck to be played in the current game.
     * @param shuffle the seed for shuffling the cards.
     */
    public void setCurrentDeckAtIndex(final int index, final int shuffle) {
        this.currentDeck = this.getDecks().get(index);
        Random random = new Random();
        random.setSeed(shuffle);
        shuffle(this.currentDeck, random);
        this.hand = new ArrayList<Card>();

        //also put the first card in the player's hand
        if (this.currentDeck.get(0).getType() == Card.Type.ENVIRONMENT) {
            this.hand.add(new EnvironmentCard(this.currentDeck.get(0)));
        } else {
            this.hand.add(new MinionCard(this.currentDeck.get(0)));
        }
        this.currentDeck.remove(0);
        this.mana = 1;
    }
}
