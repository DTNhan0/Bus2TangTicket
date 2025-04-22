USE bus2tangticket;

ALTER TABLE information AUTO_INCREMENT = 1;
ALTER TABLE assignment AUTO_INCREMENT = 1;
ALTER TABLE `account` AUTO_INCREMENT = 1;
ALTER TABLE blog AUTO_INCREMENT = 1;
ALTER TABLE busroute AUTO_INCREMENT = 1;
ALTER TABLE busstop AUTO_INCREMENT = 1;
ALTER TABLE busstopschedule AUTO_INCREMENT = 1;
ALTER TABLE `function` AUTO_INCREMENT = 1;
ALTER TABLE `history` AUTO_INCREMENT = 1;
ALTER TABLE invoice AUTO_INCREMENT = 1;
ALTER TABLE invoicedetail AUTO_INCREMENT = 1;
ALTER TABLE mediafile AUTO_INCREMENT = 1;
ALTER TABLE permission AUTO_INCREMENT = 1;
ALTER TABLE permissionfunction AUTO_INCREMENT = 1;
ALTER TABLE permissiongroup AUTO_INCREMENT = 1;
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

-- BUSROUTE
INSERT INTO busroute (
  IdParent, BusRouteName, Overview, Description, Highlights,
  Included, Excluded, WhatToBring, BeforeYouGo, IsAvailable
)
VALUES
(NULL, 'Line 1: Trung tâm TP - Dinh Độc Lập',
  'Tuyến du lịch thành phố bằng xe buýt hai tầng hiện đại với lộ trình trung tâm Sài Gòn.',
  'Trải nghiệm khám phá trung tâm thành phố Hồ Chí Minh bằng xe buýt hai tầng Vietnam Sightseeing với hệ thống thuyết minh tự động đa ngôn ngữ.',
  'Đi qua Nhà thờ Đức Bà, Bưu điện, Dinh Độc Lập, phố đi bộ Nguyễn Huệ...',
  'Vé xe buýt, Tai nghe, Wifi miễn phí, Bản đồ, Bảo hiểm',
  'Không bao gồm đồ ăn, vé vào cổng địa điểm, đưa đón khách sạn',
  'Trang phục lịch sự, điện thoại để nghe audio guide',
  'Vui lòng đến sớm 10 phút, chuẩn bị vé QR để check-in',
  1
),

(NULL, 'Line 2: Sài Gòn - Chợ Lớn',
  'Là một trong những sản phẩm xe buýt 2 tầng đầu tiên Sài Gòn, Sài Gòn - Chợ lớn City tour là một hình thức độc đáo với dịch vụ chất lượng cao mang đến một chuyến du lịch sáng tạo, thú vị và linh hoạt.',
  'Chúng tôi cam kết cải thiện chất lượng trong ngành du lịch tại mỗi thành phố nơi chúng tôi có mặt, cũng như nỗ lực để đạt được sự hài lòng hoàn toàn của khách hàng.',
  'Xe buýt hai tầng Vietnam Sightseeing tại thành phố Hồ Chí Minh\nVào cửa không giới hạn các điểm tham quan chính ở Sài Gòn (vé có giá trị trong 4 giờ)\nTham quan Sài Gòn theo tốc độ của riêng bạn và ngắm nhìn những địa danh tiêu biểu nhất của Sài Gòn\nCó thể kết nối Audio guide 9 ngôn ngữ\nXe buýt chạy cứ sau 30 phút',
  'Vé xe buýt hai tầng\nLái xe / Phụ xe\nWifi miễn phí trên xe\nThuyết minh đa ngôn ngữ\nTai nghe\nBản đồ lộ trình\nBảo hiểm du lịch',
  'Phí vào cửa\nThức ăn và đồ uống khác\nĐón và trả khách sạn\nTiền thưởng (không bắt buộc)',
  'Trang phục thoải mái\nKính râm\nKem chống nắng',
  'Thời gian có thể thay đổi tùy theo tình trạng giao thông.\nĐưa cho nhân viên mặc đồng phục vé điện tử để check in.',
  0
),

(2, 'Line 2B: Sài Gòn - Chợ Lớn - Mở rộng',
  'Tuyến mở rộng từ Line 2, đi qua thêm nhiều điểm ở khu vực quận 5.',
  'Bổ sung các điểm như Bệnh viện Hùng Vương, Chùa Bà Thiên Hậu, Hẻm ẩm thực Hải Thượng Lãn Ông.',
  'Tuyến dài hơn, có thời lượng 6 giờ\nDừng lâu hơn tại mỗi điểm',
  'Vé xe buýt, Nước suối, Tai nghe',
  'Không bao gồm hướng dẫn viên riêng, các tour nội khu',
  'Nón, khẩu trang, điện thoại sạc đầy',
  'Có thể đông vào dịp lễ, vui lòng giữ gìn trật tự trên xe',
  1
);

-- BUSSTOP
-- Trạm dừng cho tuyến Line 1: Trung tâm TP - Dinh Độc Lập (IdBusRoute = 1)
INSERT INTO `busstop` (`IdParent`, `IdBusRoute`, `BusStopName`, `Introduction`, `Address`, `StopOrder`, `IsAvailable`) VALUES
(NULL, 1, 'Nhà thờ Đức Bà', 
'Điểm dừng gần Nhà thờ Đức Bà Sài Gòn - một công trình kiến trúc tiêu biểu của Pháp thời thuộc địa.', 
'01 Công xã Paris, Phường Bến Nghé, Quận 1, TP.HCM', 
0, TRUE),

(NULL, 1, 'Dinh Độc Lập', 
'Xe dừng tại cổng chính Dinh Độc Lập, nơi từng là Phủ Tổng Thống của chính quyền Việt Nam Cộng Hòa.', 
'135 Nam Kỳ Khởi Nghĩa, Phường Bến Thành, Quận 1, TP.HCM', 
1, TRUE);


-- Trạm dừng cho tuyến Line 2: Sài Gòn - Chợ Lớn (IdBusRoute = 2)
INSERT INTO `busstop` (`IdParent`, `IdBusRoute`, `BusStopName`, `Introduction`, `Address`, `StopOrder`, `IsAvailable`) VALUES
(NULL, 2, 'Chợ Bến Thành', 
'Trạm khởi hành tại khu vực phía trước Chợ Bến Thành – biểu tượng thương mại của Sài Gòn.', 
'Đường Lê Lợi, Phường Bến Thành, Quận 1, TP.HCM', 
0, FALSE),

(NULL, 2, 'Chùa Bà Thiên Hậu', 
'Xe dừng gần Chùa Bà Thiên Hậu, một ngôi chùa linh thiêng có kiến trúc Trung Hoa nổi bật ở quận 5.', 
'710 Nguyễn Trãi, Phường 11, Quận 5, TP.HCM', 
1, FALSE);

-- Trạm dừng cho tuyến Line 2B: Sài Gòn - Chợ Lớn - Mở rộng (IdBusRoute = 3)
INSERT INTO `busstop` (`IdParent`, `IdBusRoute`, `BusStopName`, `Introduction`, `Address`, `StopOrder`, `IsAvailable`) VALUES
(NULL, 3, 'Bệnh viện Hùng Vương', 
'Trạm dừng gần bệnh viện lớn và lâu đời nhất tại TP.HCM, điểm nhận diện nổi bật tại quận 5.', 
'128 Hồng Bàng, Phường 12, Quận 5, TP.HCM', 
0, TRUE),

(4, 3, 'Line 2: Chùa Bà Thiên Hậu', 
'Xe dừng gần Chùa Bà Thiên Hậu, một ngôi chùa linh thiêng có kiến trúc Trung Hoa nổi bật ở quận 5.', 
'710 Nguyễn Trãi, Phường 11, Quận 5, TP.HCM', 
1, TRUE);

-- TICKETPRICE
-- Vé 24h (active) cho Line 1
INSERT INTO ticketprice (IdBusRoute, ParentPrice, ChildPrice, TicketType, Status)
VALUES (1, 299000, 225000, '24h', 1);

-- Vé 48h (active) cho Line 1
INSERT INTO ticketprice (IdBusRoute, ParentPrice, ChildPrice, TicketType, Status)
VALUES (1, 399000, 289000, '48h', 1);

-- Vé 24h (active) cho Line 2
INSERT INTO ticketprice (IdBusRoute, ParentPrice, ChildPrice, TicketType, Status)
VALUES (2, 275000, 199000, '24h', 1);

-- Vé 48h (inactive - vé cũ, thay thế bằng vé khác chẳng hạn)
INSERT INTO ticketprice (IdBusRoute, ParentPrice, ChildPrice, TicketType, Status)
VALUES (2, 375000, 289000, '48h', 0);

-- Vé 48h (active) cho Line 2
INSERT INTO ticketprice (IdBusRoute, ParentPrice, ChildPrice, TicketType, Status)
VALUES (2, 359000, 265000, '48h', 1);

-- Vé 24h (active) cho Line 2B
INSERT INTO ticketprice (IdBusRoute, ParentPrice, ChildPrice, TicketType, Status)
VALUES (3, 319000, 240000, '24h', 1);

-- Vé 24h (inactive - vé cũ) cho Line 2B
INSERT INTO ticketprice (IdBusRoute, ParentPrice, ChildPrice, TicketType, Status)
VALUES (3, 305000, 230000, '24h', 0);

-- Vé 48h (active) cho Line 2B
INSERT INTO ticketprice (IdBusRoute, ParentPrice, ChildPrice, TicketType, Status)
VALUES (3, 415000, 330000, '48h', 1);

-- ASSIGNMENT
-- Account 1: nguyenvanan → Line 1
INSERT INTO assignment (IdBusRoute, IdAccount) VALUES (1, 1);

-- Account 2: tranthibich → Line 1 và Line 2
INSERT INTO assignment (IdBusRoute, IdAccount) VALUES (1, 2);
INSERT INTO assignment (IdBusRoute, IdAccount) VALUES (2, 2);

-- Account 3: levancuong → Line 2B
INSERT INTO assignment (IdBusRoute, IdAccount) VALUES (3, 3);

-- Account 4: phamthiduyen → Line 1
INSERT INTO assignment (IdBusRoute, IdAccount) VALUES (1, 4);

-- Account 5: hoangvanem → Line 2
INSERT INTO assignment (IdBusRoute, IdAccount) VALUES (2, 5);

-- Account 6: dothiphuong → Line 2B
INSERT INTO assignment (IdBusRoute, IdAccount) VALUES (3, 6);

-- ROUTERDEPARTUREDATE
-- Line 1: Trung tâm TP - Dinh Độc Lập (IdBusRoute = 1)
INSERT INTO routedeparturedate (Date, IdBusRoute, NumberOfSeats, Status) VALUES
('2025-04-19', 1, 30, 1),
('2025-04-20', 1, 30, 1),
('2025-04-21', 1, 30, 1);

-- Line 2: Sài Gòn - Chợ Lớn (IdBusRoute = 2)
INSERT INTO routedeparturedate (Date, IdBusRoute, NumberOfSeats, Status) VALUES
('2025-04-19', 2, 30, 1),
('2025-04-20', 2, 30, 1),
('2025-04-21', 2, 30, 1);

-- Line 2B: Sài Gòn - Chợ Lớn - Mở rộng (IdBusRoute = 3)
INSERT INTO routedeparturedate (Date, IdBusRoute, NumberOfSeats, Status) VALUES
('2025-04-19', 3, 30, 1),
('2025-04-20', 3, 30, 1),
('2025-04-21', 3, 30, 1);
