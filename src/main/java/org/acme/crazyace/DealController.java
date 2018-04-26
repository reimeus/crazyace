package org.acme.crazyace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DealController {

    private static final int ACE = 1;

    @Autowired
    private CardService      cardService;

    @RequestMapping("/getPlayerCards")
    public CardResponse getPlayerCards(String playerName) {

        CardResponse response = new CardResponse(cardService.getStartTime(), getImageLocation(cardService.getCurrentCard()));
        List<CardView> list = response.getCards();
        for (Card card : cardService.getPlayerCards(playerName)) {
            list.add(new CardView(card.getId(), getImageLocation(card), card.getSuit(), card.getValue() == 1));
        }
        response.setCurrentPlayer(cardService.getCurrentPlayer());
        response.setPlayersMap(cardService.getPlayersMap());
        response.setWinner(cardService.getWinner());
        response.setAceSuit(cardService.getAceSuit());

        return response;
    }

    @RequestMapping(value = "/playCard", method = RequestMethod.POST)
    public void playCard(@RequestBody PlayedCard cardVo) {

        System.out.println(">> Played " + cardVo);

        List<Card> playerCards = cardService.getPlayerCards(cardVo.getPlayerName());
        Card playedCard = playerCards.stream().filter(c -> cardVo.getCardId() == c.getId()).findFirst()
                .orElseThrow(() -> new BadRequestException(cardVo.getPlayerName() + " does not have card " + cardVo.getCardId()));

        Card currentCard = cardService.getCurrentCard();
        if (currentCard == null) {
            throw new BadRequestException("No current card");
        }

        // check for ace changeover
        Suit aceSuit = cardService.getAceSuit();
        if (aceSuit != null && playedCard.getValue() != ACE && playedCard.getSuit() != aceSuit) {
            throw new BadRequestException("Cannot play that card");
        }

        // no ace changeover
        if (aceSuit == null && playedCard.getValue() != ACE && playedCard.getSuit() != currentCard.getSuit()
                && playedCard.getValue() != currentCard.getValue()) {
            throw new BadRequestException("Cannot play that card");
        }

        playerCards.remove(playedCard);
        cardService.returnCard(currentCard);
        cardService.setCurrentCard(playedCard);
        cardService.setAceSuit(cardVo.getAceSuit());

        if (playerCards.isEmpty()) { // we have a winner
            cardService.setWinner(cardVo.getPlayerName());
        }
        cardService.nextPlayer();
    }

    @RequestMapping(value = "/requestCard", method = RequestMethod.POST)
    public void requestCard(@RequestParam String playerName) {
        List<Card> playerCards = cardService.getPlayerCards(playerName);
        playerCards.add(cardService.getCardFromDeck());
        cardService.nextPlayer();
    }

    private String getImageLocation(Card card) {
        if (card != null) {
            return "/resources/" + card.getImageName() + ".png";
        } else {
            return null;
        }
    }
}
