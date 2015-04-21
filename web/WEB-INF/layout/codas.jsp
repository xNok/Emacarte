<%-- 
    Document   : coda - pied de page
    Created on : 1 mars 2015, 13:13:22
    Author     : Alexandre
--%>


<div class="test stick_to_bottom">
            
    <c:choose>
      <c:when test="${ empty sessionScope.sessionUtilisateur }">pizza.
        <form action="connexion.html">
            <a href="/Emacarte/connexion"><button class="btn btn-lg btn-primary btn-block" type="button">Connexion</button></a>
        </form>
      </c:when>

      <c:otherwise>pizzas.
        <a href="/Emacarte/profil"><img src="/Emacarte/img/avatar.png" class="avatar"></a>
      </c:otherwise>
    </c:choose>

</div>


<footer>
</footer>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="javascripts/bootstrap.js"></script>
    <script type="text/javascript">
    </script>
  </body>
</html>
