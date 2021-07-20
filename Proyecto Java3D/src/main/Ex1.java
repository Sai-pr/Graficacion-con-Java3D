/*
 * El primer ejercicio es un arerglo de cubos (Minimo 10) XD aqui son 20
 */
package main;

import javax.vecmath.*;
import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.applet.MainFrame;
import javax.swing.JPanel;

public class Ex1 extends JPanel
{

    public Ex1()
    {
        // create canvas
        System.setProperty("sun.awt.noerasebackground", "true");
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

    private BranchGroup createSceneGraph()
    {
        BranchGroup root = new BranchGroup();
        Background background = new Background(0.3f, 0.3f, 0.3f);
        BoundingSphere bounds = new BoundingSphere();
        background.setApplicationBounds(bounds);
        root.addChild(background);
        Shape3D shape;
        Appearance ap = new Appearance();
        PolygonAttributes pa = new PolygonAttributes();
        ap.setPolygonAttributes(pa);
        ColoringAttributes ca = new ColoringAttributes(0f, 0f, 0f, ColoringAttributes.SHADE_FLAT);
        ap.setColoringAttributes(ca);

        TransformGroup tg;
        TransformGroup rot;

        Transform3D tr = new Transform3D();

        int tami = 20;
        float c = -0.20f;
        float x = 0.2f;
        for (int i = 0; i < tami; i++)
        {
            tr.setTranslation(new Vector3f(c, 0.35f, (c * x * 2f))); //Y  //X  //Z
            c += 0.05;
            // tamaño de la figura setScale

            tr.setScale(x);
            x -= 0.01f;
            shape = new ColorCube();
            shape.setAppearance(ap);
            tg = new TransformGroup(tr);
            Transform3D trRot = new Transform3D();
            trRot.set(new AxisAngle4d(x, 0.1, x, -Math.PI));
            rot = new TransformGroup(trRot);
            root.addChild(rot);
            rot.addChild(tg);
            tg.addChild(shape);
        }
        return root;
    }
}
