package com.Kuba2412.medicalclinic.model.mapper;

import com.Kuba2412.medicalclinic.model.User;
import com.Kuba2412.medicalclinic.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {


    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
