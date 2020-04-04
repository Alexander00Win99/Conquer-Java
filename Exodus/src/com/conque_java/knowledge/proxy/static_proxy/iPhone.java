package com.conque_java.knowledge.proxy.static_proxy;

public class iPhone implements Phone {
    static String GENERATION_8 = "iPhone 8";
    static String GENERATION_X = "iPhone X";
    static String GENERATION_11 = "iPhone 11";
    private String generation;
    private Color color;

    public iPhone(String generation, Color color) {
        this.generation = generation;
        this.color = color;
        System.out.println("这是一台" + color.getName() + "颜色的" + generation + "苹果手机！");
    }

    static enum Color {
        WHITE("象牙白", 1), GOLDEN("土豪金", 2), BLACK("暗夜黑", 3);

        private String name;
        private int index;

        private Color(String name, int idex) {
            this.name = name;
            this.index = index;
        }

        public static String getName(int index) {
            for (Color color : Color.values())
                if (color.getIndex() == index)
                    return color.getName(); // color.name亦可
            return null;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    @Override
    public String toString() {
        return "iPhone{" +
                "generation='" + generation + '\'' +
                ", color=" + color +
                '}';
    }

    @Override
    public void makeCall() {
        System.out.println("苹果手机打电话语音清晰过程流畅");
    }

    @Override
    public void takePhoto() {
        System.out.println("苹果手机拍照片画面逼真色彩绚丽");
    }

    @Override
    public void playGame() {
        System.out.println("苹果手机打游戏场景壮阔渲染充分");
    }
}
