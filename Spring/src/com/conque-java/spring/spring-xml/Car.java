/**
 * 车型分类：
 * Coupe: 双座四轮(双门轿跑)
 * Hatchback: 两厢(ABC三个立柱)
 * Estate(Station Wagon): 两厢(ABCD四个立柱)
 * Cabriolet(Convertible): 敞篷轿车(2、4、5座)
 * Sedan(Saloon): 三厢
 * Fastback: 挡风玻璃后备箱盖一体
 * Pickup: 皮卡
 * SUV(Sport Utility Vehicle): 运动型多功能汽车
 * MPV(Multi-Purpose Vehicle): 多用途车
 * Off-Roader: 越野车
 * FCEV(Fuel Cell Electric Vehicle): 燃料电池电动汽车
 * HEV(Hybrid Electric Vehicle): 混合动力汽车
 */
public class Car {
    private String band;
    private String type;
    private String color;

    public void setBand(String band) {
        this.band = band;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
