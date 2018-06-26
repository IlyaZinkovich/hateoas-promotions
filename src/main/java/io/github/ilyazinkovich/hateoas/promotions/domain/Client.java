package io.github.ilyazinkovich.hateoas.promotions.domain;

public class Client {

  private final ClientId clientId;
  private final String name;

  public Client(final ClientId clientId, final String name) {
    this.clientId = clientId;
    this.name = name;
  }
}
