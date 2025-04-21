package com.springboot.bus2tangticket.service.TaiKhoanVaPhanQuyen;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;

import java.util.List;

public interface AccountService {
    BaseResponse<Account> createAcc(int idInfo, Account account);
    BaseResponse<List<Account>> getAllAcc();
    BaseResponse<Account> getAcc(int idAcc);
    BaseResponse<Account> updateAcc(int idAcc, Account account);
    BaseResponse<Account> deleteAcc(int idAcc);
}
