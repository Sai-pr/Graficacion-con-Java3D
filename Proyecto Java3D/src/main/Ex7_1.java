package main;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.pickfast.behaviors.*;
import java.awt.Color;
import javax.media.j3d.*;
import javax.vecmath.*;

//   MousePickApp renders two interactively rotatable cubes.  
public class Ex7_1 extends javax.swing.JPanel
{

    private void mOpcionesRaton(BranchGroup vBr, TransformGroup vTG, Canvas3D canvas3D)
    {

        vTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        vTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        vTG.setCapability(TransformGroup.ENABLE_PICK_REPORTING);

        vBr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        vBr.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        vBr.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
        //Mover objeto
        PickTranslateBehavior vTraslate = new PickTranslateBehavior(vBr, canvas3D, new BoundingSphere());
        vBr.addChild(vTraslate);
        //Zoom objeto
        PickZoomBehavior vZoom = new PickZoomBehavior(vBr, canvas3D, new BoundingSphere());
        vBr.addChild(vZoom);

        //Rotar objeto
        PickRotateBehavior vRotate = new PickRotateBehavior(vBr, canvas3D, new BoundingSphere());
        vBr.addChild(vRotate);

    }

    public BranchGroup createSceneGraph()
    {
        // Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();
        int canti = 10;
        float x = -0.9f;
        TransformGroup objRotate = null;
        PickRotateBehavior pickRotate = null;
        Transform3D transform = new Transform3D();
        BoundingSphere behaveBounds = new BoundingSphere();

        // creacion del cubo
        for (int i = 0; i < canti; i++)
        {
            transform.setTranslation(new Vector3f(x, -0.0f - x, 0f));
            objRotate = new TransformGroup(transform);
            objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            objRotate.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
            objRoot.addChild(objRotate);
            objRotate.addChild(new ColorCube(0.06));
            x += 0.2;
        }

        //picking
        pickRotate = new PickRotateBehavior(objRoot, canvas, behaveBounds);
        objRoot.addChild(pickRotate);

        //movimiento con el mouse
        PickTranslateBehavior vTraslate = new PickTranslateBehavior(objRoot, canvas, new BoundingSphere());
        objRoot.addChild(vTraslate);

        PickZoomBehavior vZoom = new PickZoomBehavior(objRoot, canvas, new BoundingSphere());
        objRoot.addChild(vZoom);

        //geometria añadida circular cuadrado
        Transform3D tr = new Transform3D();
        tr.setTranslation(new Vector3f(x, 0.7f, 0.0f));

        /////////////////Donut XD//////////////
        TransformGroup vTG = null;
        TransformGroup vTTodo = new TransformGroup();
        vTTodo.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        TransformGroup rot = null;

        TransformGroup spin = new TransformGroup();
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objRoot.addChild(spin);
        spin.addChild(vTTodo);
        Transform3D trr = new Transform3D();
        trr.setTranslation(new Vector3f(0f, 0.4f, 0.5f));//           { o }   -    |
        trr.setScale(0.05);
        int vNum = 25;
        for (int i = 0; i < vNum; i++)
        {

            BranchGroup vBRDona = new BranchGroup();

            Torus toro = new Torus(0.2, 0.5);
            vTG = new TransformGroup(trr);

            Color vColor[] = new Color[2];
            vColor[0] = new Color(255, 0, 0);
            vColor[1] = new Color(0, 0, 255);
            toro.setAppearance(new Apariencia(vColor, 4, 3, vTG));

            Transform3D trRot = new Transform3D();
            trRot.set(new AxisAngle4d(0.6, 0.0, 1.0, Math.PI / (vNum / 2) * i));//Poner la inclinacion a donde saldra
            rot = new TransformGroup(trRot);
            vTTodo.addChild(rot);
            rot.addChild(vTG);

            vBRDona.addChild(toro);
            mOpcionesRaton(vBRDona, vTG, canvas);
            vTG.addChild(vBRDona);

        }

        float y = -0.9f;

        //Banda de mobius
        x = -0.9f;
        for (int i = 0; i < canti; i++)
        {
            tr.setTranslation(new Vector3f(x, -0.0f + x, 0.0f));
            tr.setScale(0.08);
            objRotate = new TransformGroup(tr);
            objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
            objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
            objRotate.setCapability(TransformGroup.ENABLE_PICK_REPORTING);
            objRoot.addChild(objRotate);
            Appearance ap = new Appearance();
            ap.setMaterial(new Material());
            PolygonAttributes pa = new PolygonAttributes();
            pa.setBackFaceNormalFlip(true);
            pa.setCullFace(PolygonAttributes.CULL_NONE);
            ap.setPolygonAttributes(pa);
            Shape3D shape = new Shape3D(mobius(), ap);

            shape.setAppearance(ap);
            objRotate.addChild(shape);
            x += 0.2;
        }

        BoundingSphere bounds = new BoundingSphere();
        Background background = new Background(0.0f, 0.0f, 0.1f);
        background.setApplicationBounds(bounds);
        objRoot.addChild(background);
        AmbientLight light = new AmbientLight(true, new Color3f(Color.red));
        light.setInfluencingBounds(bounds);
        objRoot.addChild(light);
        PointLight ptlight = new PointLight(new Color3f(Color.green), new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
        ptlight.setInfluencingBounds(bounds);
        objRoot.addChild(ptlight);
        PointLight ptlight2 = new PointLight(new Color3f(Color.orange), new Point3f(-2f, 2f, 2f), new Point3f(1f, 0f, 0f));
        ptlight2.setInfluencingBounds(bounds);
        objRoot.addChild(ptlight2);

        // Let Java 3D perform optimizations on this scene graph.
        objRoot.compile();

        return objRoot;
    } // end of CreateSceneGraph method of MousePickApp

    private Geometry mobius()
    {
        int m = 100;
        int n = 30;
        float vmin = -0.3f;
        float vmax = 0.3f;
        float umin = 0.0f;
        float umax = (float) (2 * Math.PI);
        float du = (umax - umin) / m;
        float dv = (vmax - vmin) / n;

        Point3f[] verts = new Point3f[(m + 1) * (n + 1)];
        Vector3f[] norms = new Vector3f[(m + 1) * (n + 1)];
        int count = 0;
        // vertices
        float u = umin;
        for (int i = 0; i <= m; i++)
        {
            float v = vmin;
            for (int j = 0; j <= n; j++)
            {
                float x = (float) (Math.cos(u) + v * Math.cos(u / 2) * Math.cos(u));
                float y = (float) (Math.sin(u) + v * Math.cos(u / 2) * Math.sin(u));
                float z = (float) (v * Math.sin(u / 2));
                verts[count] = new Point3f(x, y, z);

                float nx = (float) ((1 + v * Math.cos(u / 2)) * Math.cos(u) * Math.sin(u / 2)
                        - 0.5 * v * Math.sin(u));
                float ny = (float) ((1 + v * Math.cos(u / 2)) * Math.sin(u) * Math.sin(u / 2)
                        - 0.5 * v * Math.cos(u));
                float nz = -(float) ((1 + v * Math.cos(u / 2)) * Math.cos(u / 2));
                norms[count] = new Vector3f(nx, ny, nz);
                norms[count].normalize();

                count++;
                v += dv;
            }
            u += du;
        }
        // indices
        int[] inds = new int[4 * m * n];
        count = 0;
        for (int i = 0; i < m; i++)
        {
            for (int j = 0; j < n; j++)
            {
                inds[count++] = i * (n + 1) + j;
                inds[count++] = (i + 1) * (n + 1) + j;
                inds[count++] = (i + 1) * (n + 1) + j + 1;
                inds[count++] = i * (n + 1) + j + 1;
            }
        }

        IndexedQuadArray iqa = new IndexedQuadArray((m + 1) * (n + 1),
                QuadArray.COORDINATES | QuadArray.NORMALS, 4 * m * n);
        iqa.setCoordinates(0, verts);
        iqa.setNormals(0, norms);
        iqa.setCoordinateIndices(0, inds);
        iqa.setNormalIndices(0, inds);
        return iqa;
    }

    // Create a simple scene and attach it to the virtual universe
    Canvas3D canvas;

    public Ex7_1()
    {
        setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();

        canvas = new Canvas3D(config);
        add("Center", canvas);

        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas);

        BranchGroup scene = createSceneGraph();

        // This will move the ViewPlatform back a bit
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
    } // end of MousePickApp (constructor)

    public class Dodecahedron extends Shape3D
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

    public class Torus extends Shape3D
    {

        public Torus(double r1, double r2)
        {
            int m = 20;
            int n = 40;
            Point3d[] pts = new Point3d[m];
            pts[0] = new Point3d(r1 + r2, 0, 0);
            double theta = 2.0 * Math.PI / m;
            double c = Math.cos(theta);
            double s = Math.sin(theta);
            double[] mat =
            {
                c, -s, 0, r2 * (1 - c),
                s, c, 0, -r2 * s,
                0, 0, 1, 0,
                0, 0, 0, 1
            };
            Transform3D rot1 = new Transform3D(mat);
            for (int i = 1; i < m; i++)
            {
                pts[i] = new Point3d();
                rot1.transform(pts[i - 1], pts[i]);
            }

            Transform3D rot2 = new Transform3D();
            rot2.rotY(2.0 * Math.PI / n);
            IndexedQuadArray qa = new IndexedQuadArray(m * n,
                    IndexedQuadArray.COORDINATES, 4 * m * n);
            int quadIndex = 0;
            for (int i = 0; i < n; i++)
            {
                qa.setCoordinates(i * m, pts);
                for (int j = 0; j < m; j++)
                {
                    rot2.transform(pts[j]);
                    int[] quadCoords =
                    {
                        i * m + j, ((i + 1) % n) * m + j,
                        ((i + 1) % n) * m + ((j + 1) % m), i * m + ((j + 1) % m)
                    };
                    qa.setCoordinateIndices(quadIndex, quadCoords);
                    quadIndex += 4;
                }
            }
            GeometryInfo gi = new GeometryInfo(qa);
            NormalGenerator ng = new NormalGenerator();
            ng.generateNormals(gi);
            this.setGeometry(gi.getGeometryArray());
        }
    }

}

class Apariencia extends Appearance
{

    public Apariencia(Color vColor[], int vEstiloSelected, int vTipoSelected, TransformGroup vTroll)
    {
        this.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        this.setCapability(Appearance.ALLOW_POINT_ATTRIBUTES_WRITE);
        this.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
        this.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
        this.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
        this.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
        this.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);

        Material vMaterial = new Material();
        ColoringAttributes vColorAtr = new ColoringAttributes();
        PolygonAttributes vPoligonAtr = new PolygonAttributes();

        switch (vEstiloSelected)
        {
            case 1:
                //Singler
                vColorAtr.setColor((float) vColor[0].getRed() / 255f, (float) vColor[0].getGreen() / 255f, (float) vColor[0].getBlue() / 255f);//Ponemos el color
                this.setColoringAttributes(vColorAtr);
                break;
            case 2:
                //Flat
                this.setPolygonAttributes(vPoligonAtr);
                vColorAtr.setShadeModel(ColoringAttributes.SHADE_FLAT);
                this.setColoringAttributes(vColorAtr);
                break;
            case 3:
                //Ground
                this.setPolygonAttributes(vPoligonAtr);
                vColorAtr.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
                this.setColoringAttributes(vColorAtr);
                break;
            case 4:
                //Lighting
                vMaterial.setAmbientColor(new Color3f((float) vColor[0].getRed() / 255f, (float) vColor[0].getGreen() / 255f, (float) vColor[0].getBlue() / 255f));
                vMaterial.setDiffuseColor(new Color3f((float) vColor[1].getRed() / 255f, (float) vColor[1].getGreen() / 255f, (float) vColor[1].getBlue() / 255f));
                this.setMaterial(vMaterial);
                vColorAtr.setColor((float) vColor[0].getRed() / 255f, (float) vColor[0].getGreen() / 255f, (float) vColor[0].getBlue() / 255f);//Ponemos el color
                vColorAtr.setShadeModel(ColoringAttributes.SHADE_FLAT);
                this.setColoringAttributes(vColorAtr);
                mAmbiente(vColor, vTroll);
                break;
        }

        switch (vTipoSelected)
        {
            case 1:
                this.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_POINT, PolygonAttributes.CULL_BACK, 0));
                this.setPointAttributes(new PointAttributes(10, false));
                break;
            case 2:
                this.setPolygonAttributes(new PolygonAttributes(
                        PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_BACK, 0));
                this.setLineAttributes(new LineAttributes(3, LineAttributes.PATTERN_DASH, false));
                break;
            case 3:
                this.setPolygonAttributes(new PolygonAttributes(PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0));
                break;
        }

        //P:ara ponerle la iluminacion de con luz ambiental necesito el material incrutado en la parte de la aparincia.
//        PolygonAttributes pa = new PolygonAttributes();
//        appearance.setPolygonAttributes(pa);
//        ColoringAttributes ca = new ColoringAttributes((float) vColor[0].getRed() / 255f, (float) vColor[0].getGreen() / 255f, (float) vColor[0].getBlue() / 255f,ColoringAttributes.FASTEST);
//        appearance.setColoringAttributes(ca);
    }

    private void mAmbiente(Color vColor[], TransformGroup vTroll)
    {
        BoundingSphere bounds = new BoundingSphere();
        AmbientLight light = new AmbientLight(true, new Color3f((float) vColor[1].getRed() / 255f, (float) vColor[1].getGreen() / 255f, (float) vColor[1].getBlue() / 255f));
        light.setInfluencingBounds(bounds);
        vTroll.addChild(light);
        PointLight ptlight = new PointLight(new Color3f((float) vColor[0].getRed() / 255f, (float) vColor[0].getGreen() / 255f, (float) vColor[0].getBlue() / 255f), new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
        ptlight.setInfluencingBounds(bounds);
        vTroll.addChild(ptlight);
    }
}
