package com.conquer_java.design_pattern.builder.build_house;

public class DemoBuildHouse {
    public static void main(String[] args) {
        Builder worker = new HouseBuilder();
        HouseConstructor constructor = new HouseConstructor();
        constructor.command(worker);
        House house = worker.buildHouse();
        System.out.println(house);
    }
}

interface Builder {
    void buildCeling();
    void buildDoor();
    void buildWindow();
    void buildFloor();

    House buildHouse();
}

class HouseBuilder implements Builder {
    private House house = new House();

    @Override
    public void buildCeling() {
        house.setCeiling(new Ceiling());
    }

    @Override
    public void buildDoor() {
        house.setDoor(new Door());
    }

    @Override
    public void buildWindow() {
        house.setWindow(new Window());
    }

    @Override
    public void buildFloor() {
        house.setFloor(new Floor());
    }

    @Override
    public House buildHouse() {
        return house;
    }
}

class HouseConstructor {
    public void command(Builder builder) { // 建造师根据情况，选择某些部分，按照某种顺序，遵循某种条件进行分步构建。
        builder.buildCeling();
        builder.buildFloor();
    }
}

class House {
    Ceiling ceiling;
    Door door;
    Window window;
    Floor floor;

    public Ceiling getCeiling() {
        return ceiling;
    }

    public void setCeiling(Ceiling ceiling) {
        this.ceiling = ceiling;
    }

    public Door getDoor() {
        return door;
    }

    public void setDoor(Door door) {
        this.door = door;
    }

    public Window getWindow() {
        return window;
    }

    public void setWindow(Window window) {
        this.window = window;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "House{" +
                "hashCode=" + hashCode() +
                ", ceiling='" + ((ceiling == null) ? null : ceiling.INFO) + '\'' +
                ", door='" + ((door == null) ? null : door.INFO) + "\'" +
                ", window='" + ((window == null) ? null : window.INFO) + "\'" +
                ", floor='" + ((floor == null) ? null : floor.INFO) + "\'" +
                '}';
    }
}

class Ceiling {
    String INFO = "CEILING";
}

class Door {
    String INFO = "DOOR";
}

class Window {
    String INFO = "WINDOW";
}

class Floor {
    String INFO = "FLOOR";
}
