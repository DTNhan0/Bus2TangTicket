package com.springboot.bus2tangticket.model.TaiKhoanVaPhanQuyen;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdAccount")
    private Integer idAccount;

    @Column(name = "AccountName", nullable = false, unique = true)
    private String accountName;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "AccessToken", columnDefinition = "TEXT", nullable = true)
    private String accessToken;

    @Column(name = "RefreshToken", columnDefinition = "TEXT", nullable = true)
    private String refreshToken;

    @Column(name = "TokenExpired", nullable = true)
    private LocalDateTime tokenExpired;

    @Column(name = "IsLocked", nullable = false)
    private Boolean isLocked = false;

    public Account(Account account) {
        this.isLocked = account.getIsLocked();
        this.tokenExpired = null;
        this.refreshToken = null;
        this.accessToken = null;
        this.password = account.getPassword();
        this.information = null;
        this.accountName = account.getAccountName();
    }

    @Override
    public String toString() {
        return "Account{" +
                "idAccount=" + idAccount +
                ", information=" + information +
                ", accountName='" + accountName + '\'' +
                ", password='" + password + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", tokenExpired=" + tokenExpired +
                ", isLocked=" + isLocked +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(idAccount, account.idAccount) && Objects.equals(accountName, account.accountName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAccount, accountName);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdInfo", nullable = false)
    private Information information;
}
