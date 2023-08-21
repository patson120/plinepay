package com.plinepay.core.mappers;

import com.plinepay.core.dto.OnlinePaymentDto;
import com.plinepay.core.entities.OnlinePayment;
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
public interface OnlinePaymentMapper {
    
    OnlinePaymentMapper INSTANCE = Mappers.getMapper(OnlinePaymentMapper.class);

    @Mappings({
            @Mapping(source = "application.id", target = "applicationId"),
            @Mapping(source = "application.name", target = "applicationName"),
    })
    OnlinePaymentDto modelToDto(OnlinePayment onlinePayment);

    List<OnlinePaymentDto> modelsToDtos(List<OnlinePayment> onlinePayments);

    @InheritInverseConfiguration
    OnlinePayment dtoToModel(OnlinePaymentDto onlinePaymentDto);
}
