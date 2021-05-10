# JJson - Đọc json nhanh như người yêu cũ trở mặt!
Việc sử dụng thư viện json-simple vẫn còn rất khó khăn với những người mới.  
JJson được sinh ra đời để việc đọc và ghi json trở lên đơn giản hơn bao giờ hết.  
## Hướng dẫn:
### Đọc Json
cho đoạn json như sau: 
```json
{
   "firstName":"Thien",
   "lastName":"Pham",
   "address":{
      "streetAddress":"Phu Ung",
      "district":"An Thi",
      "city":"Hung Yen",
      "state":"",
      "postalCode":"56789"
   },
   "age":20,
   "phoneNumbers":[
      {
         "type":"home",
         "number":"0941661235"
      },
      {
         "type":"fax",
         "number":"0435508028"
      }
   ],
   "girlFriend":{
      "name":"Thom",
      "age":18
   },
   "interests":[
      "code",
      "watch movie",
      "nice number"
   ],
   "favoriteNumbers":[
       11,
        7,
        2000,
        08,
        09,
        2002
   ],
   "sex":"Male",
   "salary":1200.0
}
```
#### cách thực hiện:
```java
JJson jjson = JJson.parse(json); //trong đó json là chuỗi json trên
String firstName = jjson.q(".firstName").toStr();
// or jjson.k("firstName").toStr();
String city = jjson.q(".address.city").toStr();
// or jjson.k("firstName").k("city").toStr();
int postalCode = jjson.q(".address.postalCode").toInt();
// or jjson.k("firstName").k("city").toInt();
double salary = jjson.q(".salary").toDouble();
int index2Interest = jjson.q(".interests[2]").toStrs();
int index4FavoriteNumbers = jjson.q(".favoriteNumbers[4]").toInts();
// or index4FavoriteNumbers = jjson.k("favoriteNumbers").i(4).toInts();
String index1NumberPhoneNumber = jjson.q(".phoneNumbers[0].number").toStr();
String[] interests = jjson.q(".interests").toStrs();
int[] favoriteNumbers = jjson.q(".favoriteNumbers").toInts();
//sắp xếp mảng chuỗi
interests = jjson.q(".interests").sort().toStrs();
//sắp xếp mảng số giảm dần
favoriteNumbers = jjson.q(".favoriteNumbers").sort().reverse().toInts();
//số lớn nhất, nhỏ nhất, trung bình cộng
int max = jjson.q(".favoriteNumbers").max().toInts();
int min = jjson.q(".favoriteNumbers").min().toInts();
int avg = jjson.q(".favoriteNumbers").avg().toInt();
```
### Tạo json:
```java
//tạo json nhanh
JJson jjson = JJson.cre().obj(
    "firstName", "Thien",
    "lastName", "Pham",
    "phoneNumbers", JJson.cre().arr(
        JJson.cre().obj(
            "type", "home",
            "number", "0941661235"
        ),
        JJson.cre().obj(
             "type", "fax",
                "number", "0435508028"
         )
    ),
    "girlFriend", JJson.cre().obj(
        "name", "Thom",
        "age", 18
    ),
    "age", 20,
    "sex", "male",
    "salary", 1200.0
);
System.out.println(jjson);
//tạo json theo kiểu sử dụng put Map hoặc List
Map<Object, Object> m1Phone = new HashMap<>();
m1Phone.put("type", "home");
m1Phone.put("number", "0941661235");
Map<Object, Object> m2Phone = new HashMap<>();
m2Phone.put("type", "fax");
m2Phone.put("number", "0435508028");

List<Object> lphone = new ArrayList<>();
lphone.add(m1Phone);
lphone.add(m2Phone);

Map<Object, Object> mGirlFriend = new HashMap<>();
mGirlFriend.put("name", "Thom");
mGirlFriend.put("age", 18);

Map<Object, Object> mJson = new HashMap<>();
mJson.put("firstName", "Thien");
mJson.put("lastName", "Pham");
mJson.put("phoneNumbers", lphone);
mJson.put("girlFriend", mGirlFriend);
mJson.put("age", 20);
mJson.put("sex", "male");
mJson.put("salary", 1200.1);

JJson jjson = JJson.cre().put(mJson);
System.out.println(jjson);
```

## Lưu ý:
- không tìm thấy key hoặc index sẽ trả về Null
- bất cứ lỗi nào sảy ra sẽ trả về Null

## Cấu trúc:
|Type|Function|
|--------|---------|
|`static`|`parse(String json):JJson`|
|`static`|`parse(Object object):JJson`|
|`static`|`cre():JJson`|
|``constructor`|`(String json):JJson`
|``constructor`|`(Object object):JJson`
||`obj(Object... objs):JJson`|
||`arr(Object... objs):JJson`|
||`put(Map<Object, Object> map):JJson`|
||`put(List<Object> list):JJson`|
||`k(String key):JJson`|
||`i(int index):JJson`|
||`q(String query):JJson`|
||`min():JJson`|
||`max():JJson`|
||`sort():JJson`|
||`reverse():JJson`|
||`sum():JJson`|
||`avg():JJson`|
||`toObj():Object`|
||`toStr():String`|
||`toChar():char`|
||`toInt():int`|
||`toLong():long`|
||`toDouble():double`|
||`toFloat():float`|
||`toBolean():boolean`|
||`length():int`|
||`toPairObjs():JJson[][]`|
||`toKeys():String[]`|
||`toValues():JJson[]`|
||`toObjs():JJson[]`|
||`toStrs():String[]`|
||`toChars():char[]`|
||`toInts():int[]`|
||`toLongs():long[]`|
||`toDoubles():double[]`|
||`toFloats():float[]`|
||`toBooleans():boolean[]`|

## Cập nhật:
|Version|Changed|
|-------|-------|
|**2.0.1**|đổi `putObj(Object... objs):JJson` thành `obj(Object... objs):JJson`|
||đổi `putArr(Object... objs):JJson` thành `arr(Object... objs):JJson`|
||thêm `put(Map<Object, Object>)::JJson` 
||thêm `put(List<Object>)::JJson` |
|**2.0.0**|thêm `cre():JJson` để tạo mảng json (kết hợp `putObj` hoặc `putArr` để tạo) 
||thêm `putObj(Object... objs):JJson` tạo mảng object list `{a:1, b:2, ...}`
||thêm `putArr(Object... objs):JJson` tạo mảng giá trị `[1, 2, 3,...]`
|**1.0.6**|fix `sort():JJson` sắp sếp được cả chuỗi, và số (có lỗi trả về mảng 0)|
||fix `reverse():JJson` đảo được cả mảng chuỗi và số (có lỗi trả về mảng 0)|
||update lại có lỗi sảy ra trả về 0 thay vì -1 hoặc `'_'`|
||update lại `toString():String` tương đương `toStr():String`|
||add note vào các hàm (sử dụng tốt trên netbean)|
|**1.0.5**|thêm hàm `avg():JJson` trả về trung bình cộng một mảng (cần ép về giá trị gì thì dùng các hàm `Lấy giá trị` ở trên)|
||thêm hàm `sum():JJson` trả về tổng một mảng (cần ép về giá trị gì thì dùng các hàm `Lấy giá trị` ở trên)|
||thêm hàm `min():JJson` lấy số nhỏ nhất trong mảng (cần ép về giá trị gì thì dùng các hàm `Lấy giá trị` ở trên)|
||thêm hàm `max():JJson` lấy số lớn nhất trong mảng (cần ép về giá trị gì thì dùng các hàm `Lấy giá trị` ở trên)|
||thêm hàm `reverse():JJson` đảo ngược mảng số (cần ép về giá trị gì thì dùng các hàm `Lấy mảng giá trị` ở trên)|
||sửa lỗi không thể đẩy chuỗi số thực về số nguyên (fix toInt(), toLong(), toInts(), toLongs())|
|**1.0.4**|thêm các hàm lấy giá trị (`toChar():char`, `toLong():long`, `toFloat():float`)|
||thêm các hàm lấy mảng giá trị (`toChars():char[]`, `toLongs():long[]`, `toFloats():float[]`)|
||thêm hàm `toKeys():String[]` lấy tất cả key trong json|
||thêm hàm `toValues():JJson[]` lấy tất cả value trong json|
|**1.0.3.1**|fix lại function `q(String query):JJson` đọc json có mảng đầu tiên không lỗi|
|**1.0.3**|thêm function `q(String query):JJson` => đọc json nhanh hơn người yêu cũ trở mặt|
||đổi function `key(String key):JJson` => `k(String key):JJson`|
||đổi function `index(String index):JJson` => `i(String index):JJson`|
|**1.0.2**|bỏ variable `length:int` thay bằng function `length():int`|
||bỏ `toJsonStr():String` (sử dụng `toStr():String` hoặc `toString():String` thay thế)|
||cập nhật lại source|
|**1.0.1**|thêm biến `length:int` để kiểm tra độ dài mảng|
||fix lại 1 số hàm cho gọn nhẹ|
|**1.0.0**|Ra mắt `JJson.java`

## Thông tin:
- Tên:JJson
- Tác giả:Thiên Đẹp Zaii ( SystemError )
- Phiên bản:1.0.6
- Thư viện sử dụng:JSON SIMPLE
- Phiên bản thư viện:1.1.1