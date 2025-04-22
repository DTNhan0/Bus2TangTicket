package com.springboot.bus2tangticket.dto.request.Assignment;

import lombok.Data;

import java.util.List;

@Data
public class AssignmentRequestDTO {
    private List<Integer> idAccountList;
}
