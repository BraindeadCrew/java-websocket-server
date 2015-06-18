package fr.braindead.websocket.server;

import fr.braindead.websocket.handler.OnClose;
import fr.braindead.websocket.handler.OnError;
import fr.braindead.websocket.handler.OnMessage;
import io.undertow.websockets.core.WebSocketCallback;

import java.io.IOException;

/**
 *
 * Created by leiko on 18/06/15.
 */
public interface WebSocketClient {

    void send(String msg);

    void send(String msg, WebSocketCallback<Void> callback);

    void sendBlocking(String msg) throws IOException;

    boolean isOpen();

    void close() throws IOException;

    void onMessage(OnMessage handler);

    void onClose(OnClose handler);

    void onError(OnError handler);
}
