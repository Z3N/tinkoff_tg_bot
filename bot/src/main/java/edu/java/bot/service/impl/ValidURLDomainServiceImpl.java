package edu.java.bot.service.impl;

import edu.java.bot.service.ValidURLDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class ValidURLDomainServiceImpl implements ValidURLDomainService {
    private final List<URI> domains;

    public ValidURLDomainServiceImpl() {
        ArrayList<URI> uris = new ArrayList<>();
        try {
            uris.add(new URI("https://stackoverflow.com"));
            uris.add(new URI("https://github.com"));
        } catch (URISyntaxException e) {
            log.error("Can't initialize list of target domains with following exception ", e);
        }
        this.domains = uris;
    }

    @Override
    public boolean isValidDomain(URI uri) {
        for (URI domain : domains) {
            if (isEqualDomain(uri, domain)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEqualDomain(URI lhs, URI rhs) {
        return lhs.getHost().equalsIgnoreCase(rhs.getHost());
    }
}
