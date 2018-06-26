package io.github.ilyazinkovich.hateoas.promotions.api.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;

public class LogInSuccess {

  private final ClientId clientId;

  public LogInSuccess(final ClientId clientId) {
    this.clientId = clientId;
  }

  public ObjectNode toJson(final ObjectMapper mapper) {
    final ObjectNode root = mapper.createObjectNode();
    root.put("clientId", clientId.uid);
    return root;
  }
}
