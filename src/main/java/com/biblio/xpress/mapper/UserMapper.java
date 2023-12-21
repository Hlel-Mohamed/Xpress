package com.biblio.xpress.mapper;

import com.biblio.xpress.dto.UserDTO;
import com.biblio.xpress.entity.UserEntity;
import com.biblio.xpress.enums.Role;
import com.biblio.xpress.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final CardRepository cardRepository;

    @Autowired
    public UserMapper(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public UserDTO convertToDTO(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userEntity.getId());
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setFirstName(userEntity.getFirstName());
        userDTO.setLastName(userEntity.getLastName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setAddress(userEntity.getAddress());
        userDTO.setRole(userEntity.getRole().name());
        if (userEntity.getCard() != null) userDTO.setCardId(userEntity.getCard().getId());
        return userDTO;
    }

    public UserEntity convertToEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userDTO.getId());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setAddress(userDTO.getAddress());
        userEntity.setRole(Role.valueOf(userDTO.getRole()));
        userEntity.setCard(cardRepository.getById(userDTO.getCardId()));
        return userEntity;
    }

}
