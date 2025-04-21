package com.springboot.bus2tangticket.service.TaiKhoanVaPhanQuyen;

import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Information;

import java.util.List;

public interface InformationService {
    Information createInfo(Information information);
    List<Information> getInfos();
    Information getInfo(int idInfo);
    Information updateInfo(int id, Information information);
    Information deleteInfo(int infoId);
}
