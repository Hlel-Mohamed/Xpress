package com.biblio.xpress.service;

import com.biblio.xpress.entity.Card;
import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {
    private final CardRepository cardRepository;
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    public Card AssignCard(Card card){
         return cardRepository.save(card);
    }

    public Card getMyCard(UserEntity userEntity) {
        return cardRepository.getByUser(userEntity);
    }
}
