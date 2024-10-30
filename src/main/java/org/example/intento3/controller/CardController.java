package org.example.intento3.controller;


import org.example.intento3.model.*;
import org.example.intento3.service.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/regadiot/v1/cards")
public class CardController {
    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/{cardId}")
    public ResponseEntity<Card> getCardById(@PathVariable(name = "cardId") Long cardId) {
        return new ResponseEntity<Card>(cardService.getCardById(cardId), HttpStatus.OK);
    }

    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Card>> getCardByUserId(@PathVariable(name = "userId") Long userId) {
        return new ResponseEntity<List<Card>>(cardService.getCardByUserId(userId), HttpStatus.OK);
    }

    // Method: POST
    @Transactional
    @PostMapping("/{userId}")
    public ResponseEntity<Card> createCard(@PathVariable(name = "userId") Long userId, @RequestBody Card card) {
        return new ResponseEntity<Card>(cardService.createCard(userId, card), HttpStatus.CREATED);
    }

    // Method: PUT
    @Transactional
    @PutMapping("/{cardId}")
    public ResponseEntity<Card> updateCard(@PathVariable(name = "cardId") Long cardId, @RequestBody Card card) {
        card.setCardType(card.getCardType().toLowerCase());
        card.setId(cardId);
        return new ResponseEntity<Card>(cardService.updateCard(card), HttpStatus.OK);
    }

    // Method: PATCH
    @Transactional
    @PatchMapping("/cards/{cardId}/main")
    public ResponseEntity<Card> updateCardMain(@PathVariable(name = "cardId") Long cardId, @RequestBody boolean cardMain) {
        return new ResponseEntity<Card>(cardService.updateCardMain(cardId, cardMain), HttpStatus.OK);
    }

    // Method: DELETE
    @Transactional
    @DeleteMapping("/{cardId}")
    public ResponseEntity<Void> deleteCard(@PathVariable(name = "cardId") Long cardId) {
        cardService.deleteCard(cardId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}