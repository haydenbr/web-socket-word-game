package com.haydenbraxton.wordgame.actions;

import com.haydenbraxton.wordgame.actions.ActionTypes;

public class ServerScrambledWordAction extends Action {
	public String scrambledWord;

	public ServerScrambledWordAction(String scrambledWord) {
		super(ActionTypes.SCRAMBLED_WORD);
		this.scrambledWord = scrambledWord;
	}
}
