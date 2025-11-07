package com.stage1;

import java.util.*;

public class ClientList implements Iterable<Client> {
    private final Map<String, Client> byId = new LinkedHashMap<>();

    public void insert(Client c) {
        byId.put(c.getId(), c);
    }

    public Client findById(String id) {
        return byId.get(id);
    }

    @Override
    public Iterator<Client> iterator() {
        return byId.values().iterator();
    }
}
