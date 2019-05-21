var stompClient = null;


function setConnected(connected) {
	document.getElementById("connectButton").disabled = connected;
	document.getElementById("disconnectButton").disabled = !connected;
}

function connect() {
	console.log('attempting connection...');
	var socket = new SockJS('/websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function (frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/' + roomNumber, function(announcement) {
			processResponse(JSON.parse(announcement.body));
		});
		sendResponse('JOIN', false);
	});
}

function processResponse(response) {
	console.log("got response!");
	console.log("hostResponded: " + response.hostResponded);
	console.log("clientResponded: " + response.clientResponded);
	console.log("result: " + response.result);
}

function connectButtonClicked() {
	connect();
}

function disconnectButtonClicked() {
	disconnect();
}

function noButtonClicked() {
	sendResponse('RESPONSE', false);
	document.getElementById("yesButton").disabled = true;
	document.getElementById("noButton").disabled = true;
}

function yesButtonClicked() {
	sendResponse('RESPONSE', true);
	document.getElementById("yesButton").disabled = true;
	document.getElementById("noButton").disabled = true;
}

function sendResponse(responseType, response) {
	console.log("sending response: " + response);
	stompClient.send('/app/' + roomNumber + '/' + roomType, {}, JSON.stringify({type: responseType, answer: response}));
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

window.onload = function() {
	document.getElementById('connectButton').addEventListener('click', connectButtonClicked);
	document.getElementById('disconnectButton').addEventListener('click', disconnectButtonClicked);
	document.getElementById('yesButton').addEventListener('click', yesButtonClicked);
	document.getElementById('noButton').addEventListener('click', noButtonClicked);
	connect();
}

