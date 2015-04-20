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
<div class="corps">
  <div class=container>
    <div  class="row">
      <div class="col-md-1">
      </div>
      <div class="col-md-11">
        <div class="col-md-12">
            <ul>
                <li><a href="jeux/create" >Créer une partie</a></li>
                <c:out value="${ salles }" />
                <c:forEach items="${ salles }" var="titre" varStatus="status">
                    <li><a href="jeux/salle_de_jeux?num=${ titre }" >Rejoindre la salle N° <c:out value="${ titre }" /></a></li>
                </c:forEach>
            </ul>
        </div>
      </div>
    </div>
  </div>
</div>





