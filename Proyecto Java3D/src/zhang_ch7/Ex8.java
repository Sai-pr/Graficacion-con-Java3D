
package Ch7GeometricTransformation;

import javax.vecmath.*;
import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.awt.event.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;

public class Ex8 extends Applet {
  public static void main(String[] args) {
    new MainFrame(new Ex8(), 640, 480);
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
    
    Transform3D tr = new Transform3D();
    tr.setScale(0.3);
    tr.setRotation(new AxisAngle4d(1, 0, 0, Math.PI/6));
    TransformGroup tg = new TransformGroup(tr);
    spin.addChild(tg);
    
    //object
    Geometry geom = rotateShape();
    Appearance ap = new Appearance();
    ap.setMaterial(new Material());
    PolygonAttributes pa = new PolygonAttributes(PolygonAttributes.POLYGON_FILL,
    PolygonAttributes.CULL_NONE,0);
    pa.setBackFaceNormalFlip(true);
    ap.setPolygonAttributes(pa);
    Shape3D shape = new Shape3D(geom, ap);
    tg.addChild(shape);
    
    Alpha alpha = new Alpha(-1, 8000);
    RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
    BoundingSphere bounds = new BoundingSphere();
    rotator.setSchedulingBounds(bounds);
    spin.addChild(rotator);
    
    //background and lights
    Background background = new Background(1.0f, 1.0f, 1.0f);
    background.setApplicationBounds(bounds);
    root.addChild(background);
    AmbientLight light = new AmbientLight(true, new Color3f(Color.blue));
    light.setInfluencingBounds(bounds);
//    root.addChild(light);
    PointLight ptlight = new PointLight(new Color3f(Color.white),
    new Point3f(3f,3f,3f), new Point3f(1f,0f,0f));
    ptlight.setInfluencingBounds(bounds);
    root.addChild(ptlight);
    return root;
  }
    
  Geometry rotateShape() {
    int n = 60;
    IndexedQuadArray qa = new IndexedQuadArray(4*n,
    IndexedQuadArray.COORDINATES, 4*4*n);
    Transform3D trans = new Transform3D();
    trans.rotY(2*Math.PI/n);
    Point3f[] pts = {new Point3f(1, 0, 0),new Point3f(2, 0, 0),
            new Point3f(2, 1, 0),new Point3f(1, 1, 0)};
    for (int j = 0; j < n; j++) {
      for (int i = 0; i < 4; i++) {
        qa.setCoordinate(j*4+i, pts[i]);
        trans.transform(pts[i]);
      }
    }
    int quadIndex = 0;
    for (int j = 0; j < n; j++) {
      for (int i = 0; i < 4; i++) {
        int j1 = (j+1) % n;
        int i1 = (i+1) % 4;
        qa.setCoordinateIndex(quadIndex++, j*4+i);
        qa.setCoordinateIndex(quadIndex++, j*4+i1);
        qa.setCoordinateIndex(quadIndex++, (j1)*4+i1);
        qa.setCoordinateIndex(quadIndex++, (j1)*4+i);
      }
    }
    GeometryInfo gi = new GeometryInfo(qa);
    NormalGenerator ng = new NormalGenerator();
    ng.generateNormals(gi);
    return gi.getGeometryArray();
  }
}
