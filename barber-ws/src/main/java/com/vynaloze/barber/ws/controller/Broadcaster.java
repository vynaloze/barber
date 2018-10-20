package com.vynaloze.barber.ws.controller;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Broadcaster {
    private final Map<Integer, Queue<String>> clientsQueues = new ConcurrentHashMap<>();

    public synchronized void addClient(final int id) {
        clientsQueues.put(id, new ConcurrentLinkedQueue<>());
    }

    public synchronized Optional<String> getMessage(final int id) {
        return Optional.ofNullable(clientsQueues.get(id).poll());
    }

    public synchronized void pushMessage(final String message) {
        for (final Queue<String> queue : clientsQueues.values()) {
            queue.add(message);
        }
    }
}
