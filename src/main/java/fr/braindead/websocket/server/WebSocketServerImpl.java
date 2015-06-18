package fr.braindead.websocket.server;

import fr.braindead.websocket.handler.OnConnect;
import io.undertow.Undertow;

import java.net.URI;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static io.undertow.Handlers.websocket;

/**
 *
 * Created by leiko on 18/06/15.
 */
public class WebSocketServerImpl implements WebSocketServer, InternalWebSocketServer {

    private Undertow server;
    private Set<OnConnect> onConnects = new LinkedHashSet<>();
    private Set<WebSocketClient> clients = new HashSet<>();

    public WebSocketServerImpl(URI uri) {
        this.server = Undertow.builder()
                .addHttpListener(uri.getPort(), uri.getHost())
                .setHandler(websocket(new InternalServer(this)))
                .build();
    }

    @Override
    public void start() {
        this.server.start();
    }

    @Override
    public void stop() {
        this.server.stop();
    }

    @Override
    public void onConnect(OnConnect handler) {
        this.onConnects.add(handler);
    }

    @Override
    public Set<WebSocketClient> getClients() {
        return this.clients;
    }

    @Override
    public void invokeOnConnect(String path, WebSocketClient client) {
        this.clients.add(client);
        this.onConnects.forEach(h -> h.handle(path, client));
    }

    @Override
    public void invokeOnClose(WebSocketClient client) {
        this.clients.remove(client);
    }
}
