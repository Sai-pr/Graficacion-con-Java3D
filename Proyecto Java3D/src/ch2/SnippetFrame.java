//  CAVEES  160421
package ch2;

import java.awt.*;
import javax.swing.*;

public class SnippetFrame extends JFrame
{

    public static void main(String[] args)
    {

        new SnippetFrame(null, "Application Frame");
    }

    public SnippetFrame(Component component, String title)
    {
        super(title);
        setLayout(new BorderLayout());

        System.setProperty(
                "sun.awt.noerasebackground", "true");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if (component != null)
        {
            getContentPane().add(component, BorderLayout.CENTER);
        }
        pack();
        setSize(900, 900);
        center();
        setVisible(true);
    }

    public SnippetFrame()
    {
        this(null, "Application Frame v1.0");
    }

    public SnippetFrame(JComponent component, JMenuBar menuBar, String title)
    {
        this(component, title);
        setJMenuBar(menuBar);
    }

    public void center()
    {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        int x = (screenSize.width - frameSize.width) / 2;
        int y = (screenSize.height - frameSize.height) / 2;
        setLocation(x, y);
    }

}
