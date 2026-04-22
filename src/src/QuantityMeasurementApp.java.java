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

        public static Length add(Length l1, Length l2, LengthUnit targetUnit) {
            if (l1 == null || l2 == null || targetUnit == null) {
                throw new IllegalArgumentException();
            }
            double sumBase = l1.toBaseUnit() + l2.toBaseUnit();
            double resultValue = sumBase / targetUnit.getFactor();
            return new Length(resultValue, targetUnit);
        }
    }

    public static void main(String[] args) {
        Length l1 = new Length(1.0, Length.LengthUnit.FEET);
        Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

        Length r1 = Length.add(l1, l2, Length.LengthUnit.FEET);
        System.out.println(r1.value + " " + r1.unit);

        Length r2 = Length.add(l1, l2, Length.LengthUnit.INCHES);
        System.out.println(r2.value + " " + r2.unit);

        Length r3 = Length.add(l1, l2, Length.LengthUnit.YARDS);
        System.out.println(r3.value + " " + r3.unit);
    }

    public static class QuantityMeasurementAppTest {

        @Test
        public void testAddFeetTarget() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
            Length r = Length.add(l1, l2, Length.LengthUnit.FEET);
            assertEquals(2.0, r.value);
        }

        @Test
        public void testAddInchesTarget() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
            Length r = Length.add(l1, l2, Length.LengthUnit.INCHES);
            assertEquals(24.0, r.value);
        }

        @Test
        public void testAddYardsTarget() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(12.0, Length.LengthUnit.INCHES);
            Length r = Length.add(l1, l2, Length.LengthUnit.YARDS);
            assertEquals(0.666666, r.value, 1e-6);
        }

        @Test
        public void testAddCentimeterTarget() {
            Length l1 = new Length(1.0, Length.LengthUnit.INCHES);
            Length l2 = new Length(1.0, Length.LengthUnit.INCHES);
            Length r = Length.add(l1, l2, Length.LengthUnit.CENTIMETERS);
            assertEquals(5.08, r.value, 1e-2);
        }

        @Test
        public void testCommutativity() {
            Length a = new Length(1.0, Length.LengthUnit.FEET);
            Length b = new Length(12.0, Length.LengthUnit.INCHES);

            Length r1 = Length.add(a, b, Length.LengthUnit.YARDS);
            Length r2 = Length.add(b, a, Length.LengthUnit.YARDS);

            assertEquals(r1.value, r2.value, 1e-6);
        }

        @Test
        public void testNullTarget() {
            Length l1 = new Length(1.0, Length.LengthUnit.FEET);
            Length l2 = new Length(12.0, Length.LengthUnit.INCHES);

            assertThrows(IllegalArgumentException.class, () ->
                    Length.add(l1, l2, null)
            );
        }
    }
}