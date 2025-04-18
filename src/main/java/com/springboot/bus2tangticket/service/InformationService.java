package com.springboot.bus2tangticket.service;

import com.springboot.bus2tangticket.dto.request.InformationRequestDTO;
import com.springboot.bus2tangticket.model.Information;

import java.util.List;

public interface InformationService {
    Information createInfo(InformationRequestDTO dto);
    List<Information> getInfos();
    Information getInfo(int idInfo);
    Information updateInfo(int id, InformationRequestDTO dto);
    void deleteInfo(int infoId);
}
