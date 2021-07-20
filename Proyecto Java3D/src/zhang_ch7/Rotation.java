package zhang_ch7;

import javax.vecmath.*;
import java.awt.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;

public class Rotation extends Applet
{

    public static void main(String[] args)
    {
        new MainFrame(new Rotation(), 640, 650);
    }

    public void init()
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
        float vSubir = -0.8f;

        int vCont = 500;
        int vCont1 = 2000;
        for (int j = 0; j < vNum * 2; j++)
        {

//            vSubir = vSubir + 0.035f;
//            vTam = vTam + 0.001f;
            for (int i = 0; i < vNum; i++)
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

//                    if (j <= 3 && i <= 20)
//                    {
//                        rot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//                        Alpha alpha = new Alpha(-1, vCont1);
//                        vCont1 += 500;
//                        vCont += 500;
//                        RotationInterpolator rotator = new RotationInterpolator(alpha, rot);
//                        rotator.setSchedulingBounds(bounds);
//                        rot.addChild(rotator);
//
//                    } else
                {
                    rot.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                    Alpha alpha = new Alpha(-1, vCont);
                    vCont += 500;
                    RotationInterpolator rotator = new RotationInterpolator(alpha, rot);
                    rotator.setSchedulingBounds(bounds);
                    rot.addChild(rotator);
                }

            }

        }

        AmbientLight light = new AmbientLight(true, new Color3f(Color.red));
        light.setInfluencingBounds(bounds);
        root.addChild(light);
        PointLight ptlight = new PointLight(new Color3f(Color.BLACK), new Point3f(2f, 2f, 2f), new Point3f(1f, 0f, 0f));
        ptlight.setInfluencingBounds(bounds);
        root.addChild(ptlight);
        PointLight ptlight2 = new PointLight(new Color3f(Color.orange), new Point3f(-2f, 2f, 2f), new Point3f(1f, 0f, 0f));
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
