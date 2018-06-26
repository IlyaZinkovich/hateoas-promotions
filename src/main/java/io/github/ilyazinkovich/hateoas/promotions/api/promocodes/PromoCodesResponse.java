package io.github.ilyazinkovich.hateoas.promotions.api.promocodes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeResource;
import java.util.Set;

public class PromoCodesResponse {

  private final Set<? extends PromoCodeResource> promoCodes;

  PromoCodesResponse(final Set<? extends PromoCodeResource> promoCodes) {
    this.promoCodes = promoCodes;
  }

  public ObjectNode toJson(final ObjectMapper mapper) {
    final ObjectNode root = mapper.createObjectNode();
    final ArrayNode promoCodesArray = root.putArray("promoCodes");
    promoCodes.stream().map(promoCode -> {
      final ObjectNode promoCodeJson = mapper.createObjectNode();
      promoCodeJson.put("_link", promoCode.uri);
      return promoCodeJson;
    }).forEach(promoCodesArray::add);
    return root;
  }
}
