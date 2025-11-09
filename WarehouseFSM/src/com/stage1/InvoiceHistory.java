package com.stage1;

import java.util.*;

public class InvoiceHistory implements Iterable<Invoice> {
    private final Map<String, Invoice> byNo = new LinkedHashMap<>();
    private final Map<String, List<Invoice>> byClient = new HashMap<>();

    public void add(Invoice inv) {
        byNo.put(inv.getInvoiceNo(), inv);
        byClient.computeIfAbsent(inv.getClientId(), k -> new ArrayList<>()).add(inv);
    }

    public Invoice findByNo(String invoiceNo) { return byNo.get(invoiceNo); }

    public List<Invoice> findByClientId(String clientId) {
        return Collections.unmodifiableList(
                byClient.getOrDefault(clientId, Collections.emptyList()));
    }

    @Override
    public Iterator<Invoice> iterator() { return byNo.values().iterator(); }
}
