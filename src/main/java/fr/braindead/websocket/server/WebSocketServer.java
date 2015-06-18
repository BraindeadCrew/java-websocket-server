package fr.braindead.websocket.server;

import fr.braindead.websocket.handler.OnConnect;

import java.util.Set;

/**
 *
 * Created by leiko on 18/06/15.
 */
public interface WebSocketServer {

    void start();

    void stop();

    void onConnect(OnConnect handler);

    Set<WebSocketClient> getClients();
}
