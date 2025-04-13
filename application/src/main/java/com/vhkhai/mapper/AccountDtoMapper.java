package com.vhkhai.mapper;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.dto.account.AccountRequestDto;
import com.vhkhai.dto.account.AccountResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountDtoMapper {
    Account toAccount(AccountRequestDto accountRequestDto);

    AccountResponseDto toAccountResponseDto(Account account);
}
