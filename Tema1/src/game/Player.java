package game;

import fileio.CardInput;

import java.util.ArrayList;
import java.util.Random;
import static java.util.Collections.shuffle;

public class Player {
    private HeroCard heroCard;
    private ArrayList<ArrayList<Card>> decks;
    private ArrayList<Card> currentDeck;
    private ArrayList<Card> hand;
    private int mana;
    private boolean isTurnEnded = false;
    private boolean hasTankOnTable = false;
    private int numberOfWins;

    public boolean hasTankOnTable() {
        return hasTankOnTable;
    }

    public void setHasTankOnTable(boolean hasTankOnTable) {
        this.hasTankOnTable = hasTankOnTable;
    }

    public boolean isTurnEnded() {
        return isTurnEnded;
    }

    public void setTurnEnded(boolean turnEnded) {
        isTurnEnded = turnEnded;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public HeroCard getHeroCard() {
        return heroCard;
    }

    public ArrayList<ArrayList<Card>> getDecks() {
        return decks;
    }

    public ArrayList<Card> getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(ArrayList<Card> currentDeck) {
        this.currentDeck = currentDeck;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public void setHeroCard(CardInput hero) {
        this.heroCard = new HeroCard();
        this.heroCard.copyFromCardInput(hero);
    }

    public void setDecks(ArrayList<ArrayList<CardInput>> decks) {
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

    public void setCurrentDeckAtIndex(int index, int shuffle) {
        this.currentDeck = this.getDecks().get(index);
        Random random = new Random();
        random.setSeed(shuffle);
        shuffle(this.currentDeck, random);
        this.hand = new ArrayList<Card>();
        if (this.currentDeck.get(0).getType() == Card.Type.ENVIRONMENT) {
            this.hand.add(new EnvironmentCard(this.currentDeck.get(0)));
        } else {
            this.hand.add(new MinionCard(this.currentDeck.get(0)));
        }
        this.currentDeck.remove(0);
        this.mana = 1;
    }
}
