package io.github.ilyazinkovich.hateoas.promotions.api;

import static ratpack.jackson.Jackson.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.CombinedPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.Query;
import io.github.ilyazinkovich.hateoas.promotions.domain.Region;
import java.util.Optional;
import java.util.Set;
import ratpack.handling.Context;
import ratpack.handling.Handler;
import ratpack.util.MultiValueMap;

public class PromoCodesQueryHandler implements Handler {

  private final CombinedPromoCodes combinedPromoCodes;
  private final ObjectMapper mapper;

  public PromoCodesQueryHandler(final CombinedPromoCodes combinedPromoCodes,
      final ObjectMapper mapper) {
    this.combinedPromoCodes = combinedPromoCodes;
    this.mapper = mapper;
  }

  @Override
  public void handle(final Context ctx) {
    final MultiValueMap<String, String> queryParams = ctx.getRequest().getQueryParams();
    final Optional<ClientId> clientId = queryParams.getAll("clientId").stream().findFirst()
        .map(ClientId::new);
    final Optional<Region> region = queryParams.getAll("region").stream().findFirst()
        .map(Region::new);
    final Set<? extends PromoCode> promoCodes = combinedPromoCodes
        .query(new Query().withClientId(clientId).withRegion(region));
    final ObjectNode response = new PromoCodesResponse(promoCodes).toJson(mapper);
    ctx.render(json(response));
  }
}
