package com.springboot.bus2tangticket.model.TuyenXeVaTramDung;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mediafile")
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdMediaFile")
    private Integer idMediaFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdBusStop")
    private BusStop busStop;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdBusRoute")
    private BusRoute busRoute;

    @Column(name = "FileName", nullable = false, unique = true)
    private String fileName;

    @Lob
    @Column(name = "FileData", nullable = false, columnDefinition = "LONGBLOB")
    private byte[] fileData;

    @Column(name = "FileType", nullable = false)
    private String fileType;
}
