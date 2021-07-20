package ch2;

import javax.vecmath.*;
import javax.media.j3d.*;

public class TetrahedronTriangleArray extends TriangleArray
{

    public TetrahedronTriangleArray()
    {

        super(12, TriangleArray.COORDINATES | TriangleArray.NORMALS);
        setCoordinate(0, new Point3f(1f, 1f, 1f));
        setCoordinate(1, new Point3f(1f, -1, -1f));
        setCoordinate(2, new Point3f(-1f, 1f, -1f));

        setCoordinate(3, new Point3f(1f, 1f, 1f));
        setCoordinate(4, new Point3f(-1f, -1f, 1f));
        setCoordinate(5, new Point3f(1f, -1, -1f));

        setCoordinate(6, new Point3f(1f, -1, -1f));
        setCoordinate(7, new Point3f(-1f, -1f, 1f));
        setCoordinate(8, new Point3f(-1f, 1f, -1f));

        setCoordinate(9, new Point3f(-1f, 1f, -1f));
        setCoordinate(10, new Point3f(-1f, -1f, 1f));
        setCoordinate(11, new Point3f(1f, 1f, 1f));

        float n = (float) (1.0 / Math.sqrt(3));

        setNormal(0, new Vector3f(n, n, -n));
        setNormal(1, new Vector3f(n, n, -n));
        setNormal(2, new Vector3f(n, n, -n));

        setNormal(3, new Vector3f(n, -n, n));
        setNormal(4, new Vector3f(n, -n, n));
        setNormal(5, new Vector3f(n, -n, n));

        setNormal(6, new Vector3f(-n, -n, -n));
        setNormal(7, new Vector3f(-n, -n, -n));
        setNormal(8, new Vector3f(-n, -n, -n));

        setNormal(9, new Vector3f(-n, n, n));
        setNormal(10, new Vector3f(-n, n, n));
        setNormal(11, new Vector3f(-n, n, n));
    }
}
