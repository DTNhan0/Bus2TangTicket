package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen.Account;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.Assignment;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.repository.AssignmentRepo;
import com.springboot.bus2tangticket.service.TaiKhoanVaPhanQuyen.AccountServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AssignmentServiceImpl implements AssignmentService{

    @Autowired
    AssignmentRepo assignmentRepo;

    @PersistenceContext
    private EntityManager em;

    private void resetAutoIncrement() {
        // Thiết lập AUTO_INCREMENT về 1 (tức next = max(id)+1)
        em.createNativeQuery("ALTER TABLE assignment AUTO_INCREMENT = 1")
                .executeUpdate();
    }

    @Autowired
    public AssignmentServiceImpl(AssignmentRepo assignmentRepo) {
        this.assignmentRepo = assignmentRepo;
    }

    @Autowired
    public void setAssignmentRepo(AssignmentRepo assignmentRepo) {
        this.assignmentRepo = assignmentRepo;
    }

    @Autowired
    BusRouteServiceImpl busRouteService;

    @Autowired
    AccountServiceImpl accountService;

    //CREATE
    @Transactional
    @Override
    public BaseResponse<List<Assignment>> createAssignment(int idBusRoute, List<Integer> idAccountList) {
        resetAutoIncrement();
        List<Assignment> assignmentList = new ArrayList<>();

        BusRoute busRoute = busRouteService.getBusRoute(idBusRoute).getData();

        if(busRoute == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy busRoute có id: " + idBusRoute, null);


        for(Integer idAccount : idAccountList){
            Account account = accountService.getAcc(idAccount).getData();
            System.out.println(account);
            if(account == null)
                return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy account có id: " + idAccount, null);
            Assignment assignment = new Assignment();
            assignment.setAccount(account);
            assignment.setBusRoute(busRoute);

            assignmentRepo.save(assignment);
            assignmentList.add(assignment);
        }

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Tạo assignment thành công!", assignmentList);
    }

    //READ

    //UPDATE

    //DELETE
    @Transactional
    @Override
    public BaseResponse<List<Assignment>> deleteAssignment(int idBusRoute, List<Integer> idAccountList) {
        List<Assignment> assignmentDeletedList = new ArrayList<>();
        List<Assignment> assignmentList = getAssignmentAccList(idBusRoute).getData();

        if(assignmentList == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không thể xóa danh sách rỗng busRoute có id: " + idBusRoute, null);

        BusRoute selectedBusRoute = busRouteService.getBusRoute(idBusRoute).getData();

        if(selectedBusRoute == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy busRoute có id: " + idBusRoute, null);

        Set<Integer> idAccountSet = new HashSet<>(idAccountList); // O(n) để xây set

        for (Assignment assignment : assignmentList) {
            if (idAccountSet.contains(assignment.getAccount().getIdAccount())) {
                assignmentDeletedList.add(assignment);
                assignmentRepo.deleteById(assignment.getIdAssignment());
            }
        }

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Đã xóa Assignment thành công!", assignmentDeletedList);
    }

    //OTHER
    @Override
    public BaseResponse<List<Assignment>> getAssignmentAccList(int idBusRoute) {

        List<Assignment> assignmentList = new ArrayList<>();

        BusRoute selectedBusRoute = busRouteService.getBusRoute(idBusRoute).getData();

        if(selectedBusRoute == null)
            return new BaseResponse<>(ResponseStatus.FAILED, "Không tìm thấy busRoute có id: " + idBusRoute, null);

        for (Assignment assignment : assignmentRepo.findAll()){
            if(Objects.equals(assignment.getBusRoute().getIdBusRoute(), selectedBusRoute.getIdBusRoute()))
                assignmentList.add(assignment);
        }

        return new BaseResponse<>(ResponseStatus.SUCCESS, "Lấy thành công danh sách assignment theo id Busroute: " + idBusRoute, assignmentList);
    }

}
