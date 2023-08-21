package com.plinepay.core.mappers;

import com.plinepay.core.dto.TraderAccountDto;
import com.plinepay.core.entities.TraderAccount;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Mapper(componentModel = "spring")
public interface TraderAccountMapper {

    TraderAccountMapper INSTANCE = Mappers.getMapper(TraderAccountMapper.class);

    TraderAccountDto modelToDto(TraderAccount traderAccount);

    List<TraderAccountDto> modelsToDtos(List<TraderAccount> traderAccounts);

    @InheritInverseConfiguration
    TraderAccount dtoToModel(TraderAccountDto traderAccountDto);
}
