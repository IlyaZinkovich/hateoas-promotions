package io.github.ilyazinkovich.hateoas.promotions.api;

import static java.lang.String.format;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCode;
import java.util.Set;

public class PromoCodesResponse {

  private final Set<? extends PromoCode> promoCodes;

  PromoCodesResponse(final Set<? extends PromoCode> promoCodes) {
    this.promoCodes = promoCodes;
  }

  public ObjectNode toJson(final ObjectMapper mapper) {
    final ObjectNode root = mapper.createObjectNode();
    final ArrayNode promoCodesArray = root.putArray("promoCodes");
    promoCodes.stream().map(promoCode -> {
      final ObjectNode promoCodeJson = mapper.createObjectNode();
      final String link = format("/promocodes/%s/%s", promoCode.type(), promoCode.id());
      promoCodeJson.put("_link", link);
      return promoCodeJson;
    }).forEach(promoCodesArray::add);
    return root;
  }
}
