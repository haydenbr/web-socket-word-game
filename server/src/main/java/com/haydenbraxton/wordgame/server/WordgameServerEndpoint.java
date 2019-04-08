package com.haydenbraxton.wordgame.server;

import java.io.IOException;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/game")
public class WordgameServerEndpoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@OnOpen
	public void OnOpen(Session session) {
		logger.info("Connected ... " + session.getId());
	}

	@OnMessage
	public String onMessage(String unscrambledWord, Session session) {
		switch (unscrambledWord) {
			case "start":
				logger.info("Starting the game by sending the first word");
				String scrambledWord = WordRepository.getInstance().getRandomWord().getScrambledWord();
				session.getUserProperties().put("scrambledWord", scrambledWord);
				return scrambledWord;
			case "quit":
				try {
					session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game ended"));
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				break;
		}

		String scrambledWord = (String) session.getUserProperties().get("scrambledWord");
		return checkLastWordAndSendANewWord(scrambledWord, unscrambledWord, session);
	}

	private String checkLastWordAndSendANewWord(String scrambledWord, String unscrambledWord, Session session) {
		WordRepository repository = WordRepository.getInstance();
		Word word = repository.getWord(scrambledWord);

		String nextScrambledWord = repository.getRandomWord().getScrambledWord();

		session.getUserProperties().put("scrambledWord", nextScrambledWord);

		String correctUnscrambledWord = word.getUnscrambbledWord();

		if (word == null || !correctUnscrambledWord.equals(unscrambledWord)) {
			return String.format(
				"You guessed it wrong. Correct answer %s. Try the next one .. %s",
				correctUnscrambledWord,
				nextScrambledWord
			);
		}
		return String.format("You guessed it right. Try the next word ...  %s", nextScrambledWord);
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
	}
}
