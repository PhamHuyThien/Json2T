# Json2T - Đọc json chưa bao giờ đơn giản đến thế

## Cấu trúc:

### constructor

- (String json): void -> json là chuỗi json cần parse
- (Object object): void -> object được parse từ JSONValue.parse(String json)

### Method

#### Callback
- key(String key): Json2T -> key là key muốn get value trong json
- index(int index): Json2T -> vị trí muốn lấy trong 1 mảng

#### to Value
- toJsonString(): String -> trả về json
- toStr(): String -> trả về chuỗi value
- toObj(): Object -> lấy object value
- toInt(): int -> trả về giá trị kiểu int
- toDouble(): double -> trả về giá trị kiểu double

#### to Array
- toPairObjs(): Json2T[][] -> trả về mảng 2 chiều trong đó [i][0] lày key, và [i][1] là value
- toObjs(): Json2T[] -> trả về mảng đối Json2T
- toStrs(): String[] -> trả về mảng chuỗi 
- toInts(): int[] -> trả về mảng int
- toDoubles(): double[] -> trả về mảng double


## Hướng dẫn

- xem ExampleJson2T.java trong src