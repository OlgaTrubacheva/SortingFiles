package org.example.File;

import java.io.IOException;
import java.nio.file.Path;

public class FileToSortString extends FileToSort implements Comparable<FileToSort> {

    public FileToSortString(Path file, boolean asc) {
        super(file, asc);
    }
    public boolean readCurrentValue() {
        try {
            if (isNotEmpty = buff.ready()) {
                currentValue = buff.readLine();
                if (currentValue.contains(" ")) {
                    currentValue = "";
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("Не удалось прочитать очередную строку из файла " + file.toString());
            currentValue = "";
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(FileToSort anotherFile) {
        String anotherCurrentValue = anotherFile.getCurrentValue();
        if ((asc && currentValue.compareTo(anotherCurrentValue) <= 0 ) ||
                (!asc && currentValue.compareTo(anotherCurrentValue) >= 0)) {
            return 1;
        } else {return 0;}
    }
}