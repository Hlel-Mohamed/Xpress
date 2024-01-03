package com.biblio.xpress.controller;

import com.biblio.xpress.entity.Card;
import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.service.CardService;
import com.biblio.xpress.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class CardController {
    private final UserServiceImpl userService;
    private final CardService cardService;
    @Autowired
    public CardController(UserServiceImpl userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }
    //Card View
    @GetMapping("/my-cards/")
    public String myCards() {
        return "mycard";
    }
    //Get My Card request
    @GetMapping("/my-card/")
    public ResponseEntity<Card> getMyCard(Authentication authentication) {

        Optional<UserEntity> user = userService.findUserByUsername(authentication.getName());
        System.out.println(user.toString());
        if (user.isPresent()) {
            Card card = cardService.getMyCard(user.get());
            if (card!=null) {
                System.out.println(card.isValid());
                return ResponseEntity.ok(card);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return null;
    }

}
