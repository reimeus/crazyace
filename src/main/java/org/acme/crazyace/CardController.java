package org.acme.crazyace;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CardController {

	@Autowired
	private CardService cardService;

	@RequestMapping(value = "/dealCard", method = RequestMethod.POST)
	public String dealCard(@RequestParam String playerName) {
		cardService.dealCard(playerName);

		return "dealcard";
	}

	@RequestMapping("/")
	public String init(Model model) {
		model.addAttribute("defaultName", cardService.getNextPlayerName());
		return "startgame";
	}

	@RequestMapping(value = "/startGame", method = RequestMethod.POST)
	public String startGame(Model model, @RequestParam String playerName) {

		System.out.println("Starting for " + playerName);
		model.addAttribute("playerName", playerName);
		cardService.addPlayer(playerName);

		return "carddeck";
	}
}
