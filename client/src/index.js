import { WordGameTemplate } from './word-game-template';
import { ActionTypes } from './action-types';

let socket;
let template = new WordGameTemplate();

const initSocket = () => {
	socket = new WebSocket('ws://localhost:2419/websockets/game');

	template.onSubmit = () => {
		let unscrambledWord = template.getInputValue();
		sendMessage(socket, {type: ActionTypes.UNSCRAMBLED_WORD, unscrambledWord});

		template.resetInputValue();
		template.hideSubmit();
		template.showNext();
	};

	template.onNext = () => {
		template.resetInstructions();
		template.showSubmit();
		template.hideNext();
		getNextScrambledWord(socket)
	};

	socket.onopen = (event) => {
		console.log('connected', event);
		getNextScrambledWord(socket);
	}

	socket.onclose = (event) => {
		console.log('closed the connection', event);
	}

	socket.onmessage = (event) => {
		let action = JSON.parse(event.data);
		console.log('action', action);
		handleAction(action);
	}
}

const getNextScrambledWord = (socket) => sendMessage(socket, {type: ActionTypes.SCRAMBLED_WORD});

const handleAction = (action) => {
	switch (action.type) {
		case ActionTypes.SCRAMBLED_WORD:
			return handleNewScrambledWord(action);
		case ActionTypes.UNSCRAMBLED_WORD:
			return handleUnscrambledWordResponse(action);
	}
}

const handleNewScrambledWord = (action) => template.updateScrambledWord(action.scrambledWord);

const handleUnscrambledWordResponse = (action) => {
	if (action.correct) {
		template.setCorrectResponse();
	} else {
		template.setIncorrectResponse(action.correctUnscrambledWord);
	}
};

const sendMessage = (socket, action) => socket.send(JSON.stringify(action));

initSocket();
