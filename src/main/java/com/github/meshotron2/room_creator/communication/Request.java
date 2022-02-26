package com.github.meshotron2.room_creator.communication;

public class Request<T> {
    private final String type;
    private final T data;

    protected Request(String type, T data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
