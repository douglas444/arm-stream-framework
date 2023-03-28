package br.ufu.facom.armstream.exp.experiments;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class Util {


    public static Consumer<HashMap<String, String>> configurePeeker(final List<HashMap<String, String>> peekedData) {

        return properties -> {

            peekedData.add(properties);

            final String log = properties
                    .entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue() + ";")
                    .reduce("", (s1, s2) -> s1 + " " + s2);

            System.out.println(log);

        };
    }

}
