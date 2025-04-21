package com.springboot.bus2tangticket.service.TaiKhoanVaPhanQuyen;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Information;
import com.springboot.bus2tangticket.repository.InformationRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public BaseResponse<Information> createInfo(Information information) {
        resetAutoIncrement();
        Information info = informationRepo.save(information);
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo information thành công!", info);
    }

    //READ
    @Override
    public BaseResponse<List<Information>> getAllInfo() {
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy danh sách information thành công!", informationRepo.findAll());
    }

    @Override
    public BaseResponse<Information> getInfo(int idInfo) {
        Information information = informationRepo.findById(idInfo).orElse(null);

        if(information == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy information có id: " + idInfo, null);

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tìm thấy information có id: " + idInfo, information);
    }

    //UPDATE
    @Transactional
    @Override
    public BaseResponse<Information> updateInfo(int infoId, Information information) {
        BaseResponse<Information> baseResponse = getInfo(infoId);

        if (baseResponse.getData() == null)
            return new BaseResponse<>(baseResponse.getStatus(), baseResponse.getMessage(), null);

        Information existingInfo = baseResponse.getData();

        // Kiểm tra CIC đã tồn tại (và khác cái hiện tại)
        if (!existingInfo.getCic().equals(information.getCic()) &&
                informationRepo.existsByCic(information.getCic())) {
            return new BaseResponse<>(ResponseStatus.FAILED, "CIC đã tồn tại", null);
        }

        // Kiểm tra email đã tồn tại
        if (!existingInfo.getEmail().equals(information.getEmail()) &&
                informationRepo.existsByEmail(information.getEmail())) {
            return new BaseResponse<>(ResponseStatus.FAILED, "Email đã tồn tại", null);
        }

        // Kiểm tra số điện thoại đã tồn tại
        if (!existingInfo.getPhoneNumber().equals(information.getPhoneNumber()) &&
                informationRepo.existsByPhoneNumber(information.getPhoneNumber())) {
            return new BaseResponse<>(ResponseStatus.FAILED, "SĐT đã tồn tại", null);
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

        Information updated = informationRepo.save(existingInfo);

        // `updateAt` sẽ tự cập nhật qua @PreUpdate
        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã cập nhật information có id: " + infoId, updated);
    }

    //DELETE
    @Transactional
    @Override
    public BaseResponse<Information> deleteInfo(int idInfo) {
        Information information = getInfo(idInfo).getData();

        if (information == null) {
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy thông tin", null);
        }

        try {
            informationRepo.deleteById(idInfo);
        } catch (DataIntegrityViolationException e) {
            return new BaseResponse<>(ResponseStatus.FAILED, "Lỗi ràng buộc dữ liệu!", information);
        }

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Xóa thành công", information);
    }

    //OTHER
    public BaseResponse<Information> getAllAccByIdInfo(int idInfo){
        Information information = informationRepo.findById(idInfo).orElse(null);

        if(information == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy information có id: " + idInfo, null);

        System.out.println(information.getAccounts());

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã tìm thấy information có id: " + idInfo, information);
    }

}
