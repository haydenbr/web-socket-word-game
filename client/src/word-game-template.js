import './style.css'

const defaultInstructions = 'Unscramble the word!';

const template = `
<h1>Word Game</h1>
<div>
	<p id="user-instructions">${defaultInstructions}</p>
	<p><i id="scrambled-word"></i></p>
</div>
<div>
	<input id="input" />
	<button id="submit">submit</button>
	<button id="next" class="hide">next</button>
</div>`;

const hideElement = (element) => element.classList.add('hide');
const showElement = (element) => element.classList.remove('hide');
const addClickHandler = (element, callback) => element.addEventListener('click', (event) => callback(event));
const removeClickHandler = (element, callback) => element.removeClickHandler('click', callback);

export class WordGameTemplate {
	constructor() {
		this.initTemplate();
	}

	initTemplate() {
		let $templateElement = document.createElement('template');
		$templateElement.innerHTML = template
		document.body.appendChild($templateElement.content.cloneNode(true));

		this.$userInstructions = document.querySelector('#user-instructions');
		this.$scrambledWord = document.querySelector('#scrambled-word');
		this.$input = document.querySelector('#input');
		this.$submitButton = document.querySelector('#submit');
		this.$nextButton = document.querySelector('#next');
	}

	set onSubmit(handler) {
		if (this._onSubmit) {
			removeClickHandler(this.$submitButton, this._onSubmit);
		}

		this._onSubmit = handler;
		addClickHandler(this.$submitButton, this._onSubmit);
	}

	set onNext(handler) {
		if (this._onNext) {
			removeClickHandler(this.$nextButton, this._onNext);
		}

		this._onNext = handler;
		addClickHandler(this.$nextButton, this._onNext);
	}

	hideSubmit() {
		hideElement(this.$submitButton);
	}
	showSubmit() {
		showElement(this.$submitButton);
	}
	hideNext() {
		hideElement(this.$nextButton);
	}
	showNext() {
		showElement(this.$nextButton);
	}
	getInputValue() {
		return this.$input.value;
	}
	resetInputValue() {
		this.$input.value = '';
	}
	updateScrambledWord(scrambledWord) {
		this.$scrambledWord.textContent = scrambledWord;
	}
	resetInstructions() {
		this.$userInstructions.textContent = defaultInstructions;
	}
	setCorrectResponse() {
		this.$userInstructions.textContent = 'Good job! You got it right!';
	}
	setIncorrectResponse(correctUnscrambledWord) {
		this.$userInstructions.textContent = `Sorry, the right answer is "${correctUnscrambledWord}"`;
	}
}
