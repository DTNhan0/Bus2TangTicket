package com.springboot.bus2tangticket.service.TuyenXeVaTramDung;

import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.Assignment;

import java.util.List;

public interface AssignmentService {
    BaseResponse<List<Assignment>> createAssignment(int idBusRoute, List<Integer> idAccountList);
    BaseResponse<List<Assignment>> getAssignmentAccList(int idBusRoute);
    BaseResponse<List<Assignment>> deleteAssignment(int idBusRoute, List<Integer> idAccountList);
}
