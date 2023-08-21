/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.mappers;

import com.plinepay.core.dto.PrivilegeDto;
import com.plinepay.core.entities.Privilege;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Mapper(componentModel = "spring")
public interface PrivilegeMapper {

    PrivilegeMapper INSTANCE = Mappers.getMapper(PrivilegeMapper.class);

    @Mappings({
        @Mapping(source = "resource.id", target = "resourceId"),
        @Mapping(source = "resource.code", target = "resourceCode"),
        @Mapping(source = "resource.libelle", target = "resourceLibelle"),
        @Mapping(source = "role.id", target = "roleId"),
        @Mapping(source = "role.name", target = "roleName")
    })
    PrivilegeDto modelToDto(Privilege privilege);

    List<PrivilegeDto> modelsToDtos(List<Privilege> privileges);

    @InheritInverseConfiguration
    Privilege dtoToModel(PrivilegeDto privilegeDto);

}
