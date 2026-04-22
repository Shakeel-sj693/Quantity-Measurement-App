package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

enum LengthUnit {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(1.0 / 30.48);

    private final double factor;

    LengthUnit(double factor) {
        this.factor = factor;
    }

    public double convertToBaseUnit(double value) {
        return value * factor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / factor;
    }
}

public class QuantityMeasurementApp {

    public static class Length {

        private final double value;
        private final LengthUnit unit;

        public Length(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException();
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBase() {
            return unit.convertToBaseUnit(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Length other = (Length) o;
            return Double.compare(this.toBase(), other.toBase()) == 0;
        }

        public Length convertTo(LengthUnit target) {
            if (target == null) {
                throw new IllegalArgumentException();
            }
            double base = this.toBase();
            double result = target.convertFromBaseUnit(base);
            return new Length(result, target);
        }

        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (source == null || target == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException();
            }
            double base = source.convertToBaseUnit(value);
            return target.convertFromBaseUnit(base);
        }

        public static Length add(Length l1, Length l2) {
            if (l1 == null || l2 == null) {
                throw new IllegalArgumentException();
            }
            double sumBase = l1.toBase() + l2.toBase();
            double result = l1.unit.convertFromBaseUnit(sumBase);
            return new Length(result, l1.unit);
        }

        public static Length add(Length l1, Length l2, LengthUnit target) {
            if (l1 == null || l2 == null || target == null) {
                throw new IllegalArgumentException();
            }
            double sumBase = l1.toBase() + l2.toBase();
            double result = target.convertFromBaseUnit(sumBase);
            return new Length(result, target);
        }
    }

    public static void main(String[] args) {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);

        System.out.println(l1.convertTo(LengthUnit.INCHES).value);
        System.out.println(Length.add(l1, l2, LengthUnit.FEET).value);
        System.out.println(l1.equals(new Length(12.0, LengthUnit.INCHES)));
    }

    public static class QuantityMeasurementAppTest {

        @Test
        public void testEquality() {
            Length l1 = new Length(1.0, LengthUnit.FEET);
            Length l2 = new Length(12.0, LengthUnit.INCHES);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testConvert() {
            Length l1 = new Length(1.0, LengthUnit.FEET);
            Length result = l1.convertTo(LengthUnit.INCHES);
            assertEquals(12.0, result.value);
        }

        @Test
        public void testAdd() {
            Length l1 = new Length(1.0, LengthUnit.FEET);
            Length l2 = new Length(12.0, LengthUnit.INCHES);
            Length result = Length.add(l1, l2, LengthUnit.FEET);
            assertEquals(2.0, result.value);
        }

        @Test
        public void testUnitConversionBase() {
            assertEquals(1.0, LengthUnit.INCHES.convertToBaseUnit(12.0));
        }

        @Test
        public void testFromBase() {
            assertEquals(12.0, LengthUnit.INCHES.convertFromBaseUnit(1.0));
        }
    }
}