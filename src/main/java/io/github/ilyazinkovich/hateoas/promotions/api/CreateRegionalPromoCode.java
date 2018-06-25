package io.github.ilyazinkovich.hateoas.promotions.api;

import com.fasterxml.jackson.databind.JsonNode;
import io.github.ilyazinkovich.hateoas.promotions.domain.Region;
import java.util.Optional;

public class CreateRegionalPromoCode {

  final Region region;

  public CreateRegionalPromoCode(Region region) {
    this.region = region;
  }

  static Optional<CreateRegionalPromoCode> fromJson(final JsonNode jsonNode) {
    return Optional.ofNullable(jsonNode.get("region"))
        .map(JsonNode::asText)
        .map(Region::new)
        .map(CreateRegionalPromoCode::new);
  }
}
