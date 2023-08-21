/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.mappers;

import com.plinepay.core.dto.RoleDto;
import com.plinepay.core.entities.Role;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    RoleDto modelToDto(Role role);

    List<RoleDto> modelsToDtos(List<Role> role);

    @InheritInverseConfiguration
    Role dtoToModel(RoleDto roleDto);

}
