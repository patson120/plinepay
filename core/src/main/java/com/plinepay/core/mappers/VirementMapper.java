package com.plinepay.core.mappers;

import com.plinepay.core.dto.VirementDto;
import com.plinepay.core.entities.Virement;
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
public interface VirementMapper {
    
    VirementMapper INSTANCE = Mappers.getMapper(VirementMapper.class);

    @Mappings({
            @Mapping(source = "application.id", target = "applicationId"),
            @Mapping(source = "application.name", target = "applicationName"),
    })
    VirementDto modelToDto(Virement virement);

    List<VirementDto> modelsToDtos(List<Virement> virements);

    @InheritInverseConfiguration
    Virement dtoToModel(VirementDto virementDto);
}
