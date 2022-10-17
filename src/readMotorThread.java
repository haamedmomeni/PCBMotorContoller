import jssc.SerialPortException;

public class readMotorThread implements Runnable {


    public void run() {

        System.out.println("Listening thread is running...");
        try {
            motorHandler.serialPort.addEventListener(new motorHandler.PortReader());

        } catch (SerialPortException e) {
            throw new RuntimeException(e);

        }
    }
}
