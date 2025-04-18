package com.springboot.bus2tangticket.service;

import com.springboot.bus2tangticket.dto.request.InformationRequestDTO;
import com.springboot.bus2tangticket.model.Information;
import com.springboot.bus2tangticket.repository.InformationRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationServiceImpl implements InformationService{

    @Autowired
    private InformationRepo informationRepo;

    @Autowired
    private ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    public InformationServiceImpl(InformationRepo informationRepo) {
        this.informationRepo = informationRepo;
    }

    @Autowired
    public void setInformationRepo(InformationRepo informationRepo) {
        this.informationRepo = informationRepo;
    }

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE information AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    //CREATE
    @Transactional
    @Override
    public Information createInfo(InformationRequestDTO dto) {
        resetAutoIncrement();
        Information info = this.modelMapper.map(dto, Information.class);
        return informationRepo.save(info);
    }

    //READ
    @Override
    public List<Information> getInfos() {
        return informationRepo.findAll();
    }

    @Override
    public Information getInfo(int idInfo) {
        return informationRepo.findById(idInfo).orElse(null);
    }

    //UPDATE
    @Transactional
    @Override
    public Information updateInfo(int infoId, InformationRequestDTO dto) {
        Information existingInfo = informationRepo.findById(infoId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin với ID: " + infoId));

        // Kiểm tra CIC đã tồn tại (và khác cái hiện tại)
        if (!existingInfo.getCic().equals(dto.getCic()) &&
                informationRepo.existsByCic(dto.getCic())) {
            throw new RuntimeException("CIC đã tồn tại");
        }

        // Kiểm tra email đã tồn tại
        if (!existingInfo.getEmail().equals(dto.getEmail()) &&
                informationRepo.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        // Kiểm tra số điện thoại đã tồn tại
        if (!existingInfo.getPhoneNumber().equals(dto.getPhoneNumber()) &&
                informationRepo.existsByPhoneNumber(dto.getPhoneNumber())) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }

        // Cập nhật dữ liệu từ DTO
        existingInfo.setFirstName(dto.getFirstName());
        existingInfo.setMiddleName(dto.getMiddleName());
        existingInfo.setLastName(dto.getLastName());
        existingInfo.setDateOfBirth(dto.getDateOfBirth());
        existingInfo.setSex(dto.getSex());
        existingInfo.setPermanentAddress(dto.getPermanentAddress());
        existingInfo.setCic(dto.getCic());
        existingInfo.setPhoneNumber(dto.getPhoneNumber());
        existingInfo.setEmail(dto.getEmail());

        // `updateAt` sẽ tự cập nhật qua @PreUpdate
        return informationRepo.save(existingInfo);
    }

    //DELETE
    @Transactional
    @Override
    public void deleteInfo(int infoId) {
        Information existingInfo = informationRepo.findById(infoId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin với ID: " + infoId));

        resetAutoIncrement();
        informationRepo.deleteById(existingInfo.getIdInfo());
    }

    //OTHER

}
