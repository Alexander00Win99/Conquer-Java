//package com.conque_java.design_pattern.example_spring;
//
//import java.util.Objects;
//
//public class DemoPrototype {
//    public static void main(String[] args) {
//        ProductPrototype product = new ProductPrototype();
//        product.setPart1("part1");
//        product.setPart2("part2");
//        product.setPart3("part3");
//        product.setPart4("part4");
//
//        ProductPrototype clone = (ProductPrototype) product.clone();
//        System.out.println("origin: " + product);
//        System.out.println("clone: " + clone);
//    }
//}
//
//class ProductPrototype implements Cloneable {
//    private String part1;
//    private String part2;
//    private String part3;
//    private String part4;
//
//    public String getPart1() {
//        return part1;
//    }
//
//    public String getPart2() {
//        return part2;
//    }
//
//    public String getPart3() {
//        return part3;
//    }
//
//    public String getPart4() {
//        return part4;
//    }
//
//    public void setPart1(String part1) {
//        this.part1 = part1;
//    }
//
//    public void setPart2(String part2) {
//        this.part2 = part1;
//    }
//
//    public void setPart3(String part3) {
//        this.part3 = part1;
//    }
//
//    public void setPart4(String part4) {
//        this.part4 = part4;
//    }
//
//    @Override
//    ProductPrototype clone() {
//
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ProductPrototype that = (ProductPrototype) o;
//        return Objects.equals(part1, that.part1) &&
//                Objects.equals(part2, that.part2) &&
//                Objects.equals(part3, that.part3) &&
//                Objects.equals(part4, that.part4);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(part1, part2, part3, part4);
//    }
//
//    @Override
//    public String toString() {
//        return "ProductPrototype{" +
//                "part1='" + part1 + '\'' +
//                ", part2='" + part2 + '\'' +
//                ", part3='" + part3 + '\'' +
//                ", part4='" + part4 + '\'' +
//                '}';
//    }
//}
//
