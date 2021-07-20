package Ch7GeometricTransformation;

import javax.vecmath.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;

public class Ex2 extends Applet {
  public static void main(String[] args) {
    new MainFrame(new Ex2(), 640, 480);
  }

  public void init() {
    // create canvas
    GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
    Canvas3D cv = new Canvas3D(gc);
    setLayout(new BorderLayout());
    add(cv, BorderLayout.CENTER);
    BranchGroup bg = createSceneGraph();
    bg.compile();
    SimpleUniverse su = new SimpleUniverse(cv);
    su.getViewingPlatform().setNominalViewingTransform();
    su.addBranchGraph(bg);
  }

  private BranchGroup createSceneGraph() {
    BranchGroup root = new BranchGroup();
    TransformGroup spin = new TransformGroup();
    spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    root.addChild(spin);
    //object
    Primitive shape1 = new Cone();
    Transform3D tr1 = new Transform3D();
    tr1.set(new Vector3f(0,0,0.99f), 0.25f);
    tr1.setRotation(new AxisAngle4d(1,0,0, -Math.PI/2));
    TransformGroup tg1 = new TransformGroup(tr1);
    spin.addChild(tg1);
    tg1.addChild(shape1);
    Primitive shape2 = new Cone();
    Transform3D tr2 = new Transform3D();
    tr2.set(new Vector3f(0,0,-0.99f), 0.25f);
    tr2.setRotation(new AxisAngle4d(1, 0, 0, Math.PI/2));
    TransformGroup tg2 = new TransformGroup(tr2);
    spin.addChild(tg2);
    tg2.addChild(shape2);
    Alpha alpha = new Alpha(-1, 4000);
    RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
    BoundingSphere bounds = new BoundingSphere();
    rotator.setSchedulingBounds(bounds);
    spin.addChild(rotator);
    //light and background
    Background background = new Background(1.0f, 1.0f, 1.0f);
    background.setApplicationBounds(bounds);
    root.addChild(background);
    AmbientLight light = new AmbientLight(true, new Color3f(Color.red));
    light.setInfluencingBounds(bounds);
    root.addChild(light);
    PointLight ptlight = new PointLight(new Color3f(Color.green),
        new Point3f(3f,3f,3f), new Point3f(1f,0f,0f));
    ptlight.setInfluencingBounds(bounds);
    root.addChild(ptlight);
    PointLight ptlight2 = new PointLight(new Color3f(Color.orange),
        new Point3f(-2f,2f,2f), new Point3f(1f,0f,0f));
    ptlight2.setInfluencingBounds(bounds);
    root.addChild(ptlight2);
    return root;
  }
}