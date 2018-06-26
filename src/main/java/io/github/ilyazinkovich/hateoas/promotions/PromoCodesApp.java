package io.github.ilyazinkovich.hateoas.promotions;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.ilyazinkovich.hateoas.promotions.api.promocodes.PromoCodesApi;
import io.github.ilyazinkovich.hateoas.promotions.domain.PersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.domain.RegionalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.infrastructure.InMemoryPersonalPromoCodes;
import io.github.ilyazinkovich.hateoas.promotions.infrastructure.InMemoryRegionalPromoCodes;
import ratpack.server.RatpackServer;

import java.util.concurrent.ConcurrentHashMap;

public class PromoCodesApp {

    public static void main(String[] args) throws Exception {
        final PersonalPromoCodes personalPromoCodes = new InMemoryPersonalPromoCodes(new ConcurrentHashMap<>());
        final RegionalPromoCodes regionalPromoCodes = new InMemoryRegionalPromoCodes(new ConcurrentHashMap<>());
        final ObjectMapper mapper = new ObjectMapper();
        final PromoCodesApi promoCodesApi = new PromoCodesApi(personalPromoCodes, regionalPromoCodes, mapper);
        RatpackServer.start(server -> server.handlers(promoCodesApi));
    }
}