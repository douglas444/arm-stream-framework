package br.ufu.facom.armstream.ref.cdscal;

import br.ufu.facom.armstream.api.interceptor.ArmInterceptor;
import br.ufu.facom.armstream.api.modules.ArmBaseClassifier;
import br.ufu.facom.armstream.http.RemoteBaseClassifier;

import java.util.HashMap;
import java.util.function.Consumer;

public class ArmCdscal implements ArmBaseClassifier, Cloneable {

    private String remoteBaseClassifierUrl;
    private String datasetFilename;
    private String module;
    private int normalize;
    private int bufferSize;

    @Override
    public void run(ArmInterceptor interceptor, Consumer<HashMap<String, String>> peeker) {
        RemoteBaseClassifier remoteBaseClassifier = new RemoteBaseClassifier(this.remoteBaseClassifierUrl);
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("dataset_filename", this.datasetFilename);
        parameters.put("module", this.module);
        parameters.put("buffer_size", String.valueOf(this.bufferSize));
        parameters.put("normalize", String.valueOf(this.normalize));
        remoteBaseClassifier.run(parameters, interceptor, peeker, 8080);
    }

    @Override
    public ArmCdscal clone() {
        try {
            return (ArmCdscal) super.clone();
        } catch (final CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public ArmCdscal withRemoteBaseClassifierUrl(final String datasetFilename) {
        final ArmCdscal clone = this.clone();
        clone.remoteBaseClassifierUrl = datasetFilename;
        return clone;
    }

    public ArmCdscal withDatasetFileName(final String remoteBaseClassifierUrl) {
        final ArmCdscal clone = this.clone();
        clone.datasetFilename = remoteBaseClassifierUrl;
        return clone;
    }

    public ArmCdscal withModule(final String module) {
        final ArmCdscal clone = this.clone();
        clone.module = module;
        return clone;
    }

    public ArmCdscal withBufferSize(final int bufferSize) {
        final ArmCdscal clone = this.clone();
        clone.bufferSize = bufferSize;
        return clone;
    }

    public ArmCdscal withNormalize(final int normalize) {
        final ArmCdscal clone = this.clone();
        clone.normalize = normalize;
        return clone;
    }

    //Getters

    public String getRemoteBaseClassifierUrl() {
        return remoteBaseClassifierUrl;
    }

    public String getDatasetFilename() {
        return datasetFilename;
    }

    public String getModule() {
        return module;
    }

    public int getNormalize() {
        return normalize;
    }

    public int getBufferSize() {
        return bufferSize;
    }
}
