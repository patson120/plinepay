/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.mappers;

import com.plinepay.core.dto.UserDto;
import com.plinepay.core.entities.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
        @Mapping(source = "traderAccount.id", target = "traderAccountId"),
        @Mapping(source = "traderAccount.code", target = "traderAccountCode"),
        @Mapping(source = "traderAccount.name", target = "traderAccountName"),
    })
    UserDto modelToDto(User user);

    List<UserDto> modelsToDtos(List<User> users);

    @InheritInverseConfiguration
    User dtoToModel(UserDto userDto);
}
