package br.ufu.facom.armstream.api;

import java.util.HashMap;

public interface ArmParameterizableModule {

    HashMap<String, String> getNominalParameters();
    HashMap<String, Double> getNumericParameters();

}
