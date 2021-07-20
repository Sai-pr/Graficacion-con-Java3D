package main;

import javax.vecmath.*;
import java.awt.*;
import java.awt.event.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.*;
import java.applet.*;
import com.sun.j3d.utils.applet.MainFrame;
import javax.swing.JPanel;

public class Ex6 extends JPanel implements ActionListener
{
    
    public Ex6()
    {
        setLayout(new BorderLayout());
        
        this.setBackground(Color.red);
        
        Panel p = new Panel();
        Panel p_princ = new Panel();
        p_princ.setBackground(Color.red);
        
        p_princ.add(p);
        
        p.setLayout(new GridLayout(12, 1, 10, 5));
        add(p_princ, BorderLayout.EAST);
        p.add(new Panel());
        p.add(new Label("Polygon Mode"));
        Button button = new Button("Point");
        p.add(button);
        button.addActionListener(this);
        button = new Button("Line");
        p.add(button);
        button.addActionListener(this);
        button = new Button("Polygon");
        p.add(button);
        button.addActionListener(this);
        
        p.add(new Panel());
        p.add(new Label("Coloring Attribute"));
        button = new Button("Single");
        p.add(button);
        button.addActionListener(this);
        button = new Button("Flat");
        p.add(button);
        button.addActionListener(this);
        button = new Button("Gouraud");
        p.add(button);
        button.addActionListener(this);
        button = new Button("Lighting");
        p.add(button);
        button.addActionListener(this);
        
        GraphicsConfiguration gc = SimpleUniverse.getPreferredConfiguration();
        Canvas3D cv = new Canvas3D(gc);
        add(cv, BorderLayout.CENTER);
        BranchGroup bg = createSceneGraph();
        bg.compile();
        SimpleUniverse su = new SimpleUniverse(cv);
        su.getViewingPlatform().setNominalViewingTransform();
        su.addBranchGraph(bg);
        
    }
    
    Appearance ap;
    
    private BranchGroup createSceneGraph()
    {
        BranchGroup root = new BranchGroup();
        TransformGroup spin = new TransformGroup();
        spin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        root.addChild(spin);

        //allow appearance chage
        ap = new Appearance();
        ap.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
        ap.setCapability(Appearance.ALLOW_POINT_ATTRIBUTES_WRITE);
        ap.setCapability(Appearance.ALLOW_LINE_ATTRIBUTES_WRITE);
        ap.setCapability(Appearance.ALLOW_POLYGON_ATTRIBUTES_WRITE);
        ap.setCapability(Appearance.ALLOW_RENDERING_ATTRIBUTES_WRITE);
        ap.setCapability(Appearance.ALLOW_MATERIAL_WRITE);
        
        Shape3D shape = new Shape3D(new ColorPyramidDown());
        shape.setAppearance(ap);
        
        Transform3D tr = new Transform3D();
        tr.setScale(0.25);
        TransformGroup tg = new TransformGroup(tr);
        spin.addChild(tg);
        tg.addChild(shape);
        
        Alpha alpha = new Alpha(-1, 10000);
        RotationInterpolator rotator = new RotationInterpolator(alpha, spin);
        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        spin.addChild(rotator);

        //background and light
        Background background = new Background(1f, 1f, 1f);
        background.setApplicationBounds(bounds);
        root.addChild(background);
        AmbientLight light = new AmbientLight(true, new Color3f(Color.cyan));
        light.setInfluencingBounds(bounds);
        root.addChild(light);
        PointLight ptlight = new PointLight(new Color3f(Color.magenta),
                new Point3f(1f, -5f, 4f), new Point3f(1f, 0f, 0f));
        ptlight.setInfluencingBounds(bounds);
        root.addChild(ptlight);
        return root;
    }
    
    public void actionPerformed(ActionEvent actionEvent)
    {
        String cmd = actionEvent.getActionCommand();
        System.out.println("cmd == " + cmd);
        if ("Point".equals(cmd))
        {
            ap.setPolygonAttributes(new PolygonAttributes(
                    PolygonAttributes.POLYGON_POINT, PolygonAttributes.CULL_BACK, 0));
            ap.setPointAttributes(new PointAttributes(10, false));
        } else if ("Line".equals(cmd))
        {
            ap.setPolygonAttributes(new PolygonAttributes(
                    PolygonAttributes.POLYGON_LINE, PolygonAttributes.CULL_BACK, 0));
            ap.setLineAttributes(new LineAttributes(3,
                    LineAttributes.PATTERN_DASH, false));
        } else if ("Polygon".equals(cmd))
        {
            ap.setPolygonAttributes(new PolygonAttributes(
                    PolygonAttributes.POLYGON_FILL, PolygonAttributes.CULL_BACK, 0));
        } else if ("Single".equals(cmd))
        {
            ColoringAttributes ca = new ColoringAttributes();
            ca.setColor(0.5f, 0.5f, 0.5f);
            ap.setColoringAttributes(ca);
            ap.setMaterial(null);
            RenderingAttributes ra = new RenderingAttributes();
            ra.setIgnoreVertexColors(true);
            ap.setRenderingAttributes(ra);
        } else if ("Flat".equals(cmd))
        {
            ColoringAttributes ca = new ColoringAttributes();
            ca.setShadeModel(ColoringAttributes.SHADE_FLAT);
            ap.setColoringAttributes(ca);
            ap.setMaterial(null);
            RenderingAttributes ra = new RenderingAttributes();
            ra.setIgnoreVertexColors(false);
            ap.setRenderingAttributes(ra);
        } else if ("Gouraud".equals(cmd))
        {
            ColoringAttributes ca = new ColoringAttributes();
            ca.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
            ap.setColoringAttributes(ca);
            ap.setMaterial(null);
            RenderingAttributes ra = new RenderingAttributes();
            ra.setIgnoreVertexColors(false);
            ap.setRenderingAttributes(ra);
        } else if ("Lighting".equals(cmd))
        {
            ap.setMaterial(new Material());
            RenderingAttributes ra = new RenderingAttributes();
            ra.setIgnoreVertexColors(true);
            ap.setRenderingAttributes(ra);
        }
    }
    
    class ColorPyramidDown extends QuadArray
    {
        
        private final float[] verts =
        {
            // front face
            0.0f, -1.0f, 0.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 0.0f,
            -1.0f, 0.0f,
            // back face
            0.0f, -1.0f, 0.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 0.0f,
            -1.0f, 0.0f,
            // right face
            0.0f, -1.0f, 0.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 0.0f,
            -1.0f, 0.0f,
            // left face
            0.0f, -1.0f, 0.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 0.0f,
            -1.0f, 0.0f,
            // top face
            1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f,
            1.0f, 1.0f,
            // bottom face
            0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f,
        };
        
        private final float[] colors =
        {
            // front face (green)
            0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
            0.0f,
            // back face (red)
            1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f,
            0.0f,
            // right face (yellow)
            1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f,
            0.0f,
            // left face (magenta)
            1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f,
            1.0f,
            // top face (blue)
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f,
            1.0f,
            // bottom face (cyan)
            0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f,
            1.0f,
        };
        
        ColorPyramidDown()
        {
            //super(8, GeometryArray.COORDINATES | GeometryArray.NORMALS | GeometryArray.COLOR_3, 24);
            super(24, QuadArray.COORDINATES | QuadArray.COLOR_3 | GeometryArray.ALLOW_COUNT_READ | GeometryArray.NORMALS);
            
            setCoordinates(0, verts);
            setColors(0, colors);
        }
    }
    
    class TetrahedronITA extends IndexedTriangleArray
    {
        
        TetrahedronITA()
        {
            super(4, GeometryArray.COORDINATES | GeometryArray.COLOR_3, 12 | GeometryArray.ALLOW_COUNT_READ | GeometryArray.NORMALS);
            
            Point3f verts[] = new Point3f[4];
            Color3f colors[] = new Color3f[4];
            
            verts[0] = new Point3f(1.0f, 1.0f, 1.0f);
            verts[1] = new Point3f(1.0f, -1.0f, -1.0f);
            verts[2] = new Point3f(-1.0f, -1.0f, 1.0f);
            verts[3] = new Point3f(-1.0f, 1.0f, -1.0f);
            
            colors[0] = new Color3f(1.0f, 0.0f, 0.0f);
            colors[1] = new Color3f(0.0f, 1.0f, 0.0f);
            colors[2] = new Color3f(0.0f, 0.0f, 1.0f);
            colors[3] = new Color3f(1.0f, 1.0f, 0.0f);
            
            int pntsIndex[] = new int[12];
            int clrsIndex[] = new int[12];
            
            pntsIndex[0] = 2;
            clrsIndex[0] = 0;
            pntsIndex[1] = 1;
            clrsIndex[1] = 0;
            pntsIndex[2] = 0;
            clrsIndex[2] = 0;
            
            pntsIndex[3] = 3;
            clrsIndex[3] = 1;
            pntsIndex[4] = 2;
            clrsIndex[4] = 1;
            pntsIndex[5] = 0;
            clrsIndex[5] = 1;
            
            pntsIndex[6] = 1;
            clrsIndex[6] = 2;
            pntsIndex[7] = 2;
            clrsIndex[7] = 2;
            pntsIndex[8] = 3;
            clrsIndex[8] = 2;
            
            pntsIndex[9] = 1;
            clrsIndex[9] = 3;
            pntsIndex[10] = 3;
            clrsIndex[10] = 3;
            pntsIndex[11] = 0;
            clrsIndex[11] = 3;
            
            setCoordinates(0, verts);
            setCoordinateIndices(0, pntsIndex);
            setColors(0, colors);
            setColorIndices(0, clrsIndex);
        }
    }
    
    class ColorOctahedron extends IndexedTriangleArray
    {
        
        public ColorOctahedron()
        {
            super(8, GeometryArray.COORDINATES | GeometryArray.NORMALS | GeometryArray.COLOR_3, 24);
            setCoordinate(0, new Point3f(0, 0, 1));
            setCoordinate(1, new Point3f(-1, 0, 0));
            setCoordinate(2, new Point3f(0, -1, 0));
            setCoordinate(3, new Point3f(1, 0, 0));
            setCoordinate(4, new Point3f(0, 1, 0));
            setCoordinate(5, new Point3f(0, 0, -1));
            int[] vertInd =
            {
                0, 1, 2, 0, 2, 3, 0, 3, 4, 0, 4, 1,
                5, 1, 4, 5, 4, 3, 5, 3, 2, 5, 2, 1
            };
            setCoordinateIndices(0, vertInd);
            
            float d = 1f / (float) Math.sqrt(3);
            setNormal(0, new Vector3f(-d, -d, d));
            setNormal(1, new Vector3f(d, -d, d));
            setNormal(2, new Vector3f(d, d, d));
            setNormal(3, new Vector3f(-d, d, d));
            setNormal(4, new Vector3f(-d, d, -d));
            setNormal(5, new Vector3f(d, d, -d));
            setNormal(6, new Vector3f(d, -d, -d));
            setNormal(7, new Vector3f(-d, -d, -d));
            
            int[] normInd =
            {
                0, 0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3,
                4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7
            };
            setNormalIndices(0, normInd);
            setColor(0, new Color3f(1, 0, 0));
            setColor(1, new Color3f(0, 1, 0));
            setColor(2, new Color3f(0, 0, 1));
            setColor(3, new Color3f(1, 0, 1));
            setColor(4, new Color3f(1, 1, 0));
            setColor(5, new Color3f(0, 1, 1));
            this.setColorIndices(0, vertInd);
        }
    }
}
