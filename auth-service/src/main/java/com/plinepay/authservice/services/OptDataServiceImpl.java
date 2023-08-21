package com.plinepay.authservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plinepay.authservice.otp.OptData;
import com.plinepay.authservice.repository.OptDataDao;

@Service
public class OptDataServiceImpl implements OptDataService {

    @Autowired
    private OptDataDao optDataDao;

    @Override
    public OptData saveOptData(OptData otp) {
        return optDataDao.saveOptData(otp);
    }

    @Override
    public List<OptData> findAllOptData() {
        return optDataDao.findAllOptData();
    }

    @Override
    public OptData findOptDataByEmail(String email) {
        return optDataDao.findOptDataByEmail(email);
    }

    @Override
    public String deleteOptData(String email) {
        return optDataDao.deleteOptData(email);
    }
    
}
