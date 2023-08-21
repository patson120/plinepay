/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.mappers;

import com.plinepay.core.dto.PaymentMethodDto;
import com.plinepay.core.entities.PaymentMethod;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 *
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
@Mapper(componentModel = "spring")
public interface PaymentMethodMapper {

    PaymentMethodMapper INSTANCE = Mappers.getMapper(PaymentMethodMapper.class);

    PaymentMethodDto modelToDto(PaymentMethod paymentMethod);

    List<PaymentMethodDto> modelsToDtos(List<PaymentMethod> paymentMethod);

    @InheritInverseConfiguration
    PaymentMethod dtoToModel(PaymentMethodDto paymentMethodDto);

}
