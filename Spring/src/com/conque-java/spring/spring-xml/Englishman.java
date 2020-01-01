import java.util.Map;

public class Englishman implements Human {
    private String name;
    private int age;
    private boolean sex;
    private Map<Integer, Car> carMap;

    public Englishman() {
        System.out.println("I am englishman!");
    }

    public Englishman(String name, int age, boolean sex, Map<Integer, Car> carMap) {
        System.out.println("F**k U!");
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.carMap = carMap;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Map<Integer, Car> getCarMap() {
        return carMap;
    }

    public void setCarMap(Map<Integer, Car> carMap) {
        this.carMap = carMap;
    }

    @Override
    public String toString() {
        return "Englishman{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", carMap=" + carMap +
                '}';
    }

    @Override
    public void greet(String name) {
        String str = "Hello, " + name + "!";
        System.out.println(str);
    }

    @Override
    public void selfIntroduce() {
        System.out.println("I am from Britain, my name is " + name + ", and I am " + age + " years old.");
    }
}
