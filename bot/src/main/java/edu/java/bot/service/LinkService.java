package edu.java.bot.service;

import java.net.URI;
import java.net.URL;
import java.util.Set;

public interface LinkService {

    Set<URI> gets(Long id);

    void save(Long id, URI uri);

    void remove(Long id, URI uri);
}
