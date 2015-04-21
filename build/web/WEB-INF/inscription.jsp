<%-- 
    Document   : inscription
    Created on : 21 avr. 2015, 10:06:14
    Author     : Setra
--%>

<div class="corps">
  <div class="container">
      <div class="row">
      <div class="col-md-4">
      </div>
      <div class="col-md-4">
        <form class="form-signin" method="post" action="inscription">
          <h2 class="form-signin-heading">Inscrivez vous</h2>

              <label for="email">Adresse email <span class="requis">*</span></label>
              <input type="email" id="email" name="email" value="<c:out value="${utilisateur.email}"/>" size="20" maxlength="60" />
              <span class="erreur">${form.erreurs['email']}</span>
              <br />

              <label for="motdepasse">Mot de passe <span class="requis">*</span></label>
              <input type="password" id="motdepasse" name="motdepasse" value="" size="20" maxlength="20" />
              <span class="erreur">${form.erreurs['motdepasse']}</span>
              <br />

              <label for="confirmation">Confirmation du mot de passe <span class="requis">*</span></label>
              <input type="password" id="confirmation" name="confirmation" value="" size="20" maxlength="20" />
              <span class="erreur">${form.erreurs['confirmation']}</span>
              <br />

              <label for="nom">Nom d'utilisateur</label>
              <input type="text" id="nom" name="nom" value="<c:out value="${utilisateur.nom}"/>" size="20" maxlength="20" />
              <span class="erreur">${form.erreurs['nom']}</span>
              <br />

              <input type="submit" value="Inscription" class="sansLabel" />
              <br />

              <p class="${empty form.erreurs ? 'succes' : 'erreur'}">${form.resultat}</p>
        </form>
      </div>
      <div class="col-md-4">
      </div>
      </div>
    </div>
</div>
