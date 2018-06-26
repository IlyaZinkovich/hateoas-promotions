package io.github.ilyazinkovich.hateoas.promotions.domain;

import java.util.Optional;

public interface Clients {

  void store(final ClientId id, final Client client);

  Optional<Client> query(final ClientId id);
}
