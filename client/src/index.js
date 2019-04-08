var template = document.createElement('template');
template.innerHTML = `
<h1>Word Game</h1>

<div><i id="scrambled-word">waiting on server ...</i></div>
<div>
	<input id="input" />
	<button id="submit">submit</button>
</div>
`;

document.body.appendChild(template.content.cloneNode(true));
var scrambledWord = document.querySelector('#scrambled-word');
var input = document.querySelector('#input');
var submit = document.querySelector('#submit');

var socket = new WebSocket('ws://localhost:4419/websockets/game');
socket.onopen = (event) => {
	console.log('connected', event);
	socket.send('start');
}

socket.onmessage = (event) => {
	console.log('message', event);
	scrambledWord.textContent = event.data;
}

socket.onclose = (event) => {
	console.log('closed the connection', event)
}

submit.addEventListener('click', () => {
	socket.send(input.value);
	input.value = '';
});
