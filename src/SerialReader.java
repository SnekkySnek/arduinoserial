import com.fazecast.jSerialComm.SerialPort;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

public class SerialReader {
    static SerialPort chosenPort;

    public static void main(String[] args){
                // create and configure the window
                JFrame window = new JFrame();
                window.setTitle("Serial Reader");
                window.setSize(600, 450);
                window.setLayout(new BorderLayout());
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //create drop down and connect button
                JComboBox<String> portList = new JComboBox<String>();
                JButton connectButton = new JButton("Connect");
                JPanel topPanel = new JPanel();
                topPanel.add(portList);
                topPanel.add(connectButton);
                window.add(topPanel, BorderLayout.NORTH);



//                populate box
        SerialPort[] portNames = SerialPort.getCommPorts();
        for(int i = 0 ; i < portNames.length; i++){
            portList.addItem(portNames[i].getSystemPortName());
        }


        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(connectButton.getText().equals("Connect")){
                    //attempt to connect to serial port
                    chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
                    chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
                    if(chosenPort.openPort()){
                        connectButton.setText("Disconnect");
                        portList.setEnabled(false);
                    }
                    Thread thread = new Thread(){
                        @Override public void run(){
                            Scanner scanner = new Scanner(chosenPort.getInputStream());
                            int x = 0;
                            while (scanner.hasNextLine()){
                                String line = scanner.nextLine();
                                System.out.println(line);
                            }
                            scanner.close();

                        }
                    };
                    thread.start();
                }else{
                    //disconnect from serial port
                    chosenPort.closePort();
                    connectButton.setText("Connect");
                    portList.setEnabled(true);
                }
            }
        });

                //show the window
                window.setVisible(true);

    }
}
