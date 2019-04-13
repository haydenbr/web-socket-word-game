package com.haydenbraxton.wordgame.actions;

import com.haydenbraxton.wordgame.actions.ActionTypes;

public class ServerUnscrambledWordAction extends Action {
	public Boolean correct;
	public String correctUnscrambledWord;

	public ServerUnscrambledWordAction(
		Boolean correct,
		String correctUnscrambledWord
	) {
		super(ActionTypes.UNSCRAMBLED_WORD);
		this.correct = correct;
		this.correctUnscrambledWord = correctUnscrambledWord;
	}
}
