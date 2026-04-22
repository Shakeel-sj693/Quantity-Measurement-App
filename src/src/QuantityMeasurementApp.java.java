package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementApp {

    public static class Length {

        private final double value;
        private final LengthUnit unit;

        public enum LengthUnit {
            FEET(12.0),
            INCHES(1.0),
            YARDS(36.0),
            CENTIMETERS(0.393701);

            private final double factor;

            LengthUnit(double factor) {
                this.factor = factor;
            }

            public double getFactor() {
                return factor;
            }
        }

        public Length(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException();
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return value * unit.getFactor();
        }

        public boolean compare(Length other) {
            if (other == null) return false;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return compare((Length) o);
        }

        public static double convert(double value, LengthUnit source, LengthUnit target) {
            if (source == null || target == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException();
            }
            double base = value * source.getFactor();
            return base / target.getFactor();
        }

        public double convertTo(LengthUnit target) {
            return convert(this.value, this.unit, target);
        }

        public static Length add(Length l1, Length l2) {
            if (l1 == null || l2 == null) {
                throw new IllegalArgumentException();
            }
            double sumBase = l1.toBaseUnit() + l2.toBaseUnit();
            double resultValue = sumBase / l1.unit.getFactor();
            return new Length(resultValue, l1.unit);
        }

        public Length add(Length other) {
            return add(this, other);
        }
    }

    public static void main(String[] args) {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
        Length result = l1.add(l2);

        System.out.println(result.value + " " + result.unit);

        Length l3 = new Length(2.54, Length.LengthUnit.CENTIMETERS);
        Length l4 = new Length(1.0, Length.LengthUnit.INCHES);
        Length result2 = l3.add(l4);

        System.out.println(result2.value + " " + result2.unit);
    }

    public static class QuantityMeasurementAppTest {

        @Test
        public void testFeetPlusFeet() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(2.0, Length.LengthUnit.FEET);
            Length result = Length.add(l1, l2);
            assertEquals(3.0, result.value);
        }

        @Test
        public void testFeetPlusInches() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
            Length result = Length.add(l1, l2);
            assertEquals(2.0, result.value);
        }

        @Test
        public void testInchesPlusFeet() {
            Length l1 = new Length(12.0, Length.LengthUnit.INCHES);
            Length l2 = new Length(1.0, Length.LengthUnit.FEET);
            Length result = Length.add(l1, l2);
            assertEquals(24.0, result.value);
        }

        @Test
        public void testYardPlusFeet() {
            Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
            Length l2 = new Length(3.0, Length.LengthUnit.FEET);
            Length result = Length.add(l1, l2);
            assertEquals(2.0, result.value);
        }

        @Test
        public void testCmPlusInch() {
            Length l1 = new Length(2.54, Length.LengthUnit.CENTIMETERS);
            Length l2 = new Length(1.0, Length.LengthUnit.INCHES);
            Length result = Length.add(l1, l2);
            assertEquals(5.08, result.value, 1e-6);
        }

        @Test
        public void testZero() {
            Length l1 = new Length(5.0, Length.LengthUnit.FEET);
            Length l2 = new Length(0.0, Length.LengthUnit.INCHES);
            Length result = Length.add(l1, l2);
            assertEquals(5.0, result.value);
        }

        @Test
        public void testNegative() {
            Length l1 = new Length(5.0, Length.LengthUnit.FEET);
            Length l2 = new Length(-2.0, Length.LengthUnit.FEET);
            Length result = Length.add(l1, l2);
            assertEquals(3.0, result.value);
        }

        @Test
        public void testNull() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            assertThrows(IllegalArgumentException.class, () -> Length.add(l1, null));
        }
    }
}