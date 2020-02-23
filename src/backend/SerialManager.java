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

/**
* Facilitate serialization of objects for saving/opening/cloning purposes.
*/
public class SerialManager {
    /**
    * Deep Clone a PollyObject for copy/paste purposes.
    * @param sketch A reference to a PApplet to allow general functionality of the processing library for object init
    * @param obj A reference to the PollyObject that is to be cloned
    * @return A deep clone of the provided PollyObject
    * @throws IOException
    * @throws ClassNotFoundException
    * @see init(PApplet sketch)
    */
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

    /**
    * Save slides to a file that can be opened and restored later.
    * @param slides A list of all slides to be saved
    * @param filename The desired name for the file to be saved under
    * @throws IOException
    */
    protected static void saveSlides(ArrayList<DrawSpace> slides, String filename) throws IOException {
        FileOutputStream saveFile = new FileOutputStream(filename);
        ObjectOutputStream fileOutput = new ObjectOutputStream(saveFile);
        fileOutput.writeObject(slides);
        fileOutput.close();
        saveFile.close();
    }

    /**
    * Open and restore a previous slide project to continue editing.
    * @param sketch A reference to a PApplet to allow general functionality of the processing library for object init
    * @param filename The name for the slide project to be restored
    * @return A list of slides from the previous project to be made into the current slide workspace
    * @throws IOException
    * @throws ClassNotFoundException
    */
    protected static ArrayList<DrawSpace> openSlides(PApplet sketch, String filename) throws IOException, ClassNotFoundException {
        FileInputStream openFile = new FileInputStream(filename);
        ObjectInputStream fileInput = new ObjectInputStream(openFile);
        boolean savedDrawSpace = false;
        ArrayList<DrawSpace> slides = new ArrayList<DrawSpace>();
        try {
            Object obj = fileInput.readObject();
            if (obj instanceof ArrayList) {
                slides = (ArrayList<DrawSpace>) obj;
                savedDrawSpace = true;
            }
        }
        catch (EOFException e) {
            System.out.println(e);
        }
        fileInput.close();
        openFile.close();
        if (savedDrawSpace) {
            for (DrawSpace ds : slides) ds.init(sketch);
            return slides;
        }
        return null;
    }
}
