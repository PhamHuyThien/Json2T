
import json2t.Json2T;

public class ExampleJson2T {

    public static void main(String[] args) {
        // đoạn string cần parse
        String json = "{\n"
                + "   \"firstName\":\"Vinh\",\n"
                + "   \"lastName\":\"Phan\",\n"
                + "   \"address\":{\n"
                + "      \"streetAddress\":\"11 Tu Lap\",\n"
                + "      \"district\":\"Me Linh\",\n"
                + "      \"city\":\"Ha Noi\",\n"
                + "      \"state\":\"\",\n"
                + "      \"postalCode\":\"100000\"\n"
                + "   },\n"
                + "   \"age\":25,\n"
                + "   \"phoneNumbers\":[\n"
                + "      {\n"
                + "         \"type\":\"home\",\n"
                + "         \"number\":\"096677028\"\n"
                + "      },\n"
                + "      {\n"
                + "         \"type\":\"fax\",\n"
                + "         \"number\":\"0435508028\"\n"
                + "      }\n"
                + "   ],\n"
                + "   \"sex\":\"Male\",\n"
                + "   \"salary\":1200.0,\n"
                + "   \"workDone\":[\n"
                + "      \"dev\",\n"
                + "      \"tester\",\n"
                + "      \"graber\",\n"
                + "      \"designer\"\n"
                + "   ]\n"
                + "}";
        

        //trên là 1 đoạn json cơ bản, chúng ta sẽ thực hiện ví dụ từ đoạn json trên
        //trước tiên cần khởi tạo Json2T, có 2 cách
        //c1: Json2T json2T = new Json2T(json);
        //c2: Json2T json2T = Json2T.parse(json);
        //cả 2 đều trả về Json2T, nhưng mình sẽ chọn cách 2 vì nó cho chúng ta tiết kiệm dòng code
        Json2T json2T = Json2T.parse(json);
        
        //VD1
        //để lấy firstname chúng ta làm như sau:
        String firstName = json2T.key("firstName").toStr();
        System.out.println("firstName: " + firstName);
        //vì firstName dạng chuỗi lên ta cần toStr()
        
        //VD2
        int age = json2T.key("age").toInt();
        System.out.println("Age: " + age);
        //nếu là dạng số chúng ta có thể ép kiểu nhanh .toInt() hoặc .toDouble()
        
        //VD3
        //dể lấy dữ liệu postalCode hoặc city trong address ta cần làm như sau:
        String city = json2T.key("address").key("city").toStr();
        int postalCode = json2T.key("address").key("postalCode").toInt();
        System.out.println("city: " + city + ", postalCode: " + postalCode);
        //các phần tử trong andress cũng làm tương tự 
        
        //VD4
        //để lấy được số điện thoại ta có 2 cách sau:
        //cách 1: biết được vị trí cần lấy
        String typeNumber1 = json2T.key("phoneNumbers").index(0).key("type").toStr();
        String phoneNumber1 = json2T.key("phoneNumbers").index(0).key("number").toStr();
        System.out.println("type: " + typeNumber1 + ", number: " + phoneNumber1);
        //cách 2: cần lấy cả mảng để làm gì đó
        Json2T[] phoneNumbers = json2T.key("phoneNumbers").toObjs();
        for (Json2T phoneNumber : phoneNumbers) {
            String typeNumber2 = phoneNumber.key("type").toStr();
            String phoneNumber2 = phoneNumber.key("number").toStr();
            System.out.println("type: " + typeNumber2 + ", number: " + phoneNumber2);
        }
        
        //VD5
        //lấy salary
        double salary = json2T.key("salary").toDouble();
        System.out.println("Salary: "+salary);
        
        //VD6
        //lấy danh sách workDone
        String[] workDones = json2T.key("workDone").toStrs();
        for (String workDone : workDones) {
            System.out.println("workDone: " + workDone);
        }
        //tương tự như trên nếu là mảng số có thể ép nhanh .toInts() .toDoubles() 
        //nếu là mảng trộn nhiều loại vd: [1, "a", {"c": 5}, 12.5] sử dụng .toObjs();
        //là mảng đối tượng cũng sử dụng .toObjs() như VD4 cách 2
        
        //VD7
        //lấy độ dài của mảng workDone
        int lengthWorkDone = json2T.key("workDone").length;
        System.out.println("length workDone: "+lengthWorkDone);
    }

}
