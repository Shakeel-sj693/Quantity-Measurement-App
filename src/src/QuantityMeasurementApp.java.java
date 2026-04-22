package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementApp {

    public static class Length {

        private final double value;
        private final LengthUnit unit;

        public enum LengthUnit {
            FEET(12.0),
            INCHES(1.0);

            private final double conversionFactor;

            LengthUnit(double conversionFactor) {
                this.conversionFactor = conversionFactor;
            }

            public double getConversionFactor() {
                return conversionFactor;
            }
        }

        public Length(double value, LengthUnit unit) {
            if (unit == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toBaseUnit() {
            return this.value * this.unit.getConversionFactor();
        }

        public boolean compare(Length other) {
            if (other == null) return false;
            return Double.compare(this.toBaseUnit(), other.toBaseUnit()) == 0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Length other = (Length) o;
            return compare(other);
        }
    }

    public static boolean demonstrateLengthEquality(Length l1, Length l2) {
        return l1.equals(l2);
    }

    public static void demonstrateFeetEquality() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(1.0, Length.LengthUnit.FEET);
        System.out.println("Feet Equal: " + l1.equals(l2));
    }

    public static void demonstrateInchesEquality() {
        Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
        Length l2 = new Length(1.0, Length.LengthUnit.INCHES);
        System.out.println("Inches Equal: " + l1.equals(l2));
    }

    public static void demonstrateFeetInchesComparison() {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
        System.out.println("Feet == Inches: " + l1.equals(l2));
    }

    public static void main(String[] args) {
        demonstrateFeetEquality();
        demonstrateInchesEquality();
        demonstrateFeetInchesComparison();
    }

    public static class QuantityMeasurementAppTest {

        @Test
        public void testFeetEquality() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(1.0, Length.LengthUnit.FEET);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testInchesEquality() {
            Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
            Length l2 = new Length(1.0, Length.LengthUnit.INCHES);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testFeetInchesComparison() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testFeetInequality() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(2.0, Length.LengthUnit.FEET);
            assertFalse(l1.equals(l2));
        }

        @Test
        public void testInchesInequality() {
            Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
            Length l2 = new Length(2.0, Length.LengthUnit.INCHES);
            assertFalse(l1.equals(l2));
        }

        @Test
        public void testCrossUnitInequality() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(10.0, Length.LengthUnit.INCHES);
            assertFalse(l1.equals(l2));
        }

        @Test
        public void testSameReference() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            assertTrue(l1.equals(l1));
        }

        @Test
        public void testNullComparison() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            assertFalse(l1.equals(null));
        }
    }
}