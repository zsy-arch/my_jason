import java.io.*;

class TestObject implements Serializable {
    int a;

    public void test() {
        System.out.println(a);
    }
}

public class TestSerial {
    public static void main(String[] args) {
        try {
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("testobject.ser"));
//            objectOutputStream.writeObject(new TestObject());
//            objectOutputStream.close();
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("testobject.ser"));
            TestObject testObject = (TestObject) objectInputStream.readObject();
            testObject.test();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
