package backend;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import backend.objects.*;

import processing.core.PApplet;
import processing.core.PConstants;

public class FileManager {

    protected static void saveDrawSpace(DrawSpace ds, String filename) throws IOException {
        FileOutputStream saveFile = new FileOutputStream(filename);
        ObjectOutputStream fileOutput = new ObjectOutputStream(saveFile);
        fileOutput.writeObject(ds);
        fileOutput.close();
        saveFile.close();
    }

    protected static DrawSpace loadDrawSpace(PApplet sketch, String filename) throws IOException, ClassNotFoundException {
        FileInputStream loadFile = new FileInputStream(filename);
        ObjectInputStream fileInput = new ObjectInputStream(loadFile);
        ArrayList<PollyObject> objects = new ArrayList<PollyObject>();
        boolean savedDrawSpace = false;
        DrawSpace ds = new DrawSpace(sketch, 0, 0, 0, 0);
        try {
            PollyObject obj = (PollyObject) fileInput.readObject();
            if (obj instanceof DrawSpace) {
                ds = (DrawSpace) obj;
                savedDrawSpace = true;
            }
        }
        catch (EOFException e) {
            System.out.println(e);
        }
        fileInput.close();
        loadFile.close();
        if (savedDrawSpace) {
            ds.init(sketch);
            return ds;
        }
        return null;
    }
}