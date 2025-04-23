package com.springboot.bus2tangticket.service.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.UserBook;
import com.springboot.bus2tangticket.repository.UserBookRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBookServiceImpl implements UserBookService{
    @Autowired
    private UserBookRepo userBookRepo;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE userbook AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public UserBookServiceImpl(UserBookRepo userBookRepo) {
        this.userBookRepo = userBookRepo;
    }

    @Autowired
    public void setUserBookRepo(UserBookRepo userBookRepo) {
        this.userBookRepo = userBookRepo;
    }

    @Override
    @Transactional
    public BaseResponse<UserBook> createUserBook(UserBook userBook) {
        resetAutoIncrement();

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo Userbook thành công!", userBookRepo.save(userBook));
    }

    @Override
    public BaseResponse<UserBook> getUserBook(int idUserBook) {
        UserBook userBook = userBookRepo.findById(idUserBook).orElse(null);

        if(userBook == null)
            return new BaseResponse<>(ResponseStatus.SUCCESS, "Không tìm thấy userbook có id: " + idUserBook, null);

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tìm thấy userbook có id: " + idUserBook, userBook);
    }

    @Override
    public BaseResponse<List<UserBook>> getAllUserBook() {
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy danh sách userbook thành công!", userBookRepo.findAll());
    }
}
