package luodi.poc.communication.serialization.javabuildin;

import luodi.poc.communication.serialization.javabuildin.Man;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Created by liujinjing on 2018/4/16.
 */
public class JavaBuildInSerializationTest {
    public static void main(String[] args) {
        Man man_oldVersion = new Man();
        man_oldVersion.setName("man_oldVersion");

        /*try {
            JavaBuildInSerializationUtil.serialize(man_oldVersion, "man_oldVersionFile");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        try {
            System.out.println((Man) JavaBuildInSerializationUtil.deserialize("man_oldVersionFile"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
