package com.stage1;

import java.util.*;

public class ProductList implements Iterable<Product> {
    private final Map<String, Product> byId = new LinkedHashMap<>();

    public void insert(Product p) {
        byId.put(p.getId(), p);
    }

    public Product findById(String id) {
        return byId.get(id);
    }

    @Override
    public Iterator<Product> iterator() {
        return byId.values().iterator();
    }
}
