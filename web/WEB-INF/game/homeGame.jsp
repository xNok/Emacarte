<%-- 
    Document   : homeGame
    Created on : 17 mars 2015, 09:12:50
    Author     : Alexandre
--%>
<%-- 
    Document   : accueil
    Created on : 7 avr. 2015, 10:47:47
    Author     : Setra
--%>
<div class="corps imgfondpartie">
  <div class="container ">
    <div  class="row">
        <div class="col-md-10">
            </div>
            <div class="col-md-2">
                <a href="/Emacarte/regles" ><button class="btn btn-lg btn-primary btn-block">Regles</button></a>
            </div>
      <div class="col-md-11">
        <div class="col-md-12">
            <br>
            <br>
        <a href="jeux/create" ><button class="btn btn-lg btn-primary btn-block" type="submit">Créer une partie</button></a>
            <div class="col-md-3">
            </div>
            <div class="col-md-4">
            <ul>
                <c:forEach items="${ salles }" var="titre" varStatus="status">
                    <li><a href="jeux/salle_de_jeux?num=${ titre }" ><button class="btn btn-lg btn-primary btn-block">Rejoindre la salle N° <c:out value="${ titre }" /></button></a></li>
                </c:forEach>
            </ul>
            </div>
            <div class="col-md-5">
            </div>
        </div>
      </div>
    </div>
  </div>
</div>





