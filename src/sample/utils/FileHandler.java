package sample.utils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class FileHandler {

    private static final String DIR_PATH = "results/";
    private String filePath;

    public FileHandler() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        filePath = DIR_PATH + "ga_" + LocalDateTime.now().format(formatter) + ".txt";
        try {
            new File(DIR_PATH).mkdir();
            new File(filePath).createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String line) {
        try {
            Files.write(Paths.get(filePath), Collections.singleton(line),
                    StandardCharsets.UTF_8, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openFile() {
        try {
            File file = new File(filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (file.exists())
                    desktop.open(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
