package com.example.callbackex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyPaste {


    /**
     * from에서 to으로의 파일 복사
     * <p>
     * param from
     * param to
     * return returnValue
     */

    public static boolean filecopy( String from, String to) {
        return filecopy(from, to, null);
    }

        /**
         * from에서 to으로의 파일 복사
         * <p>
         * param from
         * param to
         * return returnValue
         */

    public static boolean filecopy( String from, String to, CopyProgressListener copyProgressListener){

        boolean returnValue = false;

        //복사한 파일이 위치한 경로
        File fFromFilePath = new File(from);

        // 복사한 파일이 아닐 경우, return false
        if (fFromFilePath.exists() == false || fFromFilePath.isFile() == false) {
               returnValue = false;
               return returnValue;
        }

        //복사할 파일의 크기
        long IFileTotalLength = fFromFilePath.length();

        long ITotalCopyLength = 0L;

        //파일의 크기가 0일경우, return false
        if (IFileTotalLength <= 0) {
              returnValue = false;
              return returnValue;
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {

            if(copyProgressListener != null) {
                copyProgressListener.copyFileBegin(fFromFilePath, IFileTotalLength);
            }


            //스트림 생성
            fis = new FileInputStream(from);
            fos = new FileOutputStream(to);

            int readData = 0;

            //인풋스트림을 아웃풋스트림에 쓰기
            byte[] bufferByte = new byte[2048];
            //file의 전체크기가 read데이터를 모두 읽지 않았다면, 계속해서 loop
            while ((readData = fis.read(bufferByte)) != -1) {
                //붙여넣기
                fos.write(bufferByte);
                //read 후, readData만큼 add
                ITotalCopyLength += readData;

                if(copyProgressListener != null) {
                    copyProgressListener.copyFileProgress(IFileTotalLength, ITotalCopyLength);
                }
            }

            if(copyProgressListener != null) {
                copyProgressListener.copyFileEnd(IFileTotalLength);
            }

            returnValue = true;

        } catch (IOException exception) {
            exception.printStackTrace();

            if(copyProgressListener != null) {
                copyProgressListener.copyFileException(fFromFilePath, exception);
            }

            returnValue = false;

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
            if (fos != null) {
                try {
                    fos.flush();
                } catch (IOException e) {
                }
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }


    return returnValue;
    }

}
