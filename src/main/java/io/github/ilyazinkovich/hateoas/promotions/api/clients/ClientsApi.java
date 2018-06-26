package io.github.ilyazinkovich.hateoas.promotions.api.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ilyazinkovich.hateoas.promotions.domain.Clients;
import ratpack.func.Action;
import ratpack.handling.Chain;

public class ClientsApi implements Action<Chain> {

  private final Clients clients;
  private final ObjectMapper mapper;

  public ClientsApi(final Clients clients, final ObjectMapper mapper) {
    this.clients = clients;
    this.mapper = mapper;
  }

  @Override
  public void execute(final Chain chain) {
    chain.post("login", ctx -> new LogInHandler(clients, mapper));
  }
}
