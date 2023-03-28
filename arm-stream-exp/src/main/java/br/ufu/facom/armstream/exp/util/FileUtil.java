package br.ufu.facom.armstream.exp.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class FileUtil {

    public static void writeToFile(final String content, final String destination, final String filePath)
            throws FileNotFoundException {

        final File file = new File(destination);
        if (!file.exists() && !file.mkdirs()) {
            throw new FileNotFoundException("Destination directory does not exists and could not be created.");
        }

        try (final PrintWriter out = new PrintWriter(new File(destination, filePath))) {
            out.println(content);
        }

    }

    public static String getPathForResource(final String name) throws FileNotFoundException {
        final URL url = FileUtil.class.getClassLoader().getResource(name);
        if (url == null) {
            throw new FileNotFoundException("File not found" + name);
        }

        URI uri;//Required to prevent the encoding of special characters
        try {
            uri = new URI(url.toString());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return uri.getPath();
    }

}
