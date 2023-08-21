/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plinepay.core.services.implementation;

import com.plinepay.core.components.UtilsComponent;
import com.plinepay.core.dto.SettingDto;
import com.plinepay.core.entities.Setting;
import com.plinepay.core.mappers.SettingMapper;
import com.plinepay.core.repositories.EntityManagerRepository;
import com.plinepay.core.repositories.SettingRepository;
import com.plinepay.core.services.declaration.ISettingService;
import com.plinepay.core.utils.UtilsMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 * @Project Pline Pay
 * @Since 10/08/2023 01:13
 */
@Service
public class SettingService implements ISettingService {

    @Autowired
    private EntityManagerRepository entityManagerRepository;

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private UtilsComponent utilsComponent;

    @Autowired
    private SettingMapper mapper;

    /**
     * Find Entity By Id
     *
     * @param id
     * @return
     */
    public SettingDto findSettingById(UUID id) {
        return mapper.modelToDto(settingRepository.findById(id).orElse(null));
    }

    /**
     * Return number of entities Setting
     *
     * @return
     */
    public Long getNumber() {
        return settingRepository.count();
    }

    /**
     * @return
     */
    public List<SettingDto> retrieveAll() {
        return mapper.modelsToDtos(entityManagerRepository.retrieveSetting());
    }

    /**
     * Retourne les éléments en limitant les résultats
     *
     * @param firstResult
     * @param maxResults
     * @return
     */
    public List<SettingDto> retrieveByLimit(int firstResult, int maxResults) {
        return mapper.modelsToDtos(entityManagerRepository.retrieveSettingLimited(firstResult, maxResults));
    }

    /**
     * Enregistrement -- Setting
     *
     * @param settingDto
     * @return
     */
    public SettingDto create(SettingDto settingDto) {

        Setting setting = mapper.dtoToModel(settingDto);
        UtilsMethods.createID(setting);
        UtilsMethods.loadCreationAttributes(Boolean.TRUE, setting, Boolean.TRUE);
        setting = settingRepository.save(setting);

        //String message = "Hello, modification des paramètres système";
        //utilsComponent.sendGlobalNotification(message, Boolean.FALSE);

        return mapper.modelToDto(setting);
    }

    /**
     * Modification -- Setting
     *
     * @param settingDto
     * @return
     */
    public SettingDto update(SettingDto settingDto) {

        Setting setting = mapper.dtoToModel(settingDto);
        UtilsMethods.loadCreationAttributes(Boolean.FALSE, setting, Boolean.TRUE);
        setting = settingRepository.save(setting);
        return mapper.modelToDto(setting);
    }

    /**
     * Delete Setting from database
     *
     * @param settingId
     * @return
     */
    public SettingDto delete(UUID settingId) {
        Setting setting = utilsComponent.findSettingById(settingId);
        settingRepository.delete(setting);
        return mapper.modelToDto(setting);
    }

}
