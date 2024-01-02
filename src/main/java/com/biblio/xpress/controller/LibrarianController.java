package com.biblio.xpress.controller;

import com.biblio.xpress.entity.Card;
import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.enums.Role;
import com.biblio.xpress.service.CardService;
import com.biblio.xpress.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
public class LibrarianController {
    @Autowired
    private final UserServiceImpl userService;
    @Autowired
    private final CardService cardService;
    public LibrarianController(UserServiceImpl userService, CardService cardService) {
        this.userService = userService;
        this.cardService = cardService;
    }
    //Get all members
    @GetMapping("/members")
    public String showAllMembers(Model model) {

            List<UserEntity> members = userService.findUserByRole(Role.MEMBER);
            model.addAttribute("members", members);

            return "members";
    }
    //Assign a card to new members that is valid for a year
    @PostMapping(value="/members/assign-acard/{id}")
    public ResponseEntity<String> assignCard(@PathVariable Long id, RedirectAttributes attributes) {
        UserEntity user = userService.findUserById(id);
        Card card = new Card();
        card.setUser(user);
        card.setStartDate(Date.valueOf(LocalDate.now()));
        card.setValidUntil(Date.valueOf(LocalDate.now().plusYears(1)));
        Long cardnumber = 5105105105105100L;
        card.setCardNumber(cardnumber+id);
        if (cardService.AssignCard(card)!=null) {
            user.setCard(card);
            userService.saveUser(user);
            return ResponseEntity.ok(card.toString());
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
