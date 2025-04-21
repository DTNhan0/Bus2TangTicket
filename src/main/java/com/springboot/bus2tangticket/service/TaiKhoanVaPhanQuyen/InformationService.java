package com.springboot.bus2tangticket.service.TaiKhoanVaPhanQuyen;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Information;

import java.util.List;

public interface InformationService {
    BaseResponse<Information> createInfo(Information information);
    BaseResponse<List<Information>> getAllInfo();
    BaseResponse<Information> getInfo(int idInfo);
    BaseResponse<Information> updateInfo(int idInfo, Information information);
    BaseResponse<Information> deleteInfo(int idInfo);
}
