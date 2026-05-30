package com.ghtk.onlinebiddingproject.domain.model.auction;

import com.ghtk.onlinebiddingproject.domain.model.shared.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {

    @Test
    void shouldCreateMoney() {
        Money money = Money.of(100.50);
        assertEquals(100.50, money.amount().doubleValue());
    }

    @Test
    void shouldCreateZeroMoney() {
        Money money = Money.zero();
        assertEquals(0, money.amount().doubleValue());
    }

    @Test
    void shouldCompareGreaterThan() {
        Money bigger = Money.of(200);
        Money smaller = Money.of(100);

        assertTrue(bigger.isGreaterThan(smaller));
        assertFalse(smaller.isGreaterThan(bigger));
    }

    @Test
    void shouldCompareGreaterThanOrEqualTo() {
        Money same = Money.of(100);
        Money bigger = Money.of(200);

        assertTrue(same.isGreaterThanOrEqualTo(Money.of(100)));
        assertTrue(bigger.isGreaterThanOrEqualTo(Money.of(100)));
        assertFalse(same.isGreaterThanOrEqualTo(Money.of(200)));
    }

    @Test
    void shouldAdd() {
        Money a = Money.of(100);
        Money b = Money.of(50);

        Money result = a.add(b);

        assertEquals(Money.of(150), result);
    }

    @Test
    void shouldMultiply() {
        Money money = Money.of(100);

        Money result = money.multiply(3);

        assertEquals(Money.of(300), result);
    }

    @Test
    void shouldUseCorrectEquality() {
        Money a = Money.of(100);
        Money b = Money.of(100);
        Money c = Money.of(200);

        assertEquals(a, b);
        assertNotEquals(a, c);
    }
}
