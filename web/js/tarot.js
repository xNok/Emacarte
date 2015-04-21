(function($){
    
$(document).ready(function(){ 

//variables Globales
var wsUri = "ws://localhost:8080/Emacarte/tarot/";

var websocket;
var output = $('#output');
init();

//---------------inititialisation de la Web Socket---------------\\
function init() {
    websocket = new WebSocket(wsUri + salle);
    
    websocket.onopen = function(evt) { onOpen(evt); };
    websocket.onmessage = function(evt) { onMessage(evt); };
    websocket.onclose = function(evt) { onClose(evt); };
    websocket.onerror = function(evt) { onError(evt); };
}
//---------------évènement lié à la web socket---------------\\
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
    }else if(message.action === "annonce"){
        afficherAnnonce();
    }else if(message.action === "afficherLevee"){
        afficherLevee();
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

//---------------evènement---------------\\

//message dans la chat
$("#submit").click(function(e){
        e.preventDefault(); //annule l'action d'envoi du formulaire
        var input = $('#input').val();
        writeToScreen('<span style="color: red;">'+input+'</span>');

        var message = {
            action: "chat",
            message: input
        };

        sendText(JSON.stringify(message));
    });
//annonce
$('.annonce').click(function(){
    //affiche un msg dans la console
    writeToScreen('<span style="color: green;">Annonce enregisté</span>');
    
    //envoi du message à l'appli
    var message = {
        action: "annonce",
        valeur: $(this).attr('idannonce')
    };
    
    //masquer la panneau d'annonce
    $('#annonces').hide();
    
    sendText(JSON.stringify(message));  
});

//-----variable de jeux
var carteDepose;

//---------------action relative au Jeu de tarot---------------\\

/**
 * 
 * @param {type} main
 * @returns Affiche la main et enregistre l'évènement jouer carte
 */
function afficherMain(main){
    alert("afficherMain");
    
    //on vide le tapis précédent
    $('#tapis').empty();
    
    //on affiche le tapis
    $('#tapis').css("background-color", "#F0FFF0");
    
    //affiche des cartes
    $.each(main, function(i, item) {
        $('#tapis').append(
                "<div class=\"carte\" couleur=\""+ item.couleur +"\" valeur=\""+ item.valeur +"\" idcarte=\""+ i +"\">"
                + "<span>" + item.couleur +";"+ item.valeur + "</span>" +
                "</div>"
        );
    });
    
    //mise à jours, enregistrement de l'évènement pour les cartes
    var cartes = $(".carte");
    cartes.click(function() {
      var carte = $(this);
      var idcarte = carte.attr("idcarte");
      var valeur = carte.attr("valeur");
      var couleur = carte.attr("couleur");
      jouerCarte(idcarte, valeur, couleur, carte );      
    });
}
/**
 * 
 * @param {type} idcarte
 * @param {type} valeur
 * @param {type} couleur
 * @param {type} carte élément selectionée avec Jquery
 * @returns {undefined}
 */
function jouerCarte(idcarte, valeur, couleur, carte){
    alert("jouerCarte");
    
    //la dépose n'est pas vide
    if(carteDepose){
        carteDepose.toggleClass("selected");
    }
    
    
    carteDepose = carte;    
    carteDepose.toggleClass("selected");
    
    //achiche une messag edans la console
    writeToScreen('<span style="color: green;">Carte jouée</span>');
    
    //envoi du message à l'appli
    var message = {
        action: "jouerCarte",
        valeur: idcarte
    };
    
    sendText(JSON.stringify(message));     
}

/**
 * Afficher la demande d'annonce
 */
function afficherAnnonce(){
    alert("afficherAnnonce")
    $('#annonces').show();
}

/**
 * 
 * afficher les carte joué par les autres joueurs
 */
function afficherLevee(){
    alert("afficherLevee");
}

});

})(jQuery);








