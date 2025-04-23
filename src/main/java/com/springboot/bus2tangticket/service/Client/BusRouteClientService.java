package com.springboot.bus2tangticket.service.Client;

import com.springboot.bus2tangticket.repository.BusRouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusRouteClientService {
    @Autowired
    private BusRouteRepo busRouteRepo;


}
