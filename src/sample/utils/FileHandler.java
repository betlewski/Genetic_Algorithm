package sample.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class FileHandler {

    public String destDir = "results/";
    public String destFile;

    public FileHandler() {
        new File(destDir).mkdir();
        setDestFilename();
    }

    private void setDestFilename() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        destFile = "ga_" + LocalDateTime.now().format(formatter) + ".data";
    }

    public void log(String line) {
        try {
            String filePath = destDir + destFile;
            Files.write(Paths.get(filePath), Collections.singleton(line), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
