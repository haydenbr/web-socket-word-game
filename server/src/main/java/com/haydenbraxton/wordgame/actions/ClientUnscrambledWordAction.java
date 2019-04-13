package com.haydenbraxton.wordgame.actions;

import com.haydenbraxton.wordgame.actions.ActionTypes;

public class ClientUnscrambledWordAction extends Action {
	public String unscrambledWord;

	public ClientUnscrambledWordAction(String unscrambledWord) {
		super(ActionTypes.UNSCRAMBLED_WORD);
		this.unscrambledWord = unscrambledWord;
	}
}
