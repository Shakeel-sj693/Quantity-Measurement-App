package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementApp {

    public static class Feet {
        private final double value;

        public Feet(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Feet other = (Feet) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    public static class Inches {
        private final double value;

        public Inches(double value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Inches other = (Inches) obj;
            return Double.compare(this.value, other.value) == 0;
        }
    }

    public static void demonstrateFeetEquality() {
        Feet f1 = new Feet(1.0);
        Feet f2 = new Feet(1.0);

        System.out.println("Feet Equal: " + f1.equals(f2));
    }

    public static void demonstrateInchesEquality() {
        Inches i1 = new Inches(1.0);
        Inches i2 = new Inches(1.0);

        System.out.println("Inches Equal: " + i1.equals(i2));
    }

    public static void main(String[] args) {
        demonstrateFeetEquality();
        demonstrateInchesEquality();
    }

    public static class QuantityMeasurementAppTest {

        @Test
        public void testFeetEquality_SameValue() {
            Feet f1 = new Feet(1.0);
            Feet f2 = new Feet(1.0);
            assertTrue(f1.equals(f2));
        }

        @Test
        public void testFeetEquality_DifferentValue() {
            Feet f1 = new Feet(1.0);
            Feet f2 = new Feet(2.0);
            assertFalse(f1.equals(f2));
        }

        @Test
        public void testFeetEquality_NullComparison() {
            Feet f1 = new Feet(1.0);
            assertFalse(f1.equals(null));
        }

        @Test
        public void testFeetEquality_DifferentClass() {
            Feet f1 = new Feet(1.0);
            Inches i1 = new Inches(1.0);
            assertFalse(f1.equals(i1));
        }

        @Test
        public void testFeetEquality_SameReference() {
            Feet f1 = new Feet(1.0);
            assertTrue(f1.equals(f1));
        }

        @Test
        public void testInchesEquality_SameValue() {
            Inches i1 = new Inches(1.0);
            Inches i2 = new Inches(1.0);
            assertTrue(i1.equals(i2));
        }

        @Test
        public void testInchesEquality_DifferentValue() {
            Inches i1 = new Inches(1.0);
            Inches i2 = new Inches(2.0);
            assertFalse(i1.equals(i2));
        }

        @Test
        public void testInchesEquality_NullComparison() {
            Inches i1 = new Inches(1.0);
            assertFalse(i1.equals(null));
        }

        @Test
        public void testInchesEquality_DifferentClass() {
            Inches i1 = new Inches(1.0);
            Feet f1 = new Feet(1.0);
            assertFalse(i1.equals(f1));
        }

        @Test
        public void testInchesEquality_SameReference() {
            Inches i1 = new Inches(1.0);
            assertTrue(i1.equals(i1));
        }
    }
}