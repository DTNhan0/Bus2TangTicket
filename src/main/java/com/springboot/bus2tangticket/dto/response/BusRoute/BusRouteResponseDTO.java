package com.springboot.bus2tangticket.dto.response.BusRoute;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.springboot.bus2tangticket.model.TuyenXeVaTramDung.BusRoute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusRouteResponseDTO {
    private Integer idBusRoute;

    private Integer parentRoute;

    private String busRouteName;

    private String overview;

    private String description;

    private String highlights;

    private String included;

    private String excluded;

    private String whatToBring;

    private String beforeYouGo;

    private Boolean isAvailable;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime updateAt;
}
