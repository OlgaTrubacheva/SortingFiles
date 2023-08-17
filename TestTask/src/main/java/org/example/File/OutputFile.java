package org.example.File;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class OutputFile {
    private BufferedWriter buff;
    private Path file;
    public OutputFile(Path file) {
        this.file = file;
    }

    public void createStream() throws IOException {
        buff = new BufferedWriter(new FileWriter(file.toFile()));
    }
    public void writeValue(String value) {
        try {
            buff.write(value);
            buff.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void closeStream() {
        try {
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
