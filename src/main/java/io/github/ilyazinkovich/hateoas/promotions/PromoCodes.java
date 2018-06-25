package io.github.ilyazinkovich.hateoas.promotions;

import java.util.Set;

public interface PromoCodes {

  Set<? extends PromoCode> query(final Query query);
}
