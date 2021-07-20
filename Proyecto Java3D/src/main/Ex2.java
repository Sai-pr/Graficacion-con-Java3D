package main;

import ch1.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Color3f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author leand
 */
public class Ex2 extends JPanel
{

    public static void main(String[] args)
    {
        System.setProperty("sun.awt.noerasebackground", "true");
        JFrame frame = new JFrame();
        frame.setPreferredSize(new Dimension(700, 700));
        frame.setLayout(new BorderLayout());
        frame.add(new Ex2());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    }

    public Ex2()
    {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);

        BranchGroup scene = createSceneGraph();

        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
    }

    private BranchGroup createSceneGraph()
    {
        BranchGroup root = new BranchGroup();
        TransformGroup spin = new TransformGroup();
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(spin);

        //primitives
        Appearance ap = new Appearance();
        ap.setMaterial(new Material());
        //Box box = new Box(1.2f, 0.3f, 0.8f, ap);
        //Sphere sphere = new Sphere();
        //Cylinder cylinder = new Cylinder();
        //Cone cone = new Cone();

        Transform3D tr = new Transform3D();
        tr.setScale(0.2);
        TransformGroup tg = new TransformGroup(tr);

        spin.addChild(tg);
        //tg.addChild(box);//Caja

        tr.setIdentity();
        tr.setTranslation(new Vector3f(0f, 1.5f, 1f));//Circulo
        TransformGroup tgSphere = new TransformGroup(tr);
        tg.addChild(tgSphere);
        //tgSphere.addChild(sphere);

        tr.setTranslation(new Vector3f(-1f, -1.5f, 2f));//Cilindro
        TransformGroup tgCylinder = new TransformGroup(tr);
        tg.addChild(tgCylinder);
        //tgCylinder.addChild(cylinder);

        tr.setTranslation(new Vector3f(1f, -1.5f, 0f));
        TransformGroup tgCone = new TransformGroup(tr);//Cono
        tg.addChild(tgCone);
        //tgCone.addChild(cone);

        TransformGroup vTG = null;
        TransformGroup vTTodo = new TransformGroup();
        vTTodo.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TransformGroup rot = null;

        spin.addChild(vTTodo);
        Transform3D trr = new Transform3D();
        trr.setTranslation(new Vector3f(0f, 0.4f, 0.5f));
        trr.setScale(0.03);
        int vNum = 50;
        for (int i = 0; i < vNum; i++)
        {

            if (i % 2 == 0)
            {
                Cone conito = new Cone();
                vTG = new TransformGroup(trr);
                Transform3D trRot = new Transform3D();
                trRot.set(new AxisAngle4d(1, 1.0, 0.0, Math.PI / (vNum / 2) * i));//Poner la inclinacion a donde saldra
                rot = new TransformGroup(trRot);
                vTTodo.addChild(rot);
                rot.addChild(vTG);
                vTG.addChild(conito);

                conito = new Cone();
                vTG = new TransformGroup(trr);
                trRot = new Transform3D();
                trRot.set(new AxisAngle4d(1, -1.0, 0.0, Math.PI / (vNum / 2) * i));//Poner la inclinacion a donde saldra
                rot = new TransformGroup(trRot);
                vTTodo.addChild(rot);
                rot.addChild(vTG);
                vTG.addChild(conito);
            } else if (i % 3 == 0)
            {
                Sphere sfera = new Sphere();
                vTG = new TransformGroup(trr);
                Transform3D trRot = new Transform3D();
                trRot.set(new AxisAngle4d(1, 0.0, 0.0, Math.PI / (vNum / 2) * i));//Poner la inclinacion a donde saldra
                rot = new TransformGroup(trRot);
                vTTodo.addChild(rot);
                rot.addChild(vTG);
                vTG.addChild(sfera);
            } else
            {
                Box box1 = new Box();
                vTG = new TransformGroup(trr);
                Transform3D trRot = new Transform3D();
                trRot.set(new AxisAngle4d(1, 0.0, 0.0, Math.PI / (vNum / 2) * i));//Poner la inclinacion a donde saldra
                rot = new TransformGroup(trRot);
                vTTodo.addChild(rot);
                rot.addChild(vTG);
                vTG.addChild(box1);
            }
        }

        Alpha alpha = new Alpha(1, 90000);
        RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        spin.addChild(rotator);

        Alpha alphaa = new Alpha(-1, 90000);
        RotationInterpolator rotatorr = new RotationInterpolator(alphaa, vTTodo);
        BoundingSphere boundss = new BoundingSphere();
        rotatorr.setSchedulingBounds(boundss);
        vTTodo.addChild(rotatorr);

        //background and light
        Background background = new Background(1.0f, 1.0f, 1.0f);
        background.setApplicationBounds(bounds);
        root.addChild(background);

        AmbientLight light = new AmbientLight(true, new Color3f(Color.MAGENTA));
        light.setInfluencingBounds(bounds);
        root.addChild(light);
        PointLight ptlight = new PointLight(new Color3f(Color.RED), new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
        ptlight.setInfluencingBounds(bounds);
        root.addChild(ptlight);
        PointLight ptlight2 = new PointLight(new Color3f(Color.CYAN), new Point3f(-2f, 2f, 2f), new Point3f(1f, 0f, 0f));
        ptlight2.setInfluencingBounds(bounds);
        root.addChild(ptlight2);
        return root;
    }

}
