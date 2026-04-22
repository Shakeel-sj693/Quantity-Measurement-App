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
    }

    public static void main(String[] args) {
        System.out.println(Length.convert(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
        System.out.println(Length.convert(3.0, Length.LengthUnit.YARDS, Length.LengthUnit.FEET));
        System.out.println(Length.convert(36.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS));
        System.out.println(Length.convert(1.0, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES));
    }

    public static class QuantityMeasurementAppTest {

        @Test
        public void testFeetToInches() {
            assertEquals(12.0, Length.convert(1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
        }

        @Test
        public void testInchesToFeet() {
            assertEquals(2.0, Length.convert(24.0, Length.LengthUnit.INCHES, Length.LengthUnit.FEET));
        }

        @Test
        public void testYardsToInches() {
            assertEquals(36.0, Length.convert(1.0, Length.LengthUnit.YARDS, Length.LengthUnit.INCHES));
        }

        @Test
        public void testInchesToYards() {
            assertEquals(2.0, Length.convert(72.0, Length.LengthUnit.INCHES, Length.LengthUnit.YARDS));
        }

        @Test
        public void testCentimeterToInch() {
            assertEquals(1.0, Length.convert(2.54, Length.LengthUnit.CENTIMETERS, Length.LengthUnit.INCHES), 1e-6);
        }

        @Test
        public void testFeetToYard() {
            assertEquals(2.0, Length.convert(6.0, Length.LengthUnit.FEET, Length.LengthUnit.YARDS));
        }

        @Test
        public void testRoundTrip() {
            double v = 5.0;
            double result = Length.convert(
                    Length.convert(v, Length.LengthUnit.FEET, Length.LengthUnit.INCHES),
                    Length.LengthUnit.INCHES,
                    Length.LengthUnit.FEET
            );
            assertEquals(v, result, 1e-6);
        }

        @Test
        public void testZero() {
            assertEquals(0.0, Length.convert(0.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
        }

        @Test
        public void testNegative() {
            assertEquals(-12.0, Length.convert(-1.0, Length.LengthUnit.FEET, Length.LengthUnit.INCHES));
        }

        @Test
        public void testInvalidInput() {
            assertThrows(IllegalArgumentException.class, () ->
                    Length.convert(Double.NaN, Length.LengthUnit.FEET, Length.LengthUnit.INCHES)
            );
        }
    }
}