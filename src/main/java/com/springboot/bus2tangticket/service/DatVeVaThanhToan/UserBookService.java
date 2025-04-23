package com.springboot.bus2tangticket.service.DatVeVaThanhToan;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.DatVeVaThanhToan.UserBook;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface UserBookService {
    BaseResponse<UserBook> createUserBook(UserBook userBook);
    BaseResponse<UserBook> getUserBook(int idUserBook);
    BaseResponse<List<UserBook>> getAllUserBook();
}
