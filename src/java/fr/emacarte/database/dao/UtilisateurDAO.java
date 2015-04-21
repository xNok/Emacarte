/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.database.dao;

import fr.emacarte.model.Utilisateur;

/**
 *
 * @author Alexandre
 */
public interface UtilisateurDAO {
    
    void creer( Utilisateur utilisateur ) throws DAOException;

    Utilisateur trouver( String email ) throws DAOException;
    
}
