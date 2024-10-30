package org.example.intento3.service;

import org.example.intento3.model.Card;

import java.util.List;

public interface CardService {
    public abstract Card createCard(Long userId, Card card);
    public abstract Card getCardById(Long card_id);
    public abstract Card updateCard(Card card);
    public abstract void deleteCard(Long card_id);
    public abstract Card updateCardMain(Long card_id, boolean cardMain);
    public abstract List<Card> getCardByUserId(Long user_id);
}