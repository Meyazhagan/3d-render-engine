package RenderEngine;

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
                            Color.RED));
      //B
      tris.add(new Triangle(new Vertex(100, 100, 100, 1),
                            new Vertex(100, 100, -100, 1),
                            new Vertex(-100, 100, -100, 1),
                            Color.WHITE));
      //C
      tris.add(new Triangle(new Vertex(100, -100, 100, 1),
                            new Vertex(100, 100, -100, 1),
                            new Vertex(100, 100, 100, 1),
                            Color.BLUE));
      //D
      tris.add(new Triangle(new Vertex(100, -100, 100, 1),
                            new Vertex(100, -100, -100, 1),
                            new Vertex(100, 100, -100, 1),
                            Color.GREEN));
      //E
      tris.add(new Triangle(new Vertex(-100, -100, 100, 1),
                            new Vertex(100, -100, 100, 1),
                            new Vertex(-100, 100, 100, 1),
                            Color.ORANGE));

      //F
      tris.add(new Triangle(new Vertex(100, -100, 100, 1),
                            new Vertex(100, 100, 100, 1),
                            new Vertex(-100, 100, 100, 1),
                            Color.DARK_GRAY));
      //G
      tris.add(new Triangle(new Vertex(-100, -100, 100, 1),
                            new Vertex(-100, 100, 100, 1),
                            new Vertex(-100, -100, -100, 1),
                            Color.CYAN));
      //H
      tris.add(new Triangle(new Vertex(-100, 100, 100, 1),
                            new Vertex(-100, 100, -100, 1),
                            new Vertex(-100, -100, -100, 1),
                            Color.GRAY));
      //I
      tris.add(new Triangle(new Vertex(-100, 100, -100, 1),
                            new Vertex(100, 100, -100, 1),
                            new Vertex(-100, -100, -100, 1),
                            Color.MAGENTA));
      //J
      tris.add(new Triangle(new Vertex(-100, -100, -100, 1),
                            new Vertex(100, 100, -100, 1),
                            new Vertex(100, -100, -100, 1),
                            Color.PINK));
      //K
      tris.add(new Triangle(new Vertex(100, -100, 100, 1),
                            new Vertex(-100, -100, 100, 1),
                            new Vertex(-100, -100, -100, 1),
                            Color.YELLOW));
      //L
      tris.add(new Triangle(new Vertex(-100, -100, -100, 1),
                            new Vertex(100, -100, -100, 1),
                            new Vertex(100, -100, 100, 1),
                            Color.LIGHT_GRAY));

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
