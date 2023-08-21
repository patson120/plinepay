package com.plinepay.core.mappers;

import com.plinepay.core.dto.MobileAccountDto;
import com.plinepay.core.entities.MobileAccount;
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
public interface MobileAccountMapper {
    
    MobileAccountMapper INSTANCE = Mappers.getMapper(MobileAccountMapper.class);

    @Mappings({
            @Mapping(source = "paymentMethod.id", target = "paymentMethodId"),
            @Mapping(source = "paymentMethod.code", target = "paymentMethodCode"),
            @Mapping(source = "paymentMethod.name", target = "paymentMethodName"),
            @Mapping(source = "paymentMethod.logo", target = "paymentMethodLogo"),
            @Mapping(source = "traderAccount.id", target = "traderAccountId"),
            @Mapping(source = "traderAccount.code", target = "traderAccountCode"),
            @Mapping(source = "traderAccount.name", target = "traderAccountName"),
    })
    MobileAccountDto modelToDto(MobileAccount mobileAccount);

    List<MobileAccountDto> modelsToDtos(List<MobileAccount> mobileAccounts);

    @InheritInverseConfiguration
    MobileAccount dtoToModel(MobileAccountDto mobileAccountDto);
}
