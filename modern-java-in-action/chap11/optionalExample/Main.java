package chap11.optionalExample;

public class Main {
    public static void main(String[] args) {
        // 1. 보수적인 자세로 NullPointerException 줄이기.
        // 1) 깊은 의심 : 다양한 null 확인 코드 추가
        System.out.println(getCarInsuranceName1(new Person()));
        // 2) 너무 많은 출구
        System.out.println(getCarInsuranceName2(new Person()));
    }
    public static String getCarInsuranceName1(Person person) {
        // null 확인 코드 때문에 나머지 호출 체인의 들여쓰기 수준이 증가
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown";
    }
    public static String getCarInsuranceName2(Person person) {
        // null 확인 코드마다 출구가 생긴다.
        if (person == null) {
            return "Unknown";
        }
        Car car = person.getCar();
        if (car == null) {
            return "Unknown";
        }
        Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";
        }
        return insurance.getName();
    }
}
