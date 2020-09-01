package org.mine.aplt.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wntl
 * @version v1.0
 * @Description:
 * @ClassName: FileUtil
 * @date 2020/8/1215:05
 */
public final class FileUtil {
    public static BufferedReader newFileReader(String path, String encode) throws FileNotFoundException, UnsupportedEncodingException{
        return new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), encode));
    }

    public static List<String> readFiles(String path){
        List<String> list = null;
        BufferedReader reader = null;
        try {
            list = new ArrayList<>();
            reader = newFileReader(path, "UTF-8");
            String lineContent = "";
            while ((lineContent = reader.readLine()) != null) {
                list.add(lineContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {}
        }
        return list;
    }

    public static BufferedWriter newFileWriter(String path, String encode) throws FileNotFoundException, UnsupportedEncodingException{
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), encode));
    }

    public static void writeFile(String contents, String path){
        BufferedWriter writer = null;
        try {
            writer = newFileWriter(path, "UTF-8");
            writer.write(contents);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(writer != null) writer.close();
            } catch (IOException e) {}
        }
    }

    public static void createNewFile(String path) throws IOException{
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
    }
}
