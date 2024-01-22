package com.krug.teste.dto;

import com.krug.teste.services.validation.UserInsertValid;
import com.krug.teste.services.validation.UserUpdateValid;

import java.io.Serial;

@UserUpdateValid
public class UserUpdateDTO extends UserDTO{
    @Serial
    private static final long serialVersionUID = 1L;
}
