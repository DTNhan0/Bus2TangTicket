package com.springboot.bus2tangticket.controller;

import com.springboot.bus2tangticket.dto.request.InformationRequestDTO;
import com.springboot.bus2tangticket.dto.response.InformationResponseDTO;
import com.springboot.bus2tangticket.model.Information;
import com.springboot.bus2tangticket.service.InformationServiceImpl;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class InformationController {

    @Autowired
    private InformationServiceImpl informationServiceImpl;

    @Autowired
    private ModelMapper modelMapper;

    public InformationController() {
    }

    @Autowired
    public InformationController(InformationServiceImpl informationServiceImpl) {
        this.informationServiceImpl = informationServiceImpl;
    }

    public InformationServiceImpl getInformationService() {
        return informationServiceImpl;
    }

    @Autowired
    public void setInformationService(InformationServiceImpl informationServiceImpl) {
        this.informationServiceImpl = informationServiceImpl;
    }

    //CREATE
    @PostMapping("/information")
    public ResponseEntity<InformationResponseDTO> createInfo(
            @Valid @RequestBody InformationRequestDTO requestDTO) {
        Information info = informationServiceImpl.createInfo(requestDTO);
        return new ResponseEntity<>(
                this.modelMapper.map(info, InformationResponseDTO.class),
                HttpStatus.CREATED
        );
    }

    //READ
    @GetMapping("/information")
    public ResponseEntity<List<InformationResponseDTO>> getInfos(){
        List<InformationResponseDTO> dtoList = informationServiceImpl.getInfos()
                                                                     .stream()
                                                                     .map(i -> this.modelMapper.map(i, InformationResponseDTO.class))
                                                                     .collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/information/{idInfo}")
    public ResponseEntity<InformationResponseDTO> getInfo(@PathVariable int idInfo){
        Information info = informationServiceImpl.getInfo(idInfo);
        if (info == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(this.modelMapper.map(info, InformationResponseDTO.class));
    }

    //UPDATE
    @PutMapping("/information/{idInfo}")
    public ResponseEntity<InformationResponseDTO> updateInfo(
            @PathVariable int idInfo,
            @Valid @RequestBody InformationRequestDTO updatedDto) {
        Information updated = informationServiceImpl.updateInfo(idInfo, updatedDto);
        return ResponseEntity.ok(this.modelMapper.map(updated, InformationResponseDTO.class));
    }

    //DELETE
    @DeleteMapping("/information/{idInfo}")
    public void deleteInfo(@PathVariable("idInfo") int idInfo){
        informationServiceImpl.deleteInfo(idInfo);
    }
}
