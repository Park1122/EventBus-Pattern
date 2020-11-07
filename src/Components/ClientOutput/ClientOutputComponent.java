package Components.ClientOutput;
/**
 * Copyright(c) 2018 All rights reserved by JU Consulting
 */

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;


/**
 * @author Jungho Kim
 * @date 2018
 * @version 1.1
 * @description
 *    
 */
public class ClientOutputComponent {
    private JFrame mainWindow;
    private JTextArea textArea;

    /**
     * Constructor. 
     *
     */
    public ClientOutputComponent() {
        mainWindow = new JFrame("Client Output Window");
        JPanel textPanel = new JPanel();
        Dimension screenSize = mainWindow.getToolkit().getScreenSize();
        
        mainWindow.setBounds(0, 0, (int) (screenSize.width * 0.5), (int) (screenSize.height * 0.20));
        mainWindow.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        textArea = new JTextArea((int)(screenSize.height/100),(int)(screenSize.width/24));
        textArea.setLineWrap(true);
        
        JScrollPane textAreaScrollPane = new JScrollPane(textArea);
        
        mainWindow.add(textPanel);
        textPanel.add(textAreaScrollPane);
        
        mainWindow.setVisible(true);
        textArea.append("Start the client output component.\n");
    }
    
    /**
     * @param text
     */
    public void printText(String text) {
        textArea.append("\n======== New event has been arrived ========\n");
        textArea.append(text + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
    
    /**
     */
    public void quitWindow() {
        mainWindow.dispose();
    }
}
