package backend;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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

public class SerialManager {

    // returns a deep clone of the provided PollyObject
    protected static PollyObject deepClonePollyObject(PApplet sketch, PollyObject obj) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream saveByteArray = new ByteArrayOutputStream();
        ObjectOutputStream byteArrayOutput = new ObjectOutputStream(saveByteArray);
        byteArrayOutput.writeObject(obj);
        byteArrayOutput.close();
        saveByteArray.close();
        ByteArrayInputStream openByteArray = new ByteArrayInputStream(saveByteArray.toByteArray());
        ObjectInputStream byteArrayInput = new ObjectInputStream(openByteArray);
        byteArrayInput.close();
        openByteArray.close();
        PollyObject clone = (PollyObject) byteArrayInput.readObject();
        clone.init(sketch);
        return clone;
    }

    // saves the draw space to a file that can be opened and restored later
    protected static void saveDrawSpace(DrawSpace ds, String filename) throws IOException {
        FileOutputStream saveFile = new FileOutputStream(filename);
        ObjectOutputStream fileOutput = new ObjectOutputStream(saveFile);
        fileOutput.writeObject(ds);
        fileOutput.close();
        saveFile.close();
    }

    // returns a draw space opened from file that can be made the current draw space
    protected static DrawSpace openDrawSpace(PApplet sketch, String filename) throws IOException, ClassNotFoundException {
        FileInputStream openFile = new FileInputStream(filename);
        ObjectInputStream fileInput = new ObjectInputStream(openFile);
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
        openFile.close();
        if (savedDrawSpace) {
            ds.init(sketch);
            return ds;
        }
        return null;
    }
}