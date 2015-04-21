/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.database.dao;

import fr.emacarte.model.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static fr.emacarte.database.dao.DAOUtilitaire.*;

/**
 *
 * @author Alexandre
 */
public class UtilisateurDAOImpl implements UtilisateurDAO{
    private DAOFactory daoFactory;

    public UtilisateurDAOImpl( DAOFactory daoFactory ) {
        this.daoFactory = daoFactory;
    }
    
    /*
    * Initialise la requête préparée basée sur la connexion passée en argument,
    * avec la requête SQL et les objets donnés.
    */
    public static PreparedStatement initialisationRequetePreparee( Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
        PreparedStatement preparedStatement = connexion.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
        for ( int i = 0; i < objets.length; i++ ) {
            preparedStatement.setObject( i + 1, objets[i] );
        }
        return preparedStatement;
    }
    
    /*
    * Simple méthode utilitaire permettant de faire la correspondance (le
    * mapping) entre une ligne issue de la table des utilisateurs (un
    * ResultSet) et un bean Utilisateur.
    */
   private static Utilisateur map( ResultSet resultSet ) throws SQLException {
       Utilisateur utilisateur = new Utilisateur();
       utilisateur.setId( resultSet.getLong( "id" ) );
       utilisateur.setEmail( resultSet.getString( "email" ) );
       utilisateur.setMotDePasse( resultSet.getString( "mot_de_passe" ) );
       utilisateur.setNom( resultSet.getString( "nom" ) );
       utilisateur.setDateInscription( resultSet.getTimestamp( "date_inscription" ) );
       return utilisateur;
   }

private static final String SQL_INSERT = "INSERT INTO Utilisateur (email, mot_de_passe, nom, date_inscription) VALUES (?, ?, ?, NOW())";

/* Implémentation de la méthode définie dans l'interface UtilisateurDao */
@Override
public void creer( Utilisateur utilisateur ) throws DAOException {
    Connection connexion = null;
    PreparedStatement preparedStatement = null;
    ResultSet valeursAutoGenerees = null;

    try {
        /* Récupération d'une connexion depuis la Factory */
        connexion = daoFactory.getConnection();
        preparedStatement = initialisationRequetePreparee( connexion, SQL_INSERT, true, utilisateur.getEmail(), utilisateur.getMotDePasse(), utilisateur.getNom() );
        int statut = preparedStatement.executeUpdate();
        /* Analyse du statut retourné par la requête d'insertion */
        if ( statut == 0 ) {
            throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
        }
        /* Récupération de l'id auto-généré par la requête d'insertion */
        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
        if ( valeursAutoGenerees.next() ) {
            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
            utilisateur.setId( valeursAutoGenerees.getLong( 1 ) );
        } else {
            throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
        }
    } catch ( SQLException e ) {
        throw new DAOException( e );
    } finally {
        fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
    }
}

    private static final String SQL_SELECT_PAR_EMAIL = "SELECT id, email, nom, mot_de_passe, date_inscription FROM Utilisateur WHERE email = ?";

    /* Implémentation de la méthode définie dans l'interface UtilisateurDao */
    @Override
    public Utilisateur trouver( String email ) throws DAOException {
        Connection connexion = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Utilisateur utilisateur = null;

        try {
            /* Récupération d'une connexion depuis la Factory */
            connexion = daoFactory.getConnection();
            preparedStatement = initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, email );
            resultSet = preparedStatement.executeQuery();
            /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
            if ( resultSet.next() ) {
                utilisateur = map( resultSet );
            }
        } catch ( SQLException e ) {
            throw new DAOException( e );
        } finally {
            fermeturesSilencieuses( resultSet, preparedStatement, connexion );
        }

        return utilisateur;
    }
    
    
}
