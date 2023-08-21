package com.plinepay.authservice.services;

import java.util.List;

import com.plinepay.authservice.otp.OptData;

public interface OptDataService {

    OptData saveOptData(OptData otp);
    
    List<OptData> findAllOptData();

    OptData findOptDataByEmail(String email);

    String deleteOptData(String email);
}
