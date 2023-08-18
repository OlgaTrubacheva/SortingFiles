package org.example.File;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public abstract class FileToSort {
    protected BufferedReader buff;
    protected Path file;
    protected boolean isNotEmpty;
    protected boolean asc;
    protected String currentValue;

    public FileToSort(Path file, boolean asc) {
        this.file = file;
        this.asc = asc;
    }
    public boolean getIsNotEmpty() {
        return isNotEmpty;
    }
    public String getCurrentValue() {
        return currentValue;
    }
    public Path getFile() {
        return file;
    }

    public void createStream() throws IOException {
        buff = new BufferedReader(new FileReader(file.toFile()));
        isNotEmpty = buff.ready();
    }
    public void closeStream() {
        try {
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public abstract boolean readCurrentValue();
    public abstract int compareTo(FileToSort anotherFile);
}
