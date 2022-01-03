package com.example.callbackex;

import java.io.File;

public interface CopyProgressListener {

    void copyFileBegin(File fFromFile, long fileTotalLength);
    void copyFileProgress(long fileTotalLength, long copyTotalLength);
    void copyFileEnd(long fileTotalLength);
    void copyFileException(File fFromFile, Exception e);

}
