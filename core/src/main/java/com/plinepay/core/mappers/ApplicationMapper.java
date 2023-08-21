package com.plinepay.core.mappers;

import com.plinepay.core.dto.ApplicationDto;
import com.plinepay.core.entities.Application;
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
public interface ApplicationMapper {
    
    ApplicationMapper INSTANCE = Mappers.getMapper(ApplicationMapper.class);

    @Mappings({
            @Mapping(source = "traderAccount.id", target = "traderAccountId"),
            @Mapping(source = "traderAccount.code", target = "traderAccountCode"),
            @Mapping(source = "traderAccount.name", target = "traderAccountName"),
    })
    ApplicationDto modelToDto(Application application);

    List<ApplicationDto> modelsToDtos(List<Application> applications);

    @InheritInverseConfiguration
    Application dtoToModel(ApplicationDto applicationDto);
}
