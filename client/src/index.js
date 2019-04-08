import _ from 'lodash';
import printMe from './print';

import './style.css';
import bananaGif from './banana.gif';

function component() {
  let element = document.createElement('div');

  // Lodash, currently included via a script, is required for this line to work
	element.innerHTML = _.join(['Hello', 'webpack'], ' ');
	element.classList.add('hello');

	var banana = new Image();
	banana.src = bananaGif;
	element.appendChild(banana);

	var button = document.createElement('button');
	button.textContent = 'Click me and check the console';
	button.onclick = printMe;
	element.appendChild(button);

  return element;
}

document.body.appendChild(component());
