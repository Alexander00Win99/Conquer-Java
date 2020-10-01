package com.conquer_java.pojo;

enum EnumAbstractWeek {
    SUNDAY(0, "星期日") {
        @Override
        public EnumAbstractWeek getNextDay() {
            return MONDAY;
        }
    },
    MONDAY(1, "星期一") {
        @Override
        public EnumAbstractWeek getNextDay() {
            return TUESDAY;
        }
    },
    TUESDAY(2, "星期二") {
        @Override
        public EnumAbstractWeek getNextDay() {
            return WEDNESDAY;
        }
    },
    WEDNESDAY(3, "星期三") {
        @Override
        public EnumAbstractWeek getNextDay() {
            return THURSDAY;
        }
    },
    THURSDAY(4, "星期四") {
        @Override
        public EnumAbstractWeek getNextDay() {
            return FRIDAY;
        }
    },
    FRIDAY(5, "星期五") {
        @Override
        public EnumAbstractWeek getNextDay() {
            return SATURDAY;
        }
    },
    SATURDAY(6, "星期六") {
        @Override
        public EnumAbstractWeek getNextDay() {
            return SUNDAY;
        }
    };

    private int index;
    private String description;

    /**
     * 构造方法默认是private
     * @param index
     * @param description
     */
    EnumAbstractWeek(int index, String description) {
        this.index = index;
        this.description = description;
    }

    public String toString() {
        switch (this) {
            case SUNDAY:
                return "今天是礼拜日";
            case MONDAY:
                return "今天是星期一";
            case TUESDAY:
                return "今天是星期二";
            case WEDNESDAY:
                return "今天是星期三";
            case THURSDAY:
                return "今天是星期四";
            case FRIDAY:
                return "今天是星期五";
            case SATURDAY:
                return "今天是礼拜六";
            default:
                return "输入错误";
        }
    }

    // 定义一个抽象方法
    public abstract EnumAbstractWeek getNextDay();

    public static void main(String[] args) {
        // values()是编译生成的，返回包含所有枚举实例的数组。
        EnumAbstractWeek[] values = EnumAbstractWeek.values();
        System.out.println("values(): " + values);
        for (EnumAbstractWeek day : values) {
            System.out.println(day.name());
            System.out.println(day.toString());
        }

        EnumAbstractWeek sunday = EnumAbstractWeek.SUNDAY;
        System.out.println(sunday.name());
        System.out.println(sunday.ordinal());

        EnumAbstractWeek saturday = EnumAbstractWeek.valueOf(EnumAbstractWeek.class, "SATURDAY");
        System.out.println(saturday.name());
        System.out.println(saturday.ordinal());

        System.out.println("【礼拜日 compareTo 礼拜六】：" + sunday.compareTo(saturday));

        String nextDay = EnumAbstractWeek.SATURDAY.getNextDay().toString();
        System.out.println(nextDay);
    }
}
