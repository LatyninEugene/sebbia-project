package controlers;

import model.Canvas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class GenerateAsciiDoc {
    public static File getAsciiDoc(Canvas can,String name) throws IOException {
        File file = new File("asciiCanvas.adoc");
        FileWriter fw = new FileWriter(file);
        fw.write("= "+name+"\n");
        fw.write("\n");
        Map<String, String[]> blocks = can.getBloks();
        for (String k : blocks.keySet()) {
            String[] str = blocks.get(k);
            fw.write("=== "+str[0]+"\n");

            fw.write("\n");
            fw.write("-----------------\n");
            fw.write(str[1]+"\n");
            fw.write("-----------------\n");

            String[] line = str[2].split("\n");
            for (String s : line) {
                fw.write("* "+s+"\n");
            }
            fw.write("\n");
        }

        fw.close();
        return file;
    }
}
