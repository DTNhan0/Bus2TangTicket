package com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "information")
public class Information {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdInfo")
    private int idInfo;

    @Column(name = "Cic", length = 12, nullable = false, unique = true)
    private String cic;

    @Column(name = "FirstName", length = 100, nullable = false)
    private String firstName;

    @Column(name = "MiddleName", length = 100)
    private String middleName;

    @Column(name = "LastName", length = 100, nullable = false)
    private String lastName;

    @Column(name = "DateOfBirth")
    private LocalDate dateOfBirth; // hoặc java.sql.Date nếu dùng JDBC

    @Column(name = "Sex")
    private Boolean sex;  // tinyint(1) sẽ được ánh xạ với Boolean

    @Column(name = "PermanentAddress", columnDefinition = "TEXT", nullable = false)
    private String permanentAddress;

    @Column(name = "PhoneNumber", length = 10, nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "Email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "UpdateAt", nullable = false)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy hh:mm:ss",
            timezone = "Asia/Ho_Chi_Minh" // Thêm múi giờ [[1]][[2]]
    )
    private LocalDateTime updateAt;

    @PrePersist
    protected void onCreate() {
        this.updateAt = LocalDateTime.now(); // Không cần TimeZone, lấy thời gian local [[9]]
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Information{" +
                "idInfo=" + idInfo +
                ", cic='" + cic + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", sex=" + sex +
                ", permanentAddress='" + permanentAddress + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", updateAt=" + updateAt +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Information that = (Information) o;
        return idInfo == that.idInfo && Objects.equals(cic, that.cic) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInfo, cic, phoneNumber, email);
    }
}
