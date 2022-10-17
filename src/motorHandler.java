import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import javax.swing.*;

public class motorHandler {
//    Define port name
    static SerialPort serialPort = new SerialPort("/dev/ttyUSB0");

//    Feedback received from the PCB motor
    static String feedbackPort = "";
//    Current step
    static String currentPosition = "0";
    static int currentProgressPercentage = 0;
    static int baudRate = 19200;
    static int dataBits = 8;
    static int stopBits = 1;
    static int parity = 0;
//    Connect to the PCB motor based on the defined "portName"
    public static void connect() {
        try {
            serialPort.openPort();//Open serial port
            serialPort.setParams(baudRate, dataBits, stopBits, parity);

            System.out.println("Connected to device.");
            feedbackPort = "Connected to device.\n";
//            System.out.println("port list is: " + Arrays.asList(SerialPortList.getPortNames()));
        }
        catch (SerialPortException ex) {
            System.out.println("Cannot connect to device.\nThe error is: "+ ex);
            feedbackPort = "Cannot connect to device.\nThe error is: " + ex + "\n";
        }
    }
    // Disconnect to the PCB motor open port
    public static void disconnect(){
        try {
            serialPort.closePort();//Close serial port
            System.out.println("Disconnected from device.");
            feedbackPort = "Disconnected from device.\n";
        }
        catch (SerialPortException ex) {
            System.out.println("Cannot disconnect from device.\nThe error is: "+ ex);
            feedbackPort = "Cannot disconnect from device.\nThe error is: " + ex + "\n";
        }
    }
// read feedback of motor
    public static void readFeedback(){
        try {
            StringBuilder wholeBuffer = new StringBuilder();
            String buffer = serialPort.readHexString(1);
            wholeBuffer.append((char) Integer.parseInt(buffer, 16));

            while (!( (buffer.charAt(0) == '3') && ( (buffer.charAt(1) == 'E') ))) {
                buffer = serialPort.readHexString(1);
                wholeBuffer.append((char) Integer.parseInt(buffer, 16));
            }

            buffer = serialPort.readHexString(1);
            wholeBuffer.append((char) Integer.parseInt(buffer, 16));

            System.out.print(wholeBuffer);
            feedbackPort = wholeBuffer.toString();
        }
        catch (SerialPortException ex) {
            System.out.println("Cannot disconnect from device.\nThe error is: "+ ex);
            feedbackPort = "Cannot disconnect from device.\nThe error is: " + ex + "\n";
        }
    }
    // Analyze feedback of motor to check the step
    public static void analyzeFeedback(String feedString){
        int index = feedString.indexOf("End position ");
        int indexEnd = feedString.indexOf(" for Motor ");
        boolean isFound = (index != -1);
        if (isFound) {
            currentPosition = feedString.substring(index + 13, indexEnd);
            int X = (Integer.parseInt(currentPosition) %  200)/2;
            if (X>0) { currentProgressPercentage = X; } else { currentProgressPercentage = 100 + X; }
        }
//        else {
//            command("u0");
//            currentPosition = Integer.toString(index);
//        }
    }
    // Send any command to the PCB motor open port
    public static void command(String comm) {
//        try {

///////////////////////////////////////////////////////////////////
            SwingWorker sw1 = new SwingWorker() {
                // Method
                @Override
                protected String doInBackground() throws Exception
                {
                    // Defining what thread will do here
                    try {
                        serialPort.writeString(comm + "\r");//Write data to port
                    }
                    catch (SerialPortException ex) {
                        System.out.println("There is an error with the device!\nThe error is: " + ex);
                        feedbackPort = "There is an error with the device!\nThe error is: " + ex + "\n";
                    }
                    String res = "Finished Execution";
                    return res;
                }
                @Override protected void done()
                {
                    readFeedback();
                    analyzeFeedback(feedbackPort);
                }
                };
            // Executes the swingworker on worker thread
            sw1.execute();
//////////////////////////////////////////////////////////////////
//            serialPort.writeString(comm + "\r");//Write data to port
//            readFeedback();
//            analyzeFeedback(feedbackPort);

//        }
//        catch (SerialPortException ex) {
//            System.out.println("There is an error with the device!\nThe error is: " + ex);
//            feedbackPort = "There is an error with the device!\nThe error is: " + ex + "\n";
//        }
//        return null;
    }
    // This method is not used in the current application
    static class PortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    String receivedData = serialPort.readString(event.getEventValue());
                    System.out.println(receivedData);
                }
                catch (SerialPortException ex) {
                    System.out.println("Error in receiving string from COM-port: " + ex);
                    feedbackPort = "Error in receiving string from COM-port: " + ex;
                }
            }
        }

    }


}
////////////////////////////////////////////////////////////////////////////////////////////
//            serialPort.writeString("s100;\r");//Write data to port
//            serialPort.fl
//            String buffer = serialPort.readString(97);//Read 10 bytes from serial port
//            System.out.println(buffer);
//            System.out.println(buffer.substring(buffer.length() - 2));
//            Thread.sleep(90);
//
//        Enumeration portList;
//        portList = CommPortIdentifier.getPortIdentifiers();
//        System.out.println("N.G.");
//        while (portList.hasMoreElements()) {
//            CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
//            System.out.println(portId);
//        }
//
//        String[] portNames = SerialPortList.getPortNames();
//        System.out.println("number of ports: " + portNames.length);
//        for(int i = 0; i < portNames.length; i++){
//            System.out.println(portNames[i]);
//        }


//            serialPort.fl
//            String buffer = serialPort.readString(68);//Read 10 bytes from serial port


//            String wholeBuffer = "X";
//            System.out.println("XXX: ");
//            System.out.println(wholeBuffer.substring(wholeBuffer.length() - 1 ));
//            String buffer = serialPort.readString(9);//Read 10 bytes from serial port
//            System.out.println(buffer);
////            event.isRXCHAR() && event.getEventValue() > 0
//            while (buffer != "P") {
////                System.out.print(buffer != "P");
//                buffer = serialPort.readString(1);
//                System.out.print(buffer);
//                wholeBuffer += buffer;
//            }
//            buffer = serialPort.readString(1);
//
////            System.out.println(buffer);
//            System.out.println(wholeBuffer);
////            serialPort.addEventListener(new motorHandler.PortReader());


//            for (int i = 0; i<10; i++) {
//            }
//            System.out.println(serialPort.readBufferSize());
//            }

//            int event = serialPort.getEventsMask();
//            System.out.println(serialPort.getOutputBufferBytesCount());
//            if(event.isRXCHAR() && event.getEventValue() > 0) {
//                try {
//                    String receivedData = serialPort.readString(event.getEventValue());
//                    System.out.println(receivedData);
//                } catch (SerialPortException ex) {
//                    System.out.println("Error in receiving string from COM-port: " + ex);
//                }
//            }