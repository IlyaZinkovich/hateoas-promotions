package io.github.ilyazinkovich.hateoas.promotions.domain;

import java.util.Optional;

public class Query {

  private Optional<ClientId> clientId;
  private Optional<Region> region;

  public Query() {
    this.clientId = Optional.empty();
    this.region = Optional.empty();
  }

  public Query withClientId(final Optional<ClientId> clientId) {
    this.clientId = clientId;
    return this;
  }

  public Optional<ClientId> clientId() {
    return clientId;
  }

  public Query withRegion(final Optional<Region> region) {
    this.region = region;
    return this;
  }

  public Optional<Region> region() {
    return region;
  }
}
