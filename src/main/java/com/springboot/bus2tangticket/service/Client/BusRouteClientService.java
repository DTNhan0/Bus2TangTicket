package com.springboot.bus2tangticket.service.Client;

import com.springboot.bus2tangticket.dto.response.BusRoute.BusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.BusStop.BusStopNoBusRouteResponseDTO;
import com.springboot.bus2tangticket.dto.response.Client.ClientResponseBusRouteDTO;
import com.springboot.bus2tangticket.dto.response.Client.ClientResponseListBusRouteDTO;
import com.springboot.bus2tangticket.dto.response.MediaFile.MediaFileResponseDTO;
import com.springboot.bus2tangticket.dto.response.TicketPrice.TicketPriceReponseNoBusRouteDTO;
import com.springboot.bus2tangticket.dto.response.responseUtil.BaseResponse;
import com.springboot.bus2tangticket.dto.response.responseUtil.ResponseStatus;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import com.springboot.bus2tangticket.repository.BusRouteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusRouteClientService  {

    @Autowired
    private BusRouteRepo busRouteRepo;

    public BaseResponse<List<ClientResponseListBusRouteDTO>> getClientListBusRoute() {
        List<ClientResponseListBusRouteDTO> dtoList = busRouteRepo.findAll().stream()
                .map(route -> {
                    ClientResponseListBusRouteDTO dto = new ClientResponseListBusRouteDTO();
                    dto.setIdBusRoute(route.getIdBusRoute());
                    dto.setIdParent(route.getIdParent());
                    dto.setBusRouteName(route.getBusRouteName());
                    dto.setOverview(route.getOverview());
                    dto.setDescription(route.getDescription());

                    // Ticket prices
                    List<TicketPriceReponseNoBusRouteDTO> prices = route.getTicketPrices().stream()
                            .map(tp -> {
                                TicketPriceReponseNoBusRouteDTO p = new TicketPriceReponseNoBusRouteDTO();
                                p.setIdTicketPrice(tp.getIdTicketPrice());
                                p.setParentPrice(tp.getParentPrice());
                                p.setChildPrice(tp.getChildPrice());
                                p.setTicketType(tp.getTicketType());
                                p.setStatus(tp.getStatus());
                                return p;
                            })
                            .collect(Collectors.toList());
                    dto.setTicketPriceList(prices);

                    // Media files for route
                    List<MediaFileResponseDTO> mediaList = route.getMediaFileList().stream()
                            .map(mf -> {
                                MediaFileResponseDTO m = new MediaFileResponseDTO();
                                m.setIdMediaFile(mf.getIdMediaFile());
                                m.setIdBusRoute(mf.getBusRoute() != null ? mf.getBusRoute().getIdBusRoute() : null);
                                m.setIdBusStop(mf.getBusStop()  != null ? mf.getBusStop().getIdBusStop()   : null);
                                m.setFileName(mf.getFileName());
                                m.setFileType(mf.getFileType());
                                return m;
                            })
                            .collect(Collectors.toList());
                    dto.setMediaBusRouteList(mediaList);

                    return dto;
                })
                .collect(Collectors.toList());

        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Lấy danh sách tuyến xe thành công",
                dtoList);
    }

    public BaseResponse<ClientResponseBusRouteDTO> getClientBusRoute(int idBusRoute) {
        Optional<BusRoute> opt = busRouteRepo.findById(idBusRoute);
        if (opt.isEmpty()) {
            return new BaseResponse<>(ResponseStatus.FAILED,
                    "Không tìm thấy busRoute với id: " + idBusRoute,
                    null);
        }
        BusRoute route = opt.get();

        ClientResponseBusRouteDTO dto = new ClientResponseBusRouteDTO();

        // Map BusRoute details
        BusRouteResponseDTO br = new BusRouteResponseDTO();
        br.setIdBusRoute(route.getIdBusRoute());
        br.setParentRoute(route.getIdParent());
        br.setBusRouteName(route.getBusRouteName());
        br.setOverview(route.getOverview());
        br.setDescription(route.getDescription());
        br.setHighlights(route.getHighlights());
        br.setIncluded(route.getIncluded());
        br.setExcluded(route.getExcluded());
        br.setWhatToBring(route.getWhatToBring());
        br.setBeforeYouGo(route.getBeforeYouGo());
        br.setIsAvailable(route.getIsAvailable());
        br.setUpdateAt(route.getUpdateAt());
        dto.setBusRoute(br);

        // Ticket prices
        List<TicketPriceReponseNoBusRouteDTO> priceList = route.getTicketPrices().stream()
                .map(tp -> {
                    TicketPriceReponseNoBusRouteDTO p = new TicketPriceReponseNoBusRouteDTO();
                    p.setIdTicketPrice(tp.getIdTicketPrice());
                    p.setParentPrice(tp.getParentPrice());
                    p.setChildPrice(tp.getChildPrice());
                    p.setTicketType(tp.getTicketType());
                    p.setStatus(tp.getStatus());
                    return p;
                })
                .collect(Collectors.toList());
        dto.setTicketPriceList(priceList);

        // Bus stops
        List<BusStopNoBusRouteResponseDTO> stops = route.getBusStops().stream()
                .map(bs -> {
                    BusStopNoBusRouteResponseDTO s = new BusStopNoBusRouteResponseDTO();
                    s.setIdBusStop(bs.getIdBusStop());
                    s.setIdParent(bs.getIdParent());
                    s.setBusStopName(bs.getBusStopName());
                    s.setIntroduction(bs.getIntroduction());
                    s.setAddress(bs.getAddress());
                    s.setStopOrder(bs.getStopOrder());
                    s.setUpdateAt(bs.getUpdateAt());
                    s.setIsAvailable(bs.getIsAvailable());
                    // Media on stop
                    List<MediaFileResponseDTO> stopMedia = bs.getMediaFileList().stream()
                            .map(mf -> {
                                MediaFileResponseDTO m = new MediaFileResponseDTO();
                                m.setIdMediaFile(mf.getIdMediaFile());
                                m.setIdBusRoute(mf.getBusRoute() != null ? mf.getBusRoute().getIdBusRoute() : null);
                                m.setIdBusStop(mf.getBusStop()  != null ? mf.getBusStop().getIdBusStop()   : null);
                                m.setFileName(mf.getFileName());
                                m.setFileType(mf.getFileType());
                                return m;
                            })
                            .collect(Collectors.toList());
                    s.setMediaBusStopList(stopMedia);
                    return s;
                })
                .collect(Collectors.toList());
        dto.setBusStopList(stops);

        // Media for route
        List<MediaFileResponseDTO> mediaRoute = route.getMediaFileList().stream()
                .map(mf -> {
                    MediaFileResponseDTO m = new MediaFileResponseDTO();
                    m.setIdMediaFile(mf.getIdMediaFile());
                    m.setIdBusRoute(mf.getBusRoute() != null ? mf.getBusRoute().getIdBusRoute() : null);
                    m.setIdBusStop(mf.getBusStop()  != null ? mf.getBusStop().getIdBusStop()   : null);
                    m.setFileName(mf.getFileName());
                    m.setFileType(mf.getFileType());
                    return m;
                })
                .collect(Collectors.toList());
        dto.setMediaBusRouteList(mediaRoute);

        return new BaseResponse<>(ResponseStatus.SUCCESS,
                "Lấy chi tiết busRoute thành công",
                dto);
    }
}
