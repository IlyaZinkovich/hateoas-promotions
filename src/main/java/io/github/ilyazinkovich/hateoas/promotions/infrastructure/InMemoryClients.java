package io.github.ilyazinkovich.hateoas.promotions.infrastructure;

import io.github.ilyazinkovich.hateoas.promotions.domain.Client;
import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.Clients;
import java.util.Map;
import java.util.Optional;

public class InMemoryClients implements Clients {

  private final Map<ClientId, Client> clients;

  public InMemoryClients(final Map<ClientId, Client> clients) {
    this.clients = clients;
  }

  @Override
  public void store(final ClientId id, final Client client) {
    clients.put(id, client);
  }

  @Override
  public Optional<Client> query(final ClientId id) {
    return Optional.ofNullable(clients.get(id));
  }
}
