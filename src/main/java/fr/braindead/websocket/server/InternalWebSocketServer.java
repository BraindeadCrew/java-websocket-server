package fr.braindead.websocket.server;

/**
 *
 * Created by leiko on 18/06/15.
 */
interface InternalWebSocketServer {

    void invokeOnConnect(String uri, WebSocketClient client);

    void invokeOnClose(WebSocketClient client);
}
