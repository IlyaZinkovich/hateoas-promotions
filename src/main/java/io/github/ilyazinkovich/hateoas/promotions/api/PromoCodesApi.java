package io.github.ilyazinkovich.hateoas.promotions.api;

import static java.util.stream.Collectors.toSet;
import static ratpack.http.Status.OK;
import static ratpack.jackson.Jackson.json;
import static ratpack.jackson.Jackson.jsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.github.ilyazinkovich.hateoas.promotions.domain.ClientId;
import io.github.ilyazinkovich.hateoas.promotions.domain.CombinedPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCode;
import io.github.ilyazinkovich.hateoas.promotions.domain.PromoCodeId;
import io.github.ilyazinkovich.hateoas.promotions.domain.Query;
import io.github.ilyazinkovich.hateoas.promotions.domain.Region;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCode;
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
        .post("promocodes/personal", createPersonalPromoCode(personalPromoCodes))
        .post("promocodes/regional", createRegionalPromoCode(regionalPromoCodes))
        .get("promocodes", queryPromoCodes(combinedPromoCodes, mapper));
    RatpackServer.start(server -> server.handlers(actionChain));
  }

  private static Handler index() {
    return ctx -> ctx.render("Welcome to PromoCodes API!!!");
  }

  private static Handler createPersonalPromoCode(final PersonalPromoCodes personalPromoCodes) {
    return ctx -> ctx.parse(jsonNode()).map(CreatePersonalPromoCode::fromJson)
        .next(handleCreatePersonalPromoCodeCommand(personalPromoCodes))
        .then(command -> ctx.getResponse().status(OK).send());
  }

  private static Action<Optional<CreatePersonalPromoCode>> handleCreatePersonalPromoCodeCommand(
      final PersonalPromoCodes personalPromoCodes) {
    return optionalCommand -> optionalCommand.ifPresent(command -> {
      final PersonalPromoCode promoCode = new PersonalPromoCode(PromoCodeId.next());
      personalPromoCodes.store(command.clientId, promoCode);
    });
  }

  private static Handler createRegionalPromoCode(final RegionalPromoCodes regionalPromoCodes) {
    return ctx -> ctx.parse(jsonNode()).map(CreateRegionalPromoCode::fromJson)
        .next(handleCreateRegionalPromoCodeCommand(regionalPromoCodes))
        .then(command -> ctx.getResponse().status(OK).send());
  }

  private static Action<Optional<CreateRegionalPromoCode>> handleCreateRegionalPromoCodeCommand(
      final RegionalPromoCodes regionalPromoCodes) {
    return optionalCommand -> optionalCommand.ifPresent(command -> {
      final RegionalPromoCode promoCode = new RegionalPromoCode(PromoCodeId.next());
      regionalPromoCodes.store(command.region, promoCode);
    });
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
