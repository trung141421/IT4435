# Project Guildline
Project được tạo ra với mục đích thống nhất tất cả các api 
cung cấp cho tất cả các sản phẩm. 
## Lợi ích
1. Chạy chung một khối để tiết kiệm tài nguyên
khi các sản phẩm ở giai đoạn đầu. Đặc biệt sử dụng chung database dễ dàng trong việc maintain và backup database tốn nhiều chi phí.
2. Thống nhất api nhằm tăng khả năng tái sử dụng api, tái sử dụng code, thuận lợi hơn trong quá trình sử dụng và bảo trì.
## Quy tắc
1. Các service sử dụng chung gọi là vdef, được sử dụng cho tất cả sản phẩm
2. Các service sử dụng cho các sản phẩm khác nhau thì tách riêng ra, và khi đặt tên phải có tên sản phẩm ở đầu để phân biệt. Ví dụ IGochieVoucherService.java
3. Các table trong sql khi đặt tên phải có tên sản phẩm ở đầu để phân biệt. Ví dụ với gochie voucher thì đặt tên là gochie_voucher
4. Mỗi service chỉ nên tương tác với 1 table trong database. Khi xuất hiện table thứ 2 ưu tiên tách ra một service mới.
5. Không phải lúc nào service cũng trả về định dạng của response, service có thể chỉ trả về json chứa data hoặc bất kì object nào, miễn là service đó không sử dụng bởi các router trả về cho client.

# Các dự án
## Gochie
1. Các Class có tên bắt đầu bằng Gochie
2. Các table có tên bắt đầu bằng gc_
3. Các url có dạng '/v1/gc/*'

## Izi House
1. Các Class có tên bắt đầu bằng Izehouse
2. Các table có tên bắt đầu bằng ih_
3. Các url có dạng '/v1/ih/*'

## We Care
1. Các Class có tên bắt đầu bằng Wecare
2. Các table có tên bắt đầu bằng wc_
3. Các url có dạng '/v1/wc/*'