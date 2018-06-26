package io.github.ilyazinkovich.hateoas.promotions.api.clients;

import static ratpack.jackson.Jackson.json;
import static ratpack.jackson.Jackson.jsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ilyazinkovich.hateoas.promotions.domain.Client;
import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.Clients;
import ratpack.handling.Context;
import ratpack.handling.Handler;

public class LogInHandler implements Handler {

  private final Clients clients;
  private final ObjectMapper mapper;

  public LogInHandler(final Clients clients, final ObjectMapper mapper) {
    this.clients = clients;
    this.mapper = mapper;
  }

  @Override
  public void handle(Context ctx) {
    ctx.parse(jsonNode())
        .map(LogIn::fromJson)
        .map(optionalLogIn -> optionalLogIn.map(logIn -> {
          final ClientId clientId = ClientId.next();
          final Client client = new Client(clientId, logIn.name);
          clients.store(clientId, client);
          return clientId;
        }))
        .then(optionalClientId -> {
          optionalClientId.map(clientId -> (Runnable) () -> {
            final LogInSuccess response = new LogInSuccess(clientId);
            ctx.render(json(response.toJson(mapper)));
          }).orElse(() -> {
            ctx.getResponse().status(400).send();
          }).run();
        });
  }
}
