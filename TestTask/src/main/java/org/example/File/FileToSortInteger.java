package org.example.File;

import java.io.IOException;
import java.nio.file.Path;

public class FileToSortInteger extends FileToSort implements Comparable<FileToSort> {
    private int intCurrentValue;
    public FileToSortInteger(Path file, boolean asc) {
        super(file, asc);
    }
    public int getIntCurrentValue() {
        return intCurrentValue;
    }
    public boolean readCurrentValue() {
        currentValue ="";
        intCurrentValue = 0;
        try {
            if (isNotEmpty = buff.ready()) {
                currentValue = buff.readLine();
                intCurrentValue = Integer.parseInt(currentValue);
            }
        } catch (IOException e) {
            System.out.println("Не удалось прочитать очередную строку из файла " + file.toString());
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(FileToSort anotherFile) {
        int anotherCurrentValue = ((FileToSortInteger) anotherFile).getIntCurrentValue();
        if ((asc && intCurrentValue <= anotherCurrentValue) ||
            (!asc && intCurrentValue >= anotherCurrentValue)) {
            return 1;
        } else {return 0;}
    }
}
