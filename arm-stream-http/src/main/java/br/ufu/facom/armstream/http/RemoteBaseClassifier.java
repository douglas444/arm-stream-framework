package br.ufu.facom.armstream.http;

import br.ufu.facom.armstream.api.interceptor.ArmInterceptor;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class RemoteBaseClassifier {

    private final String remoteBaseClassifierUrl;

    public RemoteBaseClassifier(final String remoteBaseClassifierUrl) {
        this.remoteBaseClassifierUrl = remoteBaseClassifierUrl;
    }

    public void run(final HashMap<String, String> parameters,
                    final ArmInterceptor interceptor,
                    final Consumer<HashMap<String, String>> peeker,
                    final int interceptorServerPort) {


        final InterceptorService interceptorService = InterceptorService.getInstance();
        interceptorService.setInterceptor(interceptor);
        interceptorService.setPeeker(peeker);

        final String interceptorServerUrl = InterceptorServer.start(interceptorServerPort);

        if (this.remoteBaseClassifierUrl == null) {
            throw new IllegalStateException("remoteBaseClassifierUrl is null");
        }

        final Client client = ClientBuilder.newClient();
        final WebTarget target = client.target(this.remoteBaseClassifierUrl)
                .queryParam("interceptor_base_url", interceptorServerUrl);

        System.out.println("Requesting start of remote base classifier with parameters: " +
                parameters.keySet()
                        .stream()
                        .map(key -> key + "=" + parameters.get(key))
                        .collect(Collectors.joining(", ", "{", "}")));

        final Response response = target.request().post(Entity.json(parameters));

        if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
            throw new RuntimeException("Request to remote classifier failed\n" + response);
        }
        System.out.println("Request to remote classifier succeeded.");
        System.out.println("Waiting for callback requests from the remote base-classifier.");

        response.close();

        try {
            interceptorService.getLatch().await();
        } catch (final InterruptedException e) {
            throw new RuntimeException(e);
        }

        InterceptorServer.stop();

    }
}
