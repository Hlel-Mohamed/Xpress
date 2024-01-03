package com.biblio.xpress.repository;

import com.biblio.xpress.entity.Card;
import com.biblio.xpress.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    Card getByUser(UserEntity userEntity);
}
