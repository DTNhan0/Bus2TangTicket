USE bus2tangticket;

ALTER TABLE information AUTO_INCREMENT = 1;
ALTER TABLE assignment AUTO_INCREMENT = 1;
ALTER TABLE `account` AUTO_INCREMENT = 1;
ALTER TABLE blog AUTO_INCREMENT = 1;
ALTER TABLE busroute AUTO_INCREMENT = 1;
ALTER TABLE busstop AUTO_INCREMENT = 1;
ALTER TABLE busstopschedule AUTO_INCREMENT = 1;
ALTER TABLE departuredate AUTO_INCREMENT = 1;
ALTER TABLE `function` AUTO_INCREMENT = 1;
ALTER TABLE `history` AUTO_INCREMENT = 1;
ALTER TABLE invoice AUTO_INCREMENT = 1;
ALTER TABLE invoicedetail AUTO_INCREMENT = 1;
ALTER TABLE mediafile AUTO_INCREMENT = 1;
ALTER TABLE permission AUTO_INCREMENT = 1;
ALTER TABLE permissionfunction AUTO_INCREMENT = 1;
ALTER TABLE permissiongroup AUTO_INCREMENT = 1;
ALTER TABLE resourceref AUTO_INCREMENT = 1;
ALTER TABLE review AUTO_INCREMENT = 1;
ALTER TABLE routedeparturedate AUTO_INCREMENT = 1;
ALTER TABLE ticketprice AUTO_INCREMENT = 1;
ALTER TABLE userbook AUTO_INCREMENT = 1;
ALTER TABLE voucher AUTO_INCREMENT = 1;

-- INFORMATION
INSERT INTO information 
(Cic, FirstName, MiddleName, LastName, DateOfBirth, Sex, PermanentAddress, PhoneNumber, Email) 
VALUES
('012345678901', 'Nguyễn', 'Văn', 'An', '1995-05-20', 1, '123 Lý Thường Kiệt, Hà Nội', '0912345678', 'nguyenvanan@gmail.com'),
('023456789012', 'Trần', 'Thị', 'Bích', '1992-11-10', 0, '456 Nguyễn Huệ, TP. HCM', '0923456789', 'tranthibich@gmail.com'),
('034567890123', 'Lê', 'Văn', 'Cường', '1988-03-15', 1, '789 Lê Lợi, Đà Nẵng', '0934567890', 'levancuong@gmail.com'),
('045678901234', 'Phạm', 'Thị', 'Duyên', '1990-07-08', 0, '12 Hai Bà Trưng, Cần Thơ', '0945678901', 'phamthiduyen@gmail.com'),
('056789012345', 'Hoàng', 'Văn', 'Em', '1998-09-25', 1, '45 Trần Phú, Hải Phòng', '0956789012', 'hoangvanem@gmail.com'),
('067890123456', 'Đỗ', 'Thị', 'Phương', '1996-01-30', 0, '99 Nguyễn Trãi, Huế', '0967890123', 'dothiphuong@gmail.com');

-- ACCOUNT
INSERT INTO account (IdInfo, AccountName, Password, AccessToken, RefreshToken, TokenExpired, IsLocked)
VALUES
(
  (SELECT IdInfo FROM information WHERE Cic = '012345678901'),
  'nguyenvanan',
  '$2a$10$hashpasswordexample1',  -- giả định mã hóa
  'token_access_1',
  'token_refresh_1',
  DATE_ADD(NOW(), INTERVAL 30 DAY),
  1
),
(
  (SELECT IdInfo FROM information WHERE Cic = '023456789012'),
  'tranthibich',
  '$2a$10$hashpasswordexample2',
  'token_access_2',
  'token_refresh_2',
  DATE_ADD(NOW(), INTERVAL 30 DAY),
  0
),
(
  (SELECT IdInfo FROM information WHERE Cic = '034567890123'),
  'levancuong',
  '$2a$10$hashpasswordexample3',
  'token_access_3',
  'token_refresh_3',
  DATE_ADD(NOW(), INTERVAL 30 DAY),
  1
),
(
  (SELECT IdInfo FROM information WHERE Cic = '045678901234'),
  'phamthiduyen',
  '$2a$10$hashpasswordexample4',
  'token_access_4',
  'token_refresh_4',
  DATE_ADD(NOW(), INTERVAL 30 DAY),
  1
),
(
  (SELECT IdInfo FROM information WHERE Cic = '056789012345'),
  'hoangvanem',
  '$2a$10$hashpasswordexample5',
  'token_access_5',
  'token_refresh_5',
  DATE_ADD(NOW(), INTERVAL 30 DAY),
  0
),
(
  (SELECT IdInfo FROM information WHERE Cic = '067890123456'),
  'dothiphuong',
  '$2a$10$hashpasswordexample6',
  'token_access_6',
  'token_refresh_6',
  DATE_ADD(NOW(), INTERVAL 30 DAY),
  1
);
 