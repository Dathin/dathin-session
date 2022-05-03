package io.github.dathin.dathinsession.mapper;

import io.github.dathin.dathinsession.model.user.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    LoginResponse tokenToLoginResponse(String token);

}
