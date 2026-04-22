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
                throw new IllegalArgumentException();
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

    public static void main(String[] args) {
        Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
        Length l2 = new Length(3.0, Length.LengthUnit.FEET);

        System.out.println(l1.equals(l2));

        Length l3 = new Length(1.0, Length.LengthUnit.CENTIMETERS);
        Length l4 = new Length(0.393701, Length.LengthUnit.INCHES);

        System.out.println(l3.equals(l4));
    }

    public static class QuantityMeasurementAppTest {

        @Test
        public void testYardToYardSameValue() {
            Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
            Length l2 = new Length(1.0, Length.LengthUnit.YARDS);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testYardToFeetEquivalent() {
            Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
            Length l2 = new Length(3.0, Length.LengthUnit.FEET);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testYardToInchesEquivalent() {
            Length l1 = new Length(1.0, Length.LengthUnit.YARDS);
            Length l2 = new Length(36.0, Length.LengthUnit.INCHES);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testCentimeterToInchEquivalent() {
            Length l1 = new Length(1.0, Length.LengthUnit.CENTIMETERS);
            Length l2 = new Length(0.393701, Length.LengthUnit.INCHES);
            assertTrue(l1.equals(l2));
        }

        @Test
        public void testFeetToCentimeterNotEqual() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(1.0, Length.LengthUnit.CENTIMETERS);
            assertFalse(l1.equals(l2));
        }

        @Test
        public void testSameReference() {
            Length l1 = new Length(2.0, Length.LengthUnit.YARDS);
            assertTrue(l1.equals(l1));
        }

        @Test
        public void testNullComparison() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            assertFalse(l1.equals(null));
        }

        @Test
        public void testTransitiveProperty() {
            Length a = new Length(1.0, Length.LengthUnit.YARDS);
            Length b = new Length(3.0, Length.LengthUnit.FEET);
            Length c = new Length(36.0, Length.LengthUnit.INCHES);

            assertTrue(a.equals(b));
            assertTrue(b.equals(c));
            assertTrue(a.equals(c));
        }
    }
}