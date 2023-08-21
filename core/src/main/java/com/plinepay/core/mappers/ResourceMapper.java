/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.mappers;

import com.plinepay.core.dto.ResourceDto;
import com.plinepay.core.entities.Resource;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import java.util.List;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Mapper(componentModel = "spring")
public interface ResourceMapper {

    ResourceMapper INSTANCE = Mappers.getMapper(ResourceMapper.class);

    ResourceDto modelToDto(Resource resource);

    List<ResourceDto> modelsToDtos(List<Resource> resources);

    @InheritInverseConfiguration
    Resource dtoToModel(ResourceDto resourceDto);
}
