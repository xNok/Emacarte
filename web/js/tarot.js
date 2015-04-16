(function($){
$(document).ready(function(){ 

var wsUri = "ws://localhost:8080/Emacarte/tarot/";

var websocket;
var output = $('#output');
init();

//inititialisation de la Web Socket
function init() {
    websocket = new WebSocket(wsUri + salle);
    
    websocket.onopen = function(evt) { onOpen(evt); };
    websocket.onmessage = function(evt) { onMessage(evt); };
    websocket.onclose = function(evt) { onClose(evt); };
    websocket.onerror = function(evt) { onError(evt); };
}

//évènement lié à la web socket

function onOpen(e) {
    writeToScreen("Connected to " + wsUri);
}

function onMessage(e){
    var message = JSON.parse(e.data);
    console.log(message);
    if(message.action === "chat"){
        writeToScreen('<span style="color: '+ message.color +';">'+message.message+'</span>');
    }else if(message.action === "afficherMain"){
        afficherMain(message.main);
    }  
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
    writeToScreen('<span style="color: red;">'+input+'</span>');
    
    var message = {
        action: "chat",
        message: input
    };
    
    sendText(JSON.stringify(message));
});

});

//action relative au Jeu de tarot

function afficherMain(main){
    alert("afficherMain");
    alert(main);
    $('#tapis').css("background-color", "#F0FFF0");
    
    $.each(main, function(i, item) {
        $('#tapis').append("<div class=\"carte\">" + item.couleur +";"+ item.valeur + "</div>");
        console.log(item.couleur +";"+ item.valeur);
    });
    
    
}

})(jQuery);








