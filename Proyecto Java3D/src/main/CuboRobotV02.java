
package main;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;

/**
 *
 * @author saipr
 */
public class CuboRobotV02 extends Applet implements KeyListener
{

    double incremento = 10d * Math.PI / 180d;
    double angulo = 0d;

    // Articulacion 1
    //Articulacion a1 = new Articulacion(30d*Math.PI/180d);	
    Articulacion a1 = new Articulacion(0d);

    // Segmento 1
    Segmento s1 = new Segmento(0.2d);

    // Articulacion 2
    //Articulacion a2 = new Articulacion(20d*Math.PI/180d);
    Articulacion a2 = new Articulacion(0d);

    // Segmento 2
    Segmento s2 = new Segmento(0.2d);

    // Articulacion 3
    //Articulacion a3 = new Articulacion(10d*Math.PI/180d);
    Articulacion a3 = new Articulacion(0d);

    // Segmento 3
    Segmento s3 = new Segmento(0.2d);

    public CuboRobotV02()
    {
        System.setProperty("sun.awt.noerasebackground", "true");
        setLayout(new BorderLayout());
        GraphicsConfiguration config
                = SimpleUniverse.getPreferredConfiguration();

        Canvas3D canvas3D = new Canvas3D(config);
        add("Center", canvas3D);
        canvas3D.addKeyListener(this);

        BranchGroup scene = createSceneGraph();

        // SimpleUniverse is a Convenience Utility class
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);

        // This will move the ViewPlatform back a bit so the
        // objects in the scene can be viewed.
        simpleU.getViewingPlatform().setNominalViewingTransform();

        simpleU.addBranchGraph(scene);
    } // end of HelloJava3Da (constructor)

    public BranchGroup createSceneGraph()
    {
        // Raiz de la escena
        BranchGroup objRoot = new BranchGroup();

        // Subir la mitad de la longitud del primer segmento
        Transform3D t3d = new Transform3D();
        t3d.set(new Vector3d());
        TransformGroup tg0 = new TransformGroup(t3d);

        // Agregar la primera articulacion
        tg0.addChild(a1.getTransformGroup());
        // Agregar el primer segmento
        a1.getTransformGroup().addChild(s1.getTransformGroup0());
        // Agregar la segunda articulacion
        s1.getTransformGroup2().addChild(a2.getTransformGroup());
        // Agregar el segundo segmento
        a2.getTransformGroup().addChild(s2.getTransformGroup0());
        // Agregar la segunda articulacion
        s2.getTransformGroup2().addChild(a3.getTransformGroup());
        // Agregar el segundo segmento
        a3.getTransformGroup().addChild(s3.getTransformGroup0());
        // Agregar la raiz del arbol a la escena
        objRoot.addChild(tg0);

        return objRoot;
    } // end of CreateSceneGraph method of HelloJava3Da

    public void keyPressed(KeyEvent e)
    {

        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            angulo += incremento;
            System.out.println("arriba");
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            angulo -= incremento;
            System.out.println("abajo");
        }

        // Ponerle un tope al ángulo de giro
        if (angulo > Math.PI / 4)
        {
            angulo = Math.PI / 4;
        } else if (angulo < -Math.PI / 4)
        {
            angulo = -Math.PI / 4;
        }

        a1.setAngulo(angulo);
        a2.setAngulo(angulo);
        a3.setAngulo(angulo);

    }

    public void keyReleased(KeyEvent e)
    {
        //System.out.println(e);
    }

    public void keyTyped(KeyEvent e)
    {
        //System.out.println(e);
    }

    public static void main(String[] args)
    {
        Frame frame = new MainFrame(new CuboRobotV02(), 512, 512);
    } // end of main (method of HelloJava3Da)

    /*
 * Segmento
 * Esta clase constituye una "falange" del dedo.
 * tg0 es el comienzo de la falange
 * tg1 es el medio de la falange
 * tg2 es el fin de la falange
     */
    private class Segmento
    {

        double longitud;
        // tg0 es el grupo del cual se pega este segmento a la articulaci�n pap�
        // tg1 es el grupo al cual se pega el cubo de esta articulaci�n
        // tg2 es el grupo del cual se pega la articulaci�n hija
        TransformGroup tg0, tg1, tg2;
        Transform3D t3D;

        public Segmento(double longitud)
        {
            this.longitud = longitud;
            t3D = new Transform3D();
            t3D.set(new Vector3d(longitud / 2d, 0d, 0d));
            tg0 = new TransformGroup();
            tg1 = new TransformGroup(t3D);
            Transform3D t3d11 = new Transform3D();
            t3d11.setScale(new Vector3d(1d, .5d, .5d));
            TransformGroup tg11 = new TransformGroup(t3d11);
            tg1.addChild(tg11);
            tg11.addChild(new ColorCube(longitud / 2d));
            tg0.addChild(tg1);
            tg2 = new TransformGroup(t3D);
            tg1.addChild(tg2);
        }

        public void setLongitud(double longitud)
        {
            this.longitud = longitud;
        }

        public double getLongitud()
        {
            return longitud;
        }

        public TransformGroup getTransformGroup0()
        {
            return tg0;
        }

        public TransformGroup getTransformGroup2()
        {
            return tg2;
        }
    }

    /*
 * Articulacion
 * Esta clase maneja las "articulaciones" que unen las falanges
 * La articulaci�n tiene solamente un TransformGroup que se llama tg.
     */
    private class Articulacion
    {

        double angulo;
        TransformGroup tg;
        Transform3D t3D;

        public Articulacion(double angulo)
        {
            this.angulo = angulo;
            t3D = new Transform3D();
            t3D.rotZ(angulo);
            tg = new TransformGroup(t3D);
            // MUY IMPORTANTE
            // si esta "capability" no se modifica, el programa no funciona.
            tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        }

        public void setAngulo(double angulo)
        {
            this.angulo = angulo;
            t3D = new Transform3D();
            t3D.rotZ(angulo);
            tg.setTransform(t3D);
        }

        public double getAngulo()
        {
            return angulo;
        }

        public TransformGroup getTransformGroup()
        {
            return tg;
        }
    }

} // end of class HelloJava3Da
