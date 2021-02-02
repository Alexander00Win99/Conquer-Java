package com.conquer_java.algorithm.exercise_leetcode;

import java.util.Stack;

public class Leetcode_0155_MinStack {
    private Stack<Integer> dataStack;
    private Stack<Integer> minStack;

    public Leetcode_0155_MinStack() {
        this.dataStack = new Stack<Integer>();
        this.minStack = new Stack<Integer>();
    }

    public void push(int x) {
        dataStack.push(x);
        if (minStack.isEmpty()) {
            minStack.push(x);
        } else {
            if (x > minStack.peek()) {
                minStack.push(minStack.peek());
            } else {
                minStack.push(x);
            }
        }
    }

    public void pop() {
        dataStack.pop();
        minStack.pop();
    }

    public int top() {
        return dataStack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
