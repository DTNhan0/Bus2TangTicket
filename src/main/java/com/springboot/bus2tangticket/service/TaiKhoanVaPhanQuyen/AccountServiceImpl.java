package com.springboot.bus2tangticket.service.TaiKhoanVaPhanQuyen;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;
import com.springboot.bus2tangticket.repository.AccountRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private InformationServiceImpl informationService;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Autowired
    public void setAccountRepo(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE account AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    //CREATE
    @Transactional
    @Override
    public BaseResponse<Account> createAcc(int idInfo, Account account) {
        resetAutoIncrement();
        Account acc = new Account(account);
        acc.setInformation(informationService.getInfo(idInfo).getData());
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo account thành công!", accountRepo.save(acc));
    }

    //READ
    @Override
    public BaseResponse<List<Account>> getAllAcc() {
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy danh sách account thành công!", accountRepo.findAll());
    }

    @Override
    public BaseResponse<Account> getAcc(int idAcc) {
        Account account = accountRepo.findById(idAcc).orElse(null);

        if(account == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy account có id: " + idAcc, null);

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tìm thấy account có id: " + idAcc, account);
    }

    //UPDATE
    @Transactional
    @Override
    public BaseResponse<Account> updateAcc(int idAcc, Account account) {
        BaseResponse<Account> baseResponse = getAcc(idAcc);

        if (baseResponse.getData() == null)
            return new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null);

        Account existingAcc = baseResponse.getData();

        // Kiểm tra AccountName đã tồn tại (và khác cái hiện tại)
        if (!existingAcc.getAccountName().equals(account.getAccountName()) &&
                accountRepo.existsByAccountName(account.getAccountName())) {
            throw new RuntimeException("AccountName đã tồn tại");
        }

        // Cập nhật dữ liệu từ DTO
        existingAcc.setAccountName(account.getAccountName());
        existingAcc.setPassword(account.getPassword());

        // `updateAt` sẽ tự cập nhật qua @PreUpdate
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã cập nhật account có id: " + idAcc, existingAcc);
    }

    //DELETE
    @Transactional
    @Override
    public BaseResponse<Account> deleteAcc(int idAcc) {
        resetAutoIncrement();
        Account account = getAcc(idAcc).getData();

        if (account == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy tài khoản có id: " + idAcc, null);

        try {
            accountRepo.deleteById(idAcc);
        }catch (DataIntegrityViolationException e){
            return new BaseResponse<>(ResponseStatus.FAILED,"Ràng buộc thực thể information không thể xóa!", account);
        }

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã xóa account có id: " + idAcc, account);
    }
}
