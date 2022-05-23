package com.github.meshotron2.room_creator.communication;

/**
 * Represents all the requests that can be received by this program.
 *
 * @param <T> The class that contains the actual data.
 *           It should be parseable by GSON, please refer to its documentation to learn more.
 */
public class Request<T> {
    /**
     * The type of the request
     */
    private final String type;
    /**
     * The data it holds
     */
    private final T data;

    protected Request(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", data=" + data.toString() +
                '}';
    }
}
