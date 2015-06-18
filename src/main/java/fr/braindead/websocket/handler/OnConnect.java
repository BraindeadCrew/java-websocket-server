package fr.braindead.websocket.handler;

import fr.braindead.websocket.server.WebSocketClient;

/**
 *
 * Created by leiko on 18/06/15.
 */
public interface OnConnect {

    void handle(String path, WebSocketClient client);
}
