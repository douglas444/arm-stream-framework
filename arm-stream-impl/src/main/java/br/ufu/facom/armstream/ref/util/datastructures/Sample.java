package br.ufu.facom.armstream.ref.util.datastructures;

import java.util.*;

public class Sample {

    private long t;
    private final double[] x;
    private final Integer y;

    private Integer id;

    public Sample(final double[] x, final Integer y) {
        this.x = x.clone();
        this.y = y;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Sample sample = (Sample) o;
        return t == sample.t &&
                Arrays.equals(x, sample.x) &&
                Objects.equals(y, sample.y);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.t, this.y);
        result = 31 * result + Arrays.hashCode(this.x);
        return result;
    }

    public double distance(final Sample sample) {
        return distance(this, sample);
    }

    public static double distance(final Sample sample1, final Sample sample2) {
        return distance(sample1.getX(), sample2.getX());
    }

    public static double distance(final double[] x1, final double[] x2) {
        double sum = 0;
        for (int i = 0; i < x1.length; ++i) {
            sum += (x1[i] - x2[i]) * (x1[i] - x2[i]);
        }
        return Math.sqrt(sum);
    }

    public void sum(final Sample sample) {

        for (int i = 0; i < this.x.length; ++i) {
            this.x[i] += sample.getX()[i];
        }

    }

    public void divide(final double scalar) {

        for (int i = 0; i < this.x.length; ++i) {
            this.x[i] /= scalar;
        }

    }

    public Sample copy() {

        final Sample sample = new Sample(new double[this.x.length], this.y);
        for (int i = 0; i < this.x.length; ++i) {
            sample.getX()[i] = this.x[i];
        }
        return sample;

    }

    public Sample calculateClosestSample(final List<Sample> samples) {

        if (samples.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return samples
                .stream()
                .min(Comparator.comparing(this::distance))
                .orElseGet(() -> samples.get(0));
    }

    public long getT() {
        return t;
    }

    public void setT(final long t) {
        this.t = t;
    }

    public double[] getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }
}
