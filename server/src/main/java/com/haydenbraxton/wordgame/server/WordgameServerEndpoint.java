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

import com.google.gson.Gson;
import com.haydenbraxton.wordgame.Constants;
import com.haydenbraxton.wordgame.actions.Action;
import com.haydenbraxton.wordgame.actions.ActionTypes;
import com.haydenbraxton.wordgame.actions.ClientUnscrambledWordAction;
import com.haydenbraxton.wordgame.actions.ServerScrambledWordAction;
import com.haydenbraxton.wordgame.actions.ServerUnscrambledWordAction;

@ServerEndpoint(value = "/game")
public class WordgameServerEndpoint {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private final Gson gson = new Gson();
	private WordRepository repository;

	public WordgameServerEndpoint() {
		this.repository = WordRepository.getInstance();
	}

	@OnOpen
	public void OnOpen(Session session) {
		logger.info("Connected ... " + session.getId());
	}

	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		Action action = this.gson.fromJson(message, Action.class);

		switch (action.type) {
			case ActionTypes.QUIT:
				this.quitSession(session);
				return;
			case ActionTypes.SCRAMBLED_WORD:
				this.sendScrambledWord(session);
				return;
			case ActionTypes.UNSCRAMBLED_WORD:
				ClientUnscrambledWordAction unscrambledWordAction = this.gson.fromJson(
					message,
					ClientUnscrambledWordAction.class
				);
				this.checkUnscrambledWord(session, unscrambledWordAction.unscrambledWord);
				return;
		}

		throw new RuntimeException(String.format("Action of type [%s] is not accepted", action.type));
	}

	private void sendScrambledWord(Session session) {
		String nextScrambledWord = this.getNextScrambledWord();
		session.getUserProperties().put(Constants.CURRENT_WORD, nextScrambledWord);
		ServerScrambledWordAction action = new ServerScrambledWordAction(nextScrambledWord);
		this.sendMessage(session, action);
	}

	private String getNextScrambledWord() {
		return this.repository.getRandomWord().getScrambledWord();
	}

	private void checkUnscrambledWord(Session session, String unscrambledWord) {
		String scrambledWord = (String) session.getUserProperties().get(Constants.CURRENT_WORD);
		Word word = this.repository.getWord(scrambledWord);
		String correctUnscrambledWord = word.getUnscrambbledWord();
		Boolean correct = correctUnscrambledWord.equals(unscrambledWord);

		ServerUnscrambledWordAction action = new ServerUnscrambledWordAction(
			correct, correctUnscrambledWord
		);
		this.sendMessage(session, action);
	}

	private void sendMessage(Session session, Action action) {
		session.getAsyncRemote().sendText(this.gson.toJson(action));
	}

	private void quitSession(Session session) {
		try {
			session.close(new CloseReason(CloseCodes.NORMAL_CLOSURE, "Game ended"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}		
	}
}
