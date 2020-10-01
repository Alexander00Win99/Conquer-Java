package com.conquer_java.knowledge.inner_class;

public class Outer {
    private String description = "Outer class!";

    private void help() {
        System.out.println("This is " + description + " in Outer class!");
    }

    private Inner getInner() {
        return new Inner();
    }

    public class Inner {
        public Inner() {
            description = "Inner class!"; // 外围类内部类对象在创建时，必定会捕获一个指向外围类对象的引用，因此，无论外围类的field是否private，都可使用这个引用访问外围类的成员。
        }

        public void help() {
            System.out.println("This is " + description + " in Inner class!");
            Outer.this.help();
        }

        public Outer getOuter() {
            System.out.println(Outer.this.getClass().getName());
            System.out.println(Outer.this instanceof Outer);
            System.out.println(this.equals(Outer.this));
            return Outer.this; // 内部类可以使用Outer.this引用指向外部类对象
        }
    }

    public static void main(String[] args) {
        Outer outer = new Outer();
        Inner inner = outer.new Inner();
        //Outer.Inner inner = outer.new Inner(); // Inner inner = outer.new Inner();同样OK；Inner inner = new Outer.Inner();报错：“this cannot be referenced from a static context”
        System.out.println(inner instanceof Inner);
        inner.help();
        inner.getOuter();
        Inner in = outer.getInner();
        System.out.println(in instanceof Inner);
    }
}
