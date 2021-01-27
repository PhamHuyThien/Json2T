# Json2T - Đọc json chưa bao giờ đơn giản đến thế

## Cấu trúc:

### Constructor
- (String json): void -> json là chuỗi json cần parse
- (Object object): void -> object được parse từ JSONValue.parse(String json)

### Variable
- length: int -> độ dài của mảng (áp dụng với mảng nếu là đối tượng trả về -1)

### Method

#### Callback:
- key(String key): Json2T -> key là key muốn get value trong json
- index(int index): Json2T -> vị trí muốn lấy trong 1 mảng

#### Lấy giá trị:
- toJsonString(): String -> trả về json
- toStr(): String -> trả về chuỗi value
- toObj(): Object -> lấy object value 
- toInt(): int -> trả về giá trị kiểu int (không thể ép kiểu trả về -1)
- toDouble(): double -> trả về giá trị kiểu double (không thể ép kiểu trả về -1)

#### Lấy mảng giá trị:
- toPairObjs(): Json2T[][] -> trả về mảng 2 chiều trong đó [i][0] lày key, và [i][1] là value
- toObjs(): Json2T[] -> trả về mảng đối tượng Json2T
- toStrs(): String[] -> trả về mảng chuỗi 
- toInts(): int[] -> trả về mảng int (không thể ép kiểu trả về -1)
- toDoubles(): double[] -> trả về mảng double (không thể ép kiểu trả về -1)


## Hướng dẫn:
- xem ExampleJson2T.java trong src

## Lưu ý:
- không tìm thấy key hoặc index sẽ trả về Null

## Cập nhật:

#### v1.0.1:
- thêm biến length: int để kiểm tra độ dài mảng
- fix lại 1 số hàm cho gọn nhẹ

#### v1.0.0: 
- ra mắt Json2T

## Thông tin:
- Tên: Json2T
- Tác giả: Phạm Huy Thiên - Thiên Đẹp Zaii ( SystemError )
- Phiên bản: 1.0.1
- Thư viện sử dụng: JSON SIMPLE
- Phiên bản thư viện: 1.1.1