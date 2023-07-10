# Hướng dẫn chạy code
1. Cài mariadb server (https://mirrors.bkns.vn/mariadb//mariadb-10.9.3/winx64-packages/mariadb-10.9.3-winx64.msi), thiết lập password cho user root
2. Login mariadb bằng tài khoản root từ command line Tạo database room
``` sql
   CREATE DATABASE mydatabase CHARACTER SET utf8 COLLATE utf8_general_ci;
```
3. Tạo các bảng trong file sql/room.sql
4. Chạy từ hàm main trong file RoomLauncher.java
6. Kiểm tra api test (http://localhost:8080/api/test) và tìm hiểu cài đặt thêm các api khác

