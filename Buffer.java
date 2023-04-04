import java.io.*;
public class Buffer {
    public String fileSpec;
    private boolean dirty;
    public DLList<String> text = new DLList<String>();

    public Buffer() {
        fileSpec = "File";
        dirty = false;
    }

    public Buffer(String fileName) {
        fileSpec = fileName;
        dirty = false;
    }

    public void setFileSpec(String fileName) {
        fileSpec = fileName;
    }

    public String getFileSpec() {
        return fileSpec;
    }

    public boolean getDirty() {
        return dirty;
    }

    public void setDirty(boolean dirt) {
        dirty = dirt;
    }

    public void bufferFileIn(String name){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(name));
                String line = reader.readLine();
                while(line != null) {
                    text.insertLast(line);
                    line = reader.readLine();
                }
                text.seek(0);
                reader.close();
            } 
            catch (IOException e) {
                System.out.println("Invalid File Name");
            }

     }
}
    

