package fr.emacarte.database;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alexandre
 */
public class Database {
    
    public Database(){
        /* Chargement du driver JDBC pour MySQL */
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch ( ClassNotFoundException e ) {
            /* Gérer les éventuelles erreurs ici. */
        }
    }
    
    public String CreateUtilisateur(){
        return "CREATE TABLE  emacarte.Utilisateur ("
            + "id INT( 11 ) NOT NULL AUTO_INCREMENT ,"
            + "email VARCHAR( 60 ) NOT NULL ,"
            + "mot_de_passe VARCHAR( 32 ) NOT NULL ,"
            + "nom VARCHAR( 20 ) NOT NULL ,"
            + "date_inscription DATETIME NOT NULL ,"
            + "PRIMARY KEY ( id ),"
            + "UNIQUE ( email )"
            + ") ENGINE = INNODB";
    }
}
