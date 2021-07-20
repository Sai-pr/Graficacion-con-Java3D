//      CAVEES  160421
package Ch2;

import java.awt.*;
import javax.swing.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.universe.*;
import javax.vecmath.*;

public class ExTetrahedronTriangleArray extends JPanel
{

    public static void main(String[] args)
    {
        new SnippetFrame(new ExTetrahedronTriangleArray(), "Tetrahedron 2");
    }

    private BoundingSphere bounds = new BoundingSphere();

    public ExTetrahedronTriangleArray()
    {
        System.setProperty("sun.awt.noerasebackground", "true");
        SimpleUniverse simpleUniverse;
        GraphicsConfiguration graphicsConfiguration;
        Canvas3D canvas3D;
        BranchGroup branchGroup;

        graphicsConfiguration = SimpleUniverse.getPreferredConfiguration();
        canvas3D = new Canvas3D(graphicsConfiguration);
        simpleUniverse = new SimpleUniverse(canvas3D);
        branchGroup = createSceneGraph();

        simpleUniverse.getViewingPlatform().setNominalViewingTransform();
        simpleUniverse.addBranchGraph(branchGroup);

        setLayout(new BorderLayout());
        add(canvas3D, BorderLayout.CENTER);
    }

    private BranchGroup createSceneGraph()
    {
        BranchGroup root = new BranchGroup();
        TransformGroup spinTransformGroup = createInterpolatorSpin();
        TransformGroup scaleTransfromGroup = createScaleTransfromGroup();

        spinTransformGroup.addChild(createTetrahedronShape());
        scaleTransfromGroup.addChild(spinTransformGroup);

        root.addChild(scaleTransfromGroup);
        root.addChild(createBackground());
        root.addChild(createAmbientLight());
        root.addChild(createPointLight1());
        root.addChild(createPointLight2());

        root.compile();
        return root;
    }

    private TransformGroup createScaleTransfromGroup()
    {
        TransformGroup scaleTransfromGroup = new TransformGroup();
        Transform3D transform3D = new Transform3D();

        transform3D.setScale(0.5);
        scaleTransfromGroup.setTransform(transform3D);

        return scaleTransfromGroup;
    }

    private TransformGroup createInterpolatorSpin()
    {
        TransformGroup objectSpin = new TransformGroup();
        Alpha rotationAlpha = new Alpha(-1, 4000);
        RotationInterpolator interpolator = new RotationInterpolator(rotationAlpha, objectSpin);
        BoundingSphere bounds = new BoundingSphere();

        objectSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        interpolator.setSchedulingBounds(bounds);
        objectSpin.addChild(interpolator);

        return objectSpin;
    }

    private Shape3D createTetrahedronShape()
    {
        Shape3D shape;

        Appearance appearance = new Appearance();
        appearance.setMaterial(new Material());
        shape = new Shape3D(new TetrahedronTriangleArray(), appearance);
        return shape;
    }

    private Background createBackground()
    {
        Background background = new Background(1.0f, 1.0f, 1.0f);
        background.setApplicationBounds(bounds);
        return background;
    }

    private AmbientLight createAmbientLight()
    {
        AmbientLight ambientLight = new AmbientLight(true, new Color3f(Color.red));
        ambientLight.setInfluencingBounds(bounds);
        return ambientLight;
    }

    private PointLight createPointLight1()
    {
        PointLight pointlight = new PointLight(new Color3f(Color.magenta),
                new Point3f(3f, 3f, 3f), new Point3f(1f, 0f, 0f));
        pointlight.setInfluencingBounds(bounds);
        return pointlight;
    }

    private PointLight createPointLight2()
    {
        PointLight pointlight = new PointLight(new Color3f(Color.cyan),
                new Point3f(-2f, 2f, 2f), new Point3f(1f, 0f, 0f));
        pointlight.setInfluencingBounds(bounds);
        return pointlight;
    }
}
