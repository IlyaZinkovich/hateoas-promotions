package io.github.ilyazinkovich.hateoas.promotions.api.clients;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;

public class LogIn {

  final String name;

  public LogIn(final String name) {
    this.name = name;
  }

  static Optional<LogIn> fromJson(final JsonNode jsonNode) {
    return Optional.ofNullable(jsonNode.get("name"))
        .map(JsonNode::asText)
        .map(LogIn::new);
  }
}
