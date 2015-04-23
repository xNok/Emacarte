<%-- 
    Document   : salleGame
    Created on : 17 mars 2015, 10:31:54
    Author     : Alexandre
--%>
<style>
    .carte{
        margin: 20px;
        width: 30px;
        height: 30px;
        background-color: red;
        float: left;
        text-align: center;
        vertical-align: middle;
    }
    
    #JeuContainer{
        float: right;
        width: 50%;
    }
    
    #depose{
        min-height: 40px;
        width: 100%;
    }
    #annonces{
        width: 100%;
        background-color: aliceblue;
        min-height: 70px;
        display: none;
    }
    .annonce{
        display: inline-block;
        float: left;
        background-color: chocolate;
        height: 30px;
        margin: 20px;
    }
    #tapis{
        min-height: 100px;
    }
    #chat{
        width: 50%;
        float: left;
    }
    #output{
        max-height: 800px;
        overflow: scroll;
    }
    .selected{
        background-color: #2989d8;
    }
    
    #infoJeu{
        height: 20%;
        overflow: scroll;
        position: fixed;
        bottom: 80px;
        right: 0;
    }
</style>

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
            <div id="JeuContainer" class="row">
               <div id="annonces">
                   <div class="annonce" idannonce="0"><span>Passer</span></div>
                   <div class="annonce" idannonce="1"><span>Petite</span></div>
                   <div class="annonce" idannonce="2"><span>Garde</span></div>
                   <div class="annonce" idannonce="4"><span>Garde Sans</span></div>
                   <div class="annonce" idannonce="6"><span>Garde Contre</span></div>
               </div>                
                <div id="tapis">
                    <!-- Injection JQuery -->
                </div>
            </div>
                <div id="infoJeu" class="col-md-10">
                    <!-- Injection JQuery -->
                </div>
        </div>
      </div>
    </div>
  </div>
</div>





<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
    var salle = ${ salle }
</script> 
<script src="/Emacarte/js/tarot.js"></script>
