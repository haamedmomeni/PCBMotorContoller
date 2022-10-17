import swing.CircleProgressBar;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{

    private JLabel lbName;
    private JButton enterButton;
    private JPanel mainPanel;
    private JButton connectButton;
    private JTextField textFieldCommand;
    public JTextArea textAreaFeedback;
    private JButton clearButton;
    private JLabel portName;
    private JButton disconnectButton;
    private JButton stepButtonPlus;
    private JButton stepButtonMinus;
    private JTextField textFieldStep;
//    private JTextArea textAreaStep;
    private CircleProgressBar circleStep;
    private JTabbedPane tabbedPane1;
    private JPanel tab1;
    private JPanel tab2;
    private JTextField textFieldBuadrate;
    private JTextField textFieldDataBits;
    private JTextField TextFieldStopBits;
    private JTextField TextFieldParity;
    private JButton breakButton;


    public MainFrame() {
        setContentPane(mainPanel);
        setTitle("PCB motor");
        portName.setText("portName: " + "ttyUSB0");
        setSize(600,600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        textAreaFeedback.append(motorHandler.feedbackPort);
        textFieldStep.setText("1");

        setVisible(true);



        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                motorHandler.baudRate = Integer.parseInt(textFieldBuadrate.getText());
                motorHandler.dataBits = Integer.parseInt(textFieldDataBits.getText());
                motorHandler.stopBits = Integer.parseInt(TextFieldStopBits.getText());
                motorHandler.parity = Integer.parseInt(TextFieldParity.getText());

                motorHandler.connect();
                motorHandler.command("u0");
                textAreaFeedback.append(motorHandler.feedbackPort);
//                textAreaStep.setText(motorHandler.currentPosition);
                circleStep.setString(motorHandler.currentPosition);
                circleStep.setValue(motorHandler.currentProgressPercentage);
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String SS = textFieldCommand.getText();
                motorHandler.command(SS);

                textAreaFeedback.append(motorHandler.feedbackPort);
                textFieldCommand.setText("");
//                textAreaStep.setText(motorHandler.currentPosition);
                circleStep.setString(motorHandler.currentPosition);
                circleStep.setValue(motorHandler.currentProgressPercentage);
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                textAreaFeedback.setText("");
//                textAreaStep.setText("0");
            }
        });

//        disconnectButton.addComponentListener(new ComponentAdapter() {
//        });
        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                motorHandler.disconnect();
                textAreaFeedback.append(motorHandler.feedbackPort);
            }
        });

        stepButtonPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String SS = textFieldStep.getText();
                motorHandler.command("s" + SS); // Integer.toString(1));
                textAreaFeedback.append(motorHandler.feedbackPort);
//                textAreaStep.setText(motorHandler.currentPosition);
                circleStep.setString(motorHandler.currentPosition);
                circleStep.setValue(motorHandler.currentProgressPercentage);
            }
        });

        stepButtonMinus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String SS = textFieldStep.getText();
                motorHandler.command("s-" + SS); // Integer.toString(1));
                textAreaFeedback.append(motorHandler.feedbackPort);
//                textAreaStep.setText(motorHandler.currentPosition);
                circleStep.setString(motorHandler.currentPosition);
                circleStep.setValue(motorHandler.currentProgressPercentage);
            }
        });
        breakButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                motorHandler.command("<CR>"); // Integer.toString(1));
                textAreaFeedback.append(motorHandler.feedbackPort);
//                textAreaStep.setText(motorHandler.currentPosition);
                circleStep.setString(motorHandler.currentPosition);
                circleStep.setValue(motorHandler.currentProgressPercentage);
            }
        });
    }

}
