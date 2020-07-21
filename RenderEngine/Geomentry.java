package practices;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Geomentry
{
	public static List<Triangle> getSquare(){
		
		List<Triangle> tris = new ArrayList<>();
		//A
      tris.add(new Triangle(new Vertex(-100, 100, 100, 1),
                            new Vertex(100, 100, 100, 1),
                            new Vertex(-100, 100, -100, 1),
                            Color.WHITE));
      //B
      tris.add(new Triangle(new Vertex(100, 100, 100, 1),
                            new Vertex(100, 100, -100, 1),
                            new Vertex(-100, 100, -100, 1),
                            Color.WHITE));
      //C
      tris.add(new Triangle(new Vertex(100, -100, 100, 1),
                            new Vertex(100, 100, -100, 1),
                            new Vertex(100, 100, 100, 1),
                            Color.WHITE));
      //D
      tris.add(new Triangle(new Vertex(100, -100, 100, 1),
                            new Vertex(100, -100, -100, 1),
                            new Vertex(100, 100, -100, 1),
                            Color.WHITE));
      //E
      tris.add(new Triangle(new Vertex(-100, -100, 100, 1),
                            new Vertex(100, -100, 100, 1),
                            new Vertex(-100, 100, 100, 1),
                            Color.WHITE));

      //F
      tris.add(new Triangle(new Vertex(100, -100, 100, 1),
                            new Vertex(100, 100, 100, 1),
                            new Vertex(-100, 100, 100, 1),
                            Color.WHITE));
      //G
      tris.add(new Triangle(new Vertex(-100, -100, 100, 1),
                            new Vertex(-100, 100, 100, 1),
                            new Vertex(-100, -100, -100, 1),
                            Color.WHITE));
      //H
      tris.add(new Triangle(new Vertex(-100, 100, 100, 1),
                            new Vertex(-100, 100, -100, 1),
                            new Vertex(-100, -100, -100, 1),
                            Color.WHITE));
      //I
      tris.add(new Triangle(new Vertex(-100, 100, -100, 1),
                            new Vertex(100, 100, -100, 1),
                            new Vertex(-100, -100, -100, 1),
                            Color.WHITE));
      //J
      tris.add(new Triangle(new Vertex(-100, -100, -100, 1),
                            new Vertex(100, 100, -100, 1),
                            new Vertex(100, -100, -100, 1),
                            Color.WHITE));
      //K
      tris.add(new Triangle(new Vertex(100, -100, 100, 1),
                            new Vertex(-100, -100, 100, 1),
                            new Vertex(-100, -100, -100, 1),
                            Color.WHITE));
      //L
      tris.add(new Triangle(new Vertex(-100, -100, -100, 1),
                            new Vertex(100, -100, -100, 1),
                            new Vertex(100, -100, 100, 1),
                            Color.WHITE));

		return tris;
	}
	public static List<Triangle> getTriangle(){
		List<Triangle> tris = new ArrayList<>();
		 tris.add(new Triangle(new Vertex(100,100,100,1),
				  new Vertex(-100,-100,100,1),
				  new Vertex(-100, 100, -100,1),
				  Color.WHITE));
		tris.add(new Triangle(new Vertex(100,100,100,1),
						  new Vertex(-100,-100,100,1),
						  new Vertex(100, -100, -100,1),
						  Color.RED));
		tris.add(new Triangle(new Vertex(-100,100, -100,1),
						  new Vertex(100,-100, -100,1),
						  new Vertex(100, 100, 100,1),
						  Color.GREEN));
		tris.add(new Triangle(new Vertex(-100, 100, -100,1),
						  new Vertex(100, -100, -100,1),
						  new Vertex(-100, -100, 100,1),
						  Color.BLUE));
		return tris;
		
	}
}
