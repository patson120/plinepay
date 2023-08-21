package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.SettingDto;

import java.util.List;
import java.util.UUID;

/**
 * @author <a href="mailto:sylvainonguene@gmail.com">Denis ETABA</a>
 */
public interface ISettingService {

    public SettingDto findSettingById(UUID id);

    public Long getNumber();

    public List<SettingDto> retrieveAll();

    public List<SettingDto> retrieveByLimit(int firstResult, int maxResults);

    public SettingDto create(SettingDto settingDto);

    public SettingDto update(SettingDto settingDto);

    public SettingDto delete(UUID settingId);
    
}
