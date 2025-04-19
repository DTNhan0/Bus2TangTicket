package com.springboot.bus2tangticket.service.TaiKhoanVaPhanQuyen;

import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Information;
import com.springboot.bus2tangticket.repository.InformationRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InformationServiceImpl implements InformationService{

    @Autowired
    private InformationRepo informationRepo;

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
    public Information createInfo(Information information) {
        resetAutoIncrement();
        return informationRepo.save(information);
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
    public Information updateInfo(int infoId, Information information) {
        Information existingInfo = informationRepo.findById(infoId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy thông tin với ID: " + infoId));

        // Kiểm tra CIC đã tồn tại (và khác cái hiện tại)
        if (!existingInfo.getCic().equals(information.getCic()) &&
                informationRepo.existsByCic(information.getCic())) {
            throw new RuntimeException("CIC đã tồn tại");
        }

        // Kiểm tra email đã tồn tại
        if (!existingInfo.getEmail().equals(information.getEmail()) &&
                informationRepo.existsByEmail(information.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        // Kiểm tra số điện thoại đã tồn tại
        if (!existingInfo.getPhoneNumber().equals(information.getPhoneNumber()) &&
                informationRepo.existsByPhoneNumber(information.getPhoneNumber())) {
            throw new RuntimeException("Số điện thoại đã tồn tại");
        }

        // Cập nhật dữ liệu từ DTO
        existingInfo.setFirstName(information.getFirstName());
        existingInfo.setMiddleName(information.getMiddleName());
        existingInfo.setLastName(information.getLastName());
        existingInfo.setDateOfBirth(information.getDateOfBirth());
        existingInfo.setSex(information.getSex());
        existingInfo.setPermanentAddress(information.getPermanentAddress());
        existingInfo.setCic(information.getCic());
        existingInfo.setPhoneNumber(information.getPhoneNumber());
        existingInfo.setEmail(information.getEmail());

        // `updateAt` sẽ tự cập nhật qua @PreUpdate
        return informationRepo.save(existingInfo);
    }

    //DELETE
    @Transactional
    @Override
    public Information deleteInfo(int infoId) {
        resetAutoIncrement();
        Information information = informationRepo.findById(infoId).orElse(null);

        informationRepo.deleteById(infoId);

        return information;
    }

    //OTHER

}
