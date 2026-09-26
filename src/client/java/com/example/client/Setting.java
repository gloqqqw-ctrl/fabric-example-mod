package com.example.client;

public class Setting {
    private final String name;
    private double value;
    private final double minimum;
    private final double maximum;
    private final double step;

    public Setting(String name, double value, double minimum, double maximum, double step) {
        this.name = name;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
        this.step = step;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = Math.max(minimum, Math.min(maximum, value));
    }

    public void increase() {
        setValue(value + step);
    }

    public void decrease() {
        setValue(value - step);
    }

    public double getMinimum() {
        return minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public double getStep() {
        return step;
    }
}
