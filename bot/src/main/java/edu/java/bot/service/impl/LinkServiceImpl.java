package edu.java.bot.service.impl;

import edu.java.bot.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Service
public class LinkServiceImpl implements LinkService {
    private final HashMap<Long , Set<URI>> links;

    public LinkServiceImpl() {
        this.links = new HashMap<>();
    }

    @Override
    public Set<URI> gets(Long id) {
        return links.get(id);
    }

    @Override
    public void save(Long id, URI uri) {
        links.computeIfAbsent(id, k -> new HashSet<>()).add(uri);
    }

    @Override
    public void remove(Long id, URI uri) {
        links.computeIfPresent(id, (k, v) -> {
            v.remove(uri);
            return v;
        });
    }
}
