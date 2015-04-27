<%-- 
    Document   : connexion
    Created on : 19 avr. 2015, 19:50:46
    Author     : Setra
--%>

<div class="corps">
  <div class="container">
      <div class="row">
      <div class="col-md-4">
      </div>
      <div class="col-md-4">
      <form class="form-signin" method="post" action="connexion">
        <h2 class="form-signin-heading">Connectez vous</h2>
                <label for="nom">Adresse email <span class="requis">*</span></label>
                <input type="email" id="email" name="email" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
                <span class="erreur">${form.erreurs['email']}</span>
                <br />

                <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
                <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
                <span class="erreur">${form.erreurs['motdepasse']}</span>
                <br />
                
        <button class="btn btn-lg btn-primary btn-block" type="submit">Connexion</button>
                <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
                
        <%-- Vérification de la présence d'un objet utilisateur en session --%>
        <c:if test="${!empty sessionScope.sessionUtilisateur}">
            <%-- Si l'utilisateur existe en session, alors on affiche son adresse email. --%>
            <p class="succes">Vous êtes connecté(e) avec l'adresse : ${sessionScope.sessionUtilisateur.email}</p>
        </c:if>
      </form>
        <a href="/Emacarte/inscription"><button class="btn btn-lg btn-primary btn-block">Inscription</button></a>
      </div>
      <div class="col-md-4">
      </div>
      </div>
    </div>
</div>