package com.ghtk.onlinebiddingproject.domain.model.auction;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuctionStatusTest {

    @Test
    void shouldTransitionDraftToPending() {
        assertTrue(AuctionStatus.DRAFT.canTransitionTo(AuctionStatus.PENDING));
    }

    @Test
    void shouldTransitionDraftToCanceled() {
        assertTrue(AuctionStatus.DRAFT.canTransitionTo(AuctionStatus.CANCELED));
    }

    @Test
    void shouldNotTransitionDraftToQueued() {
        assertFalse(AuctionStatus.DRAFT.canTransitionTo(AuctionStatus.QUEUED));
    }

    @Test
    void shouldTransitionPendingToQueued() {
        assertTrue(AuctionStatus.PENDING.canTransitionTo(AuctionStatus.QUEUED));
    }

    @Test
    void shouldTransitionPendingToCanceled() {
        assertTrue(AuctionStatus.PENDING.canTransitionTo(AuctionStatus.CANCELED));
    }

    @Test
    void shouldTransitionQueuedToOpening() {
        assertTrue(AuctionStatus.QUEUED.canTransitionTo(AuctionStatus.OPENING));
    }

    @Test
    void shouldTransitionOpeningToEnded() {
        assertTrue(AuctionStatus.OPENING.canTransitionTo(AuctionStatus.ENDED));
    }

    @Test
    void shouldTransitionOpeningToCanceled() {
        assertTrue(AuctionStatus.OPENING.canTransitionTo(AuctionStatus.CANCELED));
    }

    @Test
    void shouldNotTransitionEndedToAnything() {
        assertFalse(AuctionStatus.ENDED.canTransitionTo(AuctionStatus.OPENING));
        assertFalse(AuctionStatus.ENDED.canTransitionTo(AuctionStatus.CANCELED));
    }

    @Test
    void shouldNotTransitionCanceledToAnything() {
        assertFalse(AuctionStatus.CANCELED.canTransitionTo(AuctionStatus.DRAFT));
        assertFalse(AuctionStatus.CANCELED.canTransitionTo(AuctionStatus.OPENING));
    }

    @Test
    void shouldBeTerminalWhenEndedOrCanceled() {
        assertTrue(AuctionStatus.ENDED.isTerminal());
        assertTrue(AuctionStatus.CANCELED.isTerminal());
        assertFalse(AuctionStatus.DRAFT.isTerminal());
        assertFalse(AuctionStatus.OPENING.isTerminal());
    }

    @Test
    void shouldBeBiddableOnlyWhenOpening() {
        assertTrue(AuctionStatus.OPENING.canBeBiddedOn());
        assertFalse(AuctionStatus.DRAFT.canBeBiddedOn());
        assertFalse(AuctionStatus.PENDING.canBeBiddedOn());
        assertFalse(AuctionStatus.ENDED.canBeBiddedOn());
    }
}
