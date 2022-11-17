package game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
                || card.getName().equals("Heart Hound")
                ) {
                    EnvironmentCard newCard = new EnvironmentCard();
                    newCard.copyFromCardInput(card);
                    cards.add(newCard);
                } else if (card.getName().equals("The Ripper")
                || card.getName().equals("Miraj")
                || card.getName().equals("The Cursed One")
                || card.getName().equals("Disciple")
                || card.getName().equals("Sentinel")
                || card.getName().equals("Berserker")
                || card.getName().equals("Goliath")
                || card.getName().equals("Warden")
                ) {
                    MinionCard newCard = new MinionCard();
                    newCard.copyFromCardInput(card);
                    cards.add(newCard);
                } else {
                    HeroCard newCard = new HeroCard();
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
        if (this.currentDeck.get(0).getType().equals("environment")) {
            this.hand.add(new EnvironmentCard(this.currentDeck.get(0)));
        } else {
            this.hand.add(new MinionCard(this.currentDeck.get(0)));
        }
        this.currentDeck.remove(0);
        this.mana = 1;
    }

    public ObjectNode deckOutput(ObjectMapper objectMapper, Action action) {
        ObjectNode getPlayerDeckNode = objectMapper.createObjectNode();
        getPlayerDeckNode.put("command", action.getCommand());
        getPlayerDeckNode.put("playerIdx", action.getPlayerIdx());
        ArrayNode getPlayerDeckArray = objectMapper.createArrayNode();
        for (Card card : this.getCurrentDeck()) {
            getPlayerDeckArray.add(card.cardOutput(objectMapper));
        }
        getPlayerDeckNode.put("output", getPlayerDeckArray);

        return getPlayerDeckNode;
    }

    public ObjectNode heroOutput(ObjectMapper objectMapper, Action action) {
        ObjectNode getPlayerHeroNode = objectMapper.createObjectNode();
        getPlayerHeroNode.put("command", action.getCommand());
        getPlayerHeroNode.put("playerIdx", action.getPlayerIdx());
        getPlayerHeroNode.put("output", this.getHeroCard().cardOutput(objectMapper));

        return getPlayerHeroNode;
    }

    public ObjectNode handOutput(ObjectMapper objectMapper, Action action) {
        ObjectNode getCardsInHandNode = objectMapper.createObjectNode();
        getCardsInHandNode.put("command", action.getCommand());
        getCardsInHandNode.put("playerIdx", action.getPlayerIdx());
        ArrayNode getCardsInHandArray = objectMapper.createArrayNode();
        for (Card card : this.getHand()) {
            getCardsInHandArray.add(card.cardOutput(objectMapper));
        }
        getCardsInHandNode.put("output", getCardsInHandArray);

        return getCardsInHandNode;
    }

    public ObjectNode manaOutput(ObjectMapper objectMapper, Action action) {
        ObjectNode getPlayerManaNode = objectMapper.createObjectNode();
        getPlayerManaNode.put("command", action.getCommand());
        getPlayerManaNode.put("playerIdx", action.getPlayerIdx());
        getPlayerManaNode.put("output", this.getMana());

        return getPlayerManaNode;
    }

    public ObjectNode environmentInHandOutput(ObjectMapper objectMapper, Action action) {
        ObjectNode environmentCardsInHand = objectMapper.createObjectNode();
        environmentCardsInHand.put("command", action.getCommand());
        environmentCardsInHand.put("playerIdx", action.getPlayerIdx());
        ArrayNode environmentCards = objectMapper.createArrayNode();
        for (Card card : this.getHand()) {
            if (card.getType().equals("environment")) {
                environmentCards.add(card.cardOutput(objectMapper));
            }
        }
        environmentCardsInHand.put("output", environmentCards);

        return environmentCardsInHand;
    }
}
