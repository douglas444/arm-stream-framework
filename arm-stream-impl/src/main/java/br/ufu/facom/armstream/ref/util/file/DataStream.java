package br.ufu.facom.armstream.ref.util.file;

import br.ufu.facom.armstream.ref.util.datastructures.Sample;

import java.io.*;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public class DataStream {

    private final static String DEFAULT_SEPARATOR = ",";

    private final String separator;
    private final BufferedReader[] bufferedReaders;
    private Boolean labelIsNaN;
    private HashMap<String, Integer> labelEnumeration;

    private int currentFileIdx;
    private BufferedReader currentBufferedReader;

    private DataStream(final String[] filePaths, final String separator) throws FileNotFoundException {

        this.separator = separator;
        this.labelIsNaN = null;

        // Open all the files and create buffered readers for each one
        this.bufferedReaders = new BufferedReader[filePaths.length];
        for (int i = 0; i < filePaths.length; i++) {
            this.bufferedReaders[i] = new BufferedReader(new FileReader(filePaths[i]));
        }

        this.currentFileIdx = 0;
        this.currentBufferedReader = this.bufferedReaders[this.currentFileIdx];

    }

    public static DataStream from(final String[] filePaths, final String separator) throws FileNotFoundException {
        return new DataStream(filePaths, separator);
    }

    public static DataStream from(final String... filePaths) throws FileNotFoundException {
        return new DataStream(filePaths, DEFAULT_SEPARATOR);
    }

    public void forEach(final Consumer<Sample> consumer) throws IOException {
        Sample sample = this.next();
        while (sample != null) {
            consumer.accept(sample);
            sample = this.next();
        }
    }

    private Sample next() throws IOException, NumberFormatException {

        final String line = this.currentBufferedReader.readLine();

        if (line == null) {
            this.currentBufferedReader.close();
            this.currentFileIdx += 1;
            if (this.currentFileIdx < this.bufferedReaders.length) {
                this.currentBufferedReader = this.bufferedReaders[this.currentFileIdx];
                return this.next();
            } else {
                return null;
            }
        }

        final String[] splittedLine = line.split(this.separator);

        final int numberOfFeatures = splittedLine.length - 1;

        final double[] x = new double[numberOfFeatures];

        for (int i = 0; i < numberOfFeatures; ++i) {
            x[i] = Double.parseDouble(splittedLine[i]);
        }

        final String y = splittedLine[splittedLine.length - 1];

        if (this.labelIsNaN == null) {
            if (!isNumeric(y)) {
                this.labelIsNaN = true;
                this.labelEnumeration = new HashMap<>();
            } else {
                this.labelIsNaN = false;
            }
        }

        if (this.labelIsNaN) {
            labelEnumeration.putIfAbsent(y, labelEnumeration.size());
            return new Sample(x, labelEnumeration.get(y));
        } else {
            return new Sample(x, (int) Double.parseDouble(y));
        }

    }

    private boolean isNumeric(final String str) {

        final Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
        if (str == null) {
            return false;
        }
        return pattern.matcher(str).matches();
    }

}