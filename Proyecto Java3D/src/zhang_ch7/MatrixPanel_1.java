package Ch7GeometricTransformation;

import java.awt.*;
import javax.vecmath.*;

public class MatrixPanel_1 extends Panel {
  TextField[] fields = new TextField[16];
  
  public MatrixPanel_1() {
    setLayout(new GridLayout(4, 4));
    for (int i = 0; i < 16; i++) {
      fields[i] = new TextField(5);
      if (i/4 == i%4)
        fields[i].setText("1");
      else
        fields[i].setText("0");
      add(fields[i]);
    }    
  }
  
  public MatrixPanel_1(Matrix4d m) {
    setLayout(new GridLayout(4, 4));
    for (int i = 0; i < 16; i++) {
      fields[i] = new TextField(5);
      fields[i].setText("" + m.getElement(i/4, i%4));
      add(fields[i]);
    }
  }
  
  public void set(Matrix4d m) {
    for (int i = 0; i < 16; i++) {
      fields[i].setText("" + m.getElement(i/4, i%4));
    }    
  }

  public void get(Matrix4d m) {
    for (int i = 0; i < 16; i++) {
      m.setElement(i/4, i%4, Double.parseDouble(fields[i].getText()));
    }    
  }
}
