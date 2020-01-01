import java.util.List;

public class Chinese implements Human {
    private String name;
    private int age;
    private boolean sex;
    private List<Car> carList;

    public Chinese() {
        System.out.println("我是中国人！");
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

    public List<Car> getCarList() {
        return carList;
    }

    public void setCarList(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public String toString() {
        return "Chinese{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", carList=" + carList +
                '}';
    }

    @Override
    public void greet(String name) {
        String str = "你好，" + name + "！";
        System.out.println(str);
    }

    @Override
    public void selfIntroduce() {
        System.out.println("我来自中国, 我的名字是" + name + "，我今年" + age + "岁了。");
    }
}
