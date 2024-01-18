package software.ulpgc.imageviewer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class MainFrame extends JFrame {
    private ImageDisplay imageDisplay;
    private final Map<String, Command> commands;



    public MainFrame() {
        this.commands = new HashMap<>();
        setTitle("Image Viewer");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        add(createImageDisplay(), BorderLayout.CENTER); // Asegúrate de que el ImageDisplay esté en el centro.
        add(createButton("<"), BorderLayout.WEST); // Añade el botón "<" a la izquierda.
        add(createButton(">"), BorderLayout.EAST);
    }

    private Component createButton(String label) {
        JButton button = new JButton(label);
        button.setFont(new Font("Verdana", Font.BOLD, 40));
        button.addActionListener(e -> commands.get(label).execute());
        return button;
    }

    private Component createImageDisplay() {
        SwingImageDisplay display = new SwingImageDisplay();
        this.imageDisplay = display;
        return display;
    }


    public void add(String name, Command command) {
        commands.put(name, command);
    }

    public ImageDisplay imageDisplay() {
        return imageDisplay;
    }
}
