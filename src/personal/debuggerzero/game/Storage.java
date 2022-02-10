package personal.debuggerzero.game;

import java.io.*;

public class Storage {

    private File parentFile;

    private FileInputStream inData;
    private InputStreamReader reader;

    private FileOutputStream outData;
    private OutputStreamWriter writer;

    public Storage(){
        parentFile = new File("C:\\2048");
        if (!parentFile.isDirectory()){
            boolean result = parentFile.mkdir();
        }
    }

    public void write(int data) throws IOException {
        File file = new File(parentFile, "data.dct");
        outData = new FileOutputStream(file);
        writer = new OutputStreamWriter(outData, "gbk");

        writer.append(Integer.toString(data));

        writer.close();
        outData.close();
    }

    public int read() throws IOException {

        File file = new File(parentFile, "data.dct");
        if(!file.exists() || file.isDirectory()){
            write(0);
        }

        inData = new FileInputStream(file);
        reader = new InputStreamReader(inData, "gbk");

        int data = 0;
        while (reader.ready()) {
            data = data * 10 + (reader.read() - '0');
        }

        reader.close();
        inData.close();

        return data;
    }
}
