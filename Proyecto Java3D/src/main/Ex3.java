package main;

import zhang_ch7.*;
import javax.vecmath.*;
import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public class Ex3 extends Applet
{

    public Ex3()
    {
        System.setProperty("sun.awt.noerasebackground", "true");
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

    private BranchGroup createSceneGraph()
    {
        BranchGroup root = new BranchGroup();
        Background background = new Background(1.0f, 1.0f, 1.0f);
        BoundingSphere bounds = new BoundingSphere();
        background.setApplicationBounds(bounds);
        root.addChild(background);

        Transform3D tr = new Transform3D();
        TransformGroup tg;
        TransformGroup rot = null;

        int vNum = 20;
        float vTam = 0.04f;
        float vTam2 = 0.02f;
        float vSubir = -0.8f;

        int vCont = 400;
        int vCont1 = 2000;
        for (int j = 0; j < vNum * 2; j++)
        {

            for (int i = 0; i < vNum; i++)
            {

                if (i % 2 == 0)
                {
                    if (i % 4 == 0)
                    {
                        vTam = vTam + 0.0001f;
                        vSubir = vSubir + 0.006f;
                        tr.setTranslation(new Vector3f(0.3f, vSubir, 0f));
                        tr.setScale(vTam);
                        tg = new TransformGroup(tr);
                        Transform3D trRot = new Transform3D();
                        trRot.set(new AxisAngle4d(0, 0.0f, 1.0, Math.PI / (vNum / 2) * i));
                        rot = new TransformGroup(trRot);
                        root.addChild(rot);
                        rot.addChild(tg);
                        tg.addChild(new Shape3D(new Trapecio().mCrear(), mApariencia(new Color(1, 0, 0))));
                        rot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                        Alpha alpha = new Alpha(-1, vCont);
                        vCont += 500;
                        RotationInterpolator rotator = new RotationInterpolator(alpha, rot);
                        rotator.setSchedulingBounds(bounds);
                        rot.addChild(rotator);
                    } else
                    {
                        vTam2 = vTam2 + 0.0001f;
                        vSubir = vSubir + 0.006f;
                        tr.setTranslation(new Vector3f(0.3f, vSubir, 0f));
                        tr.setScale(vTam2);
                        tg = new TransformGroup(tr);
                        Transform3D trRot = new Transform3D();
                        trRot.set(new AxisAngle4d(0, 0.0f, 1.0, Math.PI / (vNum / 2) * i));
                        rot = new TransformGroup(trRot);
                        root.addChild(rot);
                        rot.addChild(tg);
                        Shape3D dode = new Dodecahedron();
                        dode.setAppearance(mApariencia(new Color(1, 250, 250)));
                        tg.addChild(dode);
                        rot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                        Alpha alpha = new Alpha(-1, vCont);
                        vCont += 500;
                        RotationInterpolator rotator = new RotationInterpolator(alpha, rot);
                        rotator.setSchedulingBounds(bounds);
                        rot.addChild(rotator);
                    }
                }
            }
        }

        AmbientLight light = new AmbientLight(true, new Color3f(Color.red));
        light.setInfluencingBounds(bounds);
        root.addChild(light);
        PointLight ptlight = new PointLight(new Color3f(Color.BLACK), new Point3f(2f, 2f, 2f), new Point3f(1f, 0f, 0f));
        ptlight.setInfluencingBounds(bounds);
        root.addChild(ptlight);
        PointLight ptlight2 = new PointLight(new Color3f(Color.MAGENTA), new Point3f(-2f, 2f, 2f), new Point3f(1f, 0f, 0f));
        ptlight2.setInfluencingBounds(bounds);
        root.addChild(ptlight2);

        return root;
    }

    private Appearance mApariencia(Color vColor)
    {
        Appearance appearance = new Appearance();

        //Singler
        ColoringAttributes vColorAtr = new ColoringAttributes();
        vColorAtr.setColor((float) vColor.getRed() / 255f, (float) vColor.getGreen() / 255f, (float) vColor.getBlue() / 255f);//Ponemos el color
        appearance.setColoringAttributes(vColorAtr);

        appearance.setMaterial(new Material());

//        PolygonAttributes pa = new PolygonAttributes();
//        appearance.setPolygonAttributes(pa);
//        ColoringAttributes ca = new ColoringAttributes((float) vColor[0].getRed() / 255f, (float) vColor[0].getGreen() / 255f, (float) vColor[0].getBlue() / 255f,ColoringAttributes.FASTEST);
//        appearance.setColoringAttributes(ca);
        return appearance;
    }
}

class Dodecahedron extends Shape3D
{

    public Dodecahedron()
    {
        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
        double phi = 0.5 * (Math.sqrt(5) + 1);
        Point3d[] vertices =
        {
            new Point3d(1, 1, 1),
            new Point3d(0, 1 / phi, phi), new Point3d(phi, 0, 1 / phi), new Point3d(1 / phi, phi, 0),
            new Point3d(-1, 1, 1), new Point3d(0, -1 / phi, phi), new Point3d(1, -1, 1),
            new Point3d(phi, 0, -1 / phi), new Point3d(1, 1, -1), new Point3d(-1 / phi, phi, 0),
            new Point3d(-phi, 0, 1 / phi), new Point3d(-1, -1, 1), new Point3d(1 / phi, -phi, 0),
            new Point3d(1, -1, -1), new Point3d(0, 1 / phi, -phi), new Point3d(-1, 1, -1),
            new Point3d(-1 / phi, -phi, 0), new Point3d(-phi, 0, -1 / phi), new Point3d(0, -1 / phi, -phi),
            new Point3d(-1, -1, -1)
        };
        int[] indices =
        {
            0, 1, 5, 6, 2, 0, 2, 7, 8, 3, 0, 3, 9, 4, 1,
            1, 4, 10, 11, 5, 2, 6, 12, 13, 7, 3, 8, 14, 15, 9,
            5, 11, 16, 12, 6, 7, 13, 18, 14, 8, 9, 15, 17, 10, 4,
            19, 16, 11, 10, 17, 19, 17, 15, 14, 18, 19, 18, 13, 12, 16
        };
        gi.setCoordinates(vertices);
        gi.setCoordinateIndices(indices);
        int[] stripCounts =
        {
            5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5
        };
        gi.setStripCounts(stripCounts);
        NormalGenerator ng = new NormalGenerator();
        ng.generateNormals(gi);
        this.setGeometry(gi.getGeometryArray());
    }
}

class Trapecio
{

    public Geometry mCrear()
    {
        IndexedQuadArray iqa = new IndexedQuadArray(8, GeometryArray.COORDINATES | GeometryArray.NORMALS, 4 * 6);
        Point3d[] coords =
        {
            new Point3d(-0.5, 1, -0.5), new Point3d(-0.5, 1, 0.5),
            new Point3d(0.5, 1, 0.5), new Point3d(0.5, 1, -0.5),
            new Point3d(-1, 0, -1), new Point3d(-1, 0, 1),
            new Point3d(1, 0, 1), new Point3d(1, 0, -1)
        };
        iqa.setCoordinates(0, coords);
        int[] indices =
        {
            0, 1, 2, 3,
            0, 4, 5, 1, 1, 5, 6, 2, 2, 6, 7, 3, 3, 7, 4, 0,
            4, 7, 6, 5
        };
        iqa.setCoordinateIndices(0, indices);
        float c = 1 / (float) Math.sqrt(5);
        float s = 2 * c;
        Vector3f[] normals =
        {
            new Vector3f(0, 1, 0),
            new Vector3f(-c, s, 0), new Vector3f(0, s, c), new Vector3f(c, s, 0), new Vector3f(0, s, -c),
            new Vector3f(0, -1, 0)
        };
        iqa.setNormals(0, normals);
        int[] normIndices =
        {
            0, 0, 0, 0,
            1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4,
            5, 5, 5, 5
        };
        iqa.setNormalIndices(0, normIndices);
        return iqa;

    }
}
