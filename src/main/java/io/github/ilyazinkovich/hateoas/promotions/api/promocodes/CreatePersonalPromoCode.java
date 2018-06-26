package io.github.ilyazinkovich.hateoas.promotions.api.promocodes;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import java.util.Optional;

public class CreatePersonalPromoCode {

  final ClientId clientId;

  public CreatePersonalPromoCode(final ClientId clientId) {
    this.clientId = clientId;
  }

  static Optional<CreatePersonalPromoCode> fromJson(final JsonNode jsonNode) {
    return Optional.ofNullable(jsonNode.get("clientId"))
        .map(JsonNode::asText)
        .map(ClientId::new)
        .map(CreatePersonalPromoCode::new);
  }
}
