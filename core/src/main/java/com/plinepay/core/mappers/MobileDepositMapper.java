package com.plinepay.core.mappers;

import com.plinepay.core.dto.MobileDepositDto;
import com.plinepay.core.entities.MobileDeposit;
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
public interface MobileDepositMapper {
    
    MobileDepositMapper INSTANCE = Mappers.getMapper(MobileDepositMapper.class);

    @Mappings({
            @Mapping(source = "paymentMethod.id", target = "paymentMethodId"),
            @Mapping(source = "paymentMethod.code", target = "paymentMethodCode"),
            @Mapping(source = "paymentMethod.name", target = "paymentMethodName"),
            @Mapping(source = "application.id", target = "applicationId"),
            @Mapping(source = "application.name", target = "applicationName"),
    })
    MobileDepositDto modelToDto(MobileDeposit mobileDeposit);

    List<MobileDepositDto> modelsToDtos(List<MobileDeposit> mobileDeposits);

    @InheritInverseConfiguration
    MobileDeposit dtoToModel(MobileDepositDto mobileDepositDto);
}
