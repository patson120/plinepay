package com.plinepay.core.mappers;

import com.plinepay.core.dto.TransferDto;
import com.plinepay.core.entities.Transfer;
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
public interface TransferMapper {
    
    TransferMapper INSTANCE = Mappers.getMapper(TransferMapper.class);

    @Mappings({
            @Mapping(source = "application.id", target = "applicationId"),
            @Mapping(source = "application.name", target = "applicationName"),
            @Mapping(source = "transmitter.id", target = "transmitterId"),
            @Mapping(source = "transmitter.code", target = "transmitterCode"),
            @Mapping(source = "transmitter.name", target = "transmitterName"),
            @Mapping(source = "transmitter.id", target = "recipientId"),
            @Mapping(source = "transmitter.code", target = "recipientCode"),
            @Mapping(source = "transmitter.name", target = "recipientName"),
    })
    TransferDto modelToDto(Transfer transfer);

    List<TransferDto> modelsToDtos(List<Transfer> transfers);

    @InheritInverseConfiguration
    Transfer dtoToModel(TransferDto transferDto);
}
