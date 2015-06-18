package fr.braindead.websocket.server;

import io.undertow.websockets.WebSocketConnectionCallback;
import io.undertow.websockets.core.*;
import io.undertow.websockets.spi.WebSocketHttpExchange;

import java.io.IOException;

/**
 *
 * Created by leiko on 18/06/15.
 */
class InternalServer implements WebSocketConnectionCallback {

    private InternalWebSocketServer server;

    protected InternalServer(InternalWebSocketServer server) {
        this.server = server;
    }

    @Override
    public void onConnect(WebSocketHttpExchange exchange, WebSocketChannel channel) {
        channel.resumeReceives();

        WebSocketClientImpl client = new WebSocketClientImpl(channel);
        this.server.invokeOnConnect(exchange.getRequestURI(), client);

        channel.getReceiveSetter().set(new AbstractReceiveListener() {
            @Override
            protected void onFullTextMessage(WebSocketChannel channel, BufferedTextMessage message) {
                client.invokeOnMessage(message.getData());
            }

            @Override
            protected void onFullCloseMessage(WebSocketChannel channel, BufferedBinaryMessage message) throws IOException {
                // Overriding onFullCloseMessage so that onFullTextMessage is called even though the data were sent by fragments
            }

            @Override
            protected void onClose(WebSocketChannel channel, StreamSourceFrameChannel frame) throws IOException {
                client.invokeOnClose();
                server.invokeOnClose(client);
            }

            @Override
            protected void onError(WebSocketChannel channel, Throwable error) {
                client.invokeOnError(error);
            }
        });

        channel.addCloseTask(chan -> {
            client.invokeOnClose();
            server.invokeOnClose(client);
        });
    }
}
