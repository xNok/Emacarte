(function($){
$(document).ready(function(){ 

var wsUri = "ws://localhost:8080/Emacarte/tarot/";

var websocket;
var output = $('#output');
init();

//inititialisation de la Web Socket
function init() {
    websocket = new WebSocket(wsUri + salle);
    
    websocket.onopen = function(evt) { onOpen(evt) };
    websocket.onmessage = function(evt) { onMessage(evt) }
    websocket.onclose = function(evt) { onClose(evt) };
    websocket.onerror = function(evt) { onError(evt) };
}

//évènement lié à la web socket

function onOpen(e) {
    writeToScreen("Connected to " + wsUri);
}

function onMessage(e){
    writeToScreen('<span style="color: blue;">RESPONSE: ' + e.data+'</span>')
}

function onClose(e){
    
}

function onError(e){
    
}

/*
 * envoyer un message 
 */
function sendText(text){
    websocket.send(text);
}

/*
 * ecrire dans la page
 */
function writeToScreen(message) {  
    output.html(output.html() + message + "<br>");
}

//evènement
var submit = $('#submit');
submit.click(function(e){
    e.preventDefault(); //annule l'action d'envoi du formulaire
    var input = $('#input').val()
    writeToScreen(input);
    sendText(input);
    
});

});
})(jQuery);








