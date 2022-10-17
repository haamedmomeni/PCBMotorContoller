package swing;

import javax.swing.*;
import java.awt.*;

public class CircleProgressBar extends JProgressBar {
    public CircleProgressBar() {
        setOpaque(false);
        setBackground( new Color(220, 220, 220));
        setForeground(new Color(97,97,97));
        setStringPainted(true);
        setUI(new ProgressCircleUI());

    }
}
