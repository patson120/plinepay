package com.plinepay.core.services.declaration;

import com.plinepay.core.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface IUserService {

    public UserDto findUserById(UUID id);

    public Long getNumber();

    public List<UserDto> retrieveAll();

    public List<UserDto> retrieveByLimit(int firstResult, int maxResults);

    public UserDto create(UserDto userDto);

    public UserDto update(UserDto userDto);

    public UserDto delete(UUID userId);

    public List<UserDto> retrieveUserUpdated(long lastDateUpdate);

    public UserDto disable(UUID userId);

    public UserDto enable(UUID userId);

    public UserDto updatePassword(UUID userId, String password);
}
