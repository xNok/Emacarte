/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.emacarte.webApp;

import java.text.MessageFormat;
import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 *
 * Permet de synchroniser la Http session
 * 
 * @author Alexandre
 */
public class CustomConfigurator extends ServerEndpointConfig.Configurator
{
    HttpSession httpSession;

    // modifyHandshake() is called before getEndpointInstance()!
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response)
    {
        httpSession = (HttpSession) request.getHttpSession();
        super.modifyHandshake(sec, request, response);
    }

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException
    {
        T endpoint = super.getEndpointInstance(endpointClass);

        if (endpoint instanceof TarotWSEndpoint)
            ((TarotWSEndpoint) endpoint).setHttpSession(httpSession); // <-- The injection point
        else
            throw new InstantiationException(
                    MessageFormat.format("Expected instanceof \"{0}\". Got instanceof \"{1}\".",
                    TarotWSEndpoint.class, endpoint.getClass()));

        return endpoint;
    }
}
