<%-- 
    Document   : coda - pied de page
    Created on : 1 mars 2015, 13:13:22
    Author     : Alexandre
--%>


<div class="test stick_to_bottom">
    <div class="row">         
        <c:choose>
          <c:when test="${ empty sessionScope.sessionUtilisateur }">
            <form action="connexion.html">
                <a href="/Emacarte/connexion"><button class="btn btn-lg btn-primary btn-block" type="button">Connexion</button></a>
            </form>
          </c:when>

          <c:otherwise>
              <div class="col-md-6 col-md-offset-1">
                <a href="/Emacarte/profil"><img src="/Emacarte/img/avatar.png" class="avatar"></a>
                <p>Vous étes connécté</p>
                <p>${sessionScope.sessionUtilisateur.email}</p>
              </div>
          </c:otherwise>
        </c:choose>
    </div>   
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
