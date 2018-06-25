package io.github.ilyazinkovich.hateoas.promotions.api;

import static java.util.stream.Collectors.toSet;
import static ratpack.jackson.Jackson.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.CombinedPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.Query;
import io.github.ilyazinkovich.hateoas.promotions.domain.Region;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.infrastructure.InMemoryPersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.infrastructure.InMemoryRegionalPromoCodes;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import ratpack.func.Action;
import ratpack.handling.Chain;
import ratpack.handling.Handler;
import ratpack.server.RatpackServer;
import ratpack.util.MultiValueMap;

public class PromoCodesApi {

  public static void main(String[] args) throws Exception {
    final PersonalPromoCodes personalPromoCodes =
        new InMemoryPersonalPromoCodes(new ConcurrentHashMap<>());
    final RegionalPromoCodes regionalPromoCodes =
        new InMemoryRegionalPromoCodes(new ConcurrentHashMap<>());
    final CombinedPromoCodes combinedPromoCodes =
        new CombinedPromoCodes(Stream.of(personalPromoCodes, regionalPromoCodes).collect(toSet()));
    final ObjectMapper mapper = new ObjectMapper();
    final Action<Chain> actionChain = chain -> chain
        .get(index())
        .get("promocodes", queryPromoCodes(combinedPromoCodes, mapper));
    RatpackServer.start(server -> server.handlers(actionChain));
  }

  private static Handler index() {
    return ctx -> ctx.render("Welcome to PromoCodes API!!!");
  }

  private static Handler queryPromoCodes(final CombinedPromoCodes combinedPromoCodes,
      final ObjectMapper mapper) {
    return ctx -> {
      final MultiValueMap<String, String> queryParams = ctx.getRequest().getQueryParams();
      final Optional<ClientId> clientId = queryParams.getAll("clientId").stream().findFirst()
          .map(ClientId::new);
      final Optional<Region> region = queryParams.getAll("region").stream().findFirst()
          .map(Region::new);
      final Set<? extends PromoCode> promoCodes = combinedPromoCodes
          .query(new Query().withClientId(clientId).withRegion(region));
      final ObjectNode response = new PromoCodesResponse(promoCodes).toJson(mapper);
      ctx.render(json(response));
    };
  }
}
