package fr.braindead.websocket.server;

import fr.braindead.websocket.handler.OnClose;
import fr.braindead.websocket.handler.OnError;
import fr.braindead.websocket.handler.OnMessage;
import io.undertow.websockets.core.WebSocketCallback;
import io.undertow.websockets.core.WebSocketChannel;
import io.undertow.websockets.core.WebSockets;

import java.io.IOException;

/**
 *
 * Created by leiko on 18/06/15.
 */
class WebSocketClientImpl implements WebSocketClient {

    private WebSocketChannel channel;
    private OnMessage onMessage;
    private OnClose onClose;
    private OnError onError;

    public WebSocketClientImpl(WebSocketChannel channel) {
        this.channel = channel;
    }

    @Override
    public void send(String msg) {
        this.send(msg, null);
    }

    @Override
    public void send(String msg, WebSocketCallback<Void> callback) {
        WebSockets.sendText(msg, this.channel, callback);
    }

    @Override
    public void sendBlocking(String msg) throws IOException {
        WebSockets.sendTextBlocking(msg, this.channel);
    }

    @Override
    public boolean isOpen() {
        return this.channel.isOpen();
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }

    @Override
    public void onMessage(OnMessage handler) {
        this.onMessage = handler;
    }

    @Override
    public void onClose(OnClose handler) {
        this.onClose = handler;
    }

    @Override
    public void onError(OnError handler) {
        this.onError = handler;
    }

    protected void invokeOnMessage(String msg) {
        if (this.onMessage != null) {
            this.onMessage.handle(msg);
        }
    }

    protected void invokeOnClose() {
        if (this.onClose != null) {
            this.onClose.handle();
        }
    }

    protected void invokeOnError(Throwable error) {
        if (this.onError != null) {
            this.onError.handle(error);
        }
    }
}
