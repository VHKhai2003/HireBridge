package com.vhkhai.mappers;

import com.vhkhai.aggrerates.account.Account;
import com.vhkhai.entities.account.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(AccountEntity accountEntity);
    AccountEntity toAccountEntity(Account account);
}
