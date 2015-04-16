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
    
    #depose{
        min-height: 10px;
        background-color: orange;
    }
</style>

<div id="tapis" style="width: 50%; float: right; min-height: 100px">
    <div id="depose"></div>
</div>
<div style="width: 50%">
<div id="output" ></div>
<form action="post">
    <input type="text" name="input" value="" id="input" />
    <input type="submit" value="Envoyer" id="submit"/>
</form>
</div>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script>
    var salle = ${ salle }
</script> 
<script src="/Emacarte/js/tarot.js"></script>
