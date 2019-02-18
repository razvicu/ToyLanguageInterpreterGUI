package Model.Utils;

import java.io.BufferedReader;

public class FilePair {
    private String fileName;
    private BufferedReader bufferedReader;

    public FilePair(String filename, BufferedReader bufferedReader) {
        this.fileName = filename;
        this.bufferedReader = bufferedReader;
    }

    public String getFileName() {
        return fileName;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    @Override
    public String toString() {
        return fileName + "\n";
    }
}
