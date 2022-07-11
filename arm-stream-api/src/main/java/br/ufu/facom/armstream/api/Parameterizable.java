package br.ufu.facom.armstream.api;

import java.util.HashMap;

interface Parameterizable {
    HashMap<String, String> getNominalParameters();
    HashMap<String, Double> getNumericParameters();
}
