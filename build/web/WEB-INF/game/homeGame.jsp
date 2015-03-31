<%-- 
    Document   : homeGame
    Created on : 17 mars 2015, 09:12:50
    Author     : Alexandre
--%>
<ul>
    <li><a href="jeux/create" >Créer une partie</a></li>
    <c:out value="${ salles }" />
    <c:forEach items="${ salles }" var="titre" varStatus="status">
        <li><a href="jeux/salle_de_jeux?num=${ titre }" >Rejoindre la salle N° <c:out value="${ titre }" /></a></li>
    </c:forEach>
</ul>



