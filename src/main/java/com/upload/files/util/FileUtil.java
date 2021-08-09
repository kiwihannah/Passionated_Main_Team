package com.upload.files.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil { //제품 이미지용(

    public static void fileupload(byte[] file,String filePath,String fileName) throws IOException {
        File targetfile = new File(filePath);
        if(targetfile.exists()) {
            targetfile.mkdirs();
        }

        // Binary stream write
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

}
