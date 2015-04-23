<%-- 
    Document   : preludes - entête
    Created on : 1 mars 2015, 13:13:12
    Author     : Alexandre
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>EMACarte</title>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Righteous">

    <!-- Bootstrap -->
    <link href="/Emacarte/css/style.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <header>
<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
    <ul class="nav navbar-nav navbar-left">
      <li>
          <a href="/Emacarte/accueil"><img src="/Emacarte/img/logo_site.png" class="logo"></a>
        <img src="/Emacarte/img/fond_logo.png" class="fond-logo">
      </li>
    </ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="bouton"><a href="/Emacarte/nouveautes">Nouveautés</a></li>
        <li class="bouton"><a href="/Emacarte/jeux">Jeux</a></li>
        <li class="bouton"><a href="/Emacarte/utilisateur/profil">Profil</a></li>
        <li class="bouton"><a href="/Emacarte/contact">Contact</a></li>
        <li>
          <img src="/Emacarte/img/EMA_credit.png" class="credit">
        </li>
      </ul>
    </div>
  </div>
</nav>
      <div class=" navmob">
      <div class="ptitrucbleu"></div>
      <img src="/Emacarte/img/logo_site.png" class="logomob">
      
        <div class="boutonmob">
          <a href="/Emacarte/profil">Profil</a>
        </div>
        <div class="boutonmob">
          <a href="#">Jeux</a>
        </div>
        <div class="boutonmob">
          <a href="#">Stats</a>
        </div>
        <div class="boutonmobbas">
          <a href="#">Tchat</a>
        </div>
        <div class="ptitrucdubas">
      </div>
</header>


  <div class="tchat">
 <c:if test="${ !empty sessionScope.sessionUtilisateur }">
    <div id="output" ></div>
    <form  action="post">        
        <input type="text" name="input" value="" id="input" class="text"/>
        <input type="submit" value="" id="submit" class="submitChat imgtchat" />
    </form>
    <img src="/Emacarte/img/tchatbout.jpg" href="/Emacarte/profil" class="imgtchat">
 </c:if>
  </div>


