package com.plinepay.authservice.repository;

import java.util.List;

import com.plinepay.authservice.otp.OptData;


public interface OptDataDao {

    public OptData saveOptData(OptData otp);

    public List<OptData> findAllOptData();

    public OptData findOptDataByEmail(String email);

    public String deleteOptData(String email);

}
