package ru.nsu.oop;

public class Main {
    public static void main(String[] args) {
        Expression a = new Number(5);
        a.print();

        Expression b = new Add(new Number(1), new Add(new Number(3), new Number(2)));
        b.print();

        Expression e = new Add(new Number(3), new Mul(new Number(2), new Variable("x")));
        int result = e.eval("x = 10; y = 13");
        System.out.println(result);
    }
}