package util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TxtToStringConvertor {
    public static String readFile(String path){
        String pathAnd = "src/main/resources/" + path;
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get(pathAnd));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, Charset.defaultCharset());
    }
}
