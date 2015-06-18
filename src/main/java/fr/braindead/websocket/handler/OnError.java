package fr.braindead.websocket.handler;

/**
 *
 * Created by leiko on 18/06/15.
 */
public interface OnError {

    void handle(Throwable error);
}
