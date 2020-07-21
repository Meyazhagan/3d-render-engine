package RenderEngine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;

public class renderEngine
{
	int pitchSlider;
	int headingSlider;
	int rollSlider;
	JPanel renderPanel;
	int lastX =0;
	int lastY=0;
	List<Triangle> geo;
	JToggleButton button;
	boolean rotateFlag = false;
	
    public static void main(String[] args) {
    	new renderEngine();
    }
    
    public renderEngine() {
    	JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout());

        // slider to control FoV
        JSlider FoVSlider = new JSlider(1, 179, 100);
        pane.add(FoVSlider, BorderLayout.NORTH);

        // panel to display render results
        renderPanel = new JPanel() {
            private static final long serialVersionUID = 1L;

            public void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.black);
                    g2.fillRect(0, 0, getWidth(), getHeight());

                    geo = getGeo();
                    
                    double heading = Math.toRadians(headingSlider);
                    Matrix4 headingTransform = new Matrix4(new double[] {
                            Math.cos(heading), 0, -Math.sin(heading), 0,
                            0, 1, 0, 0,
                            Math.sin(heading), 0, Math.cos(heading), 0,
                            0, 0, 0, 1
                        });
                    double pitch = Math.toRadians(pitchSlider);
                    Matrix4 pitchTransform = new Matrix4(new double[] {
                            1, 0, 0, 0,
                            0, Math.cos(pitch), Math.sin(pitch), 0,
                            0, -Math.sin(pitch), Math.cos(pitch), 0,
                            0, 0, 0, 1
                        });
                    double roll = Math.toRadians(rollSlider);
                    Matrix4 rollTransform = new Matrix4(new double[] {
                            Math.cos(roll), -Math.sin(roll), 0, 0,
                            Math.sin(roll), Math.cos(roll), 0, 0,
                            0, 0, 1, 0,
                            0, 0, 0, 1
                        });

                    Matrix4 panOutTransform = new Matrix4(new double[] {
                            1, 0, 0, 0,
                            0, 1, 0, 0,
                            0, 0, 1, 0,
                            0, 0, -400, 1
                        });

                    double viewportWidth = getWidth();
                    double viewportHeight = getHeight();
                    double fovAngle = Math.toRadians(FoVSlider.getValue());
                    double fov = Math.tan(fovAngle / 2) * 170;

                    Matrix4 transform =
                        headingTransform
                        .multiply(pitchTransform)
                        .multiply(rollTransform)
                        .multiply(panOutTransform);

                    BufferedImage img = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);

                    double[] zBuffer = new double[img.getWidth() * img.getHeight()];
                    // initialize array with extremely far away depths
                    for (int q = 0; q < zBuffer.length; q++) {
                        zBuffer[q] = Double.NEGATIVE_INFINITY;
                    }

                    for (Triangle t : geo) {
                        Vertex v1 = transform.transform(t.v1);
                        Vertex v2 = transform.transform(t.v2);
                        Vertex v3 = transform.transform(t.v3);

                        Vertex ab = new Vertex(v2.x - v1.x, v2.y - v1.y, v2.z - v1.z, v2.w - v1.w);
                        Vertex ac = new Vertex(v3.x - v1.x, v3.y - v1.y, v3.z - v1.z, v3.w - v1.w);
                        Vertex norm = new Vertex(
                                                 ab.y * ac.z - ab.z * ac.y,
                                                 ab.z * ac.x - ab.x * ac.z,
                                                 ab.x * ac.y - ab.y * ac.x,
                                                 1
                                                 );
                        double normalLength = Math.sqrt(norm.x * norm.x + norm.y * norm.y + norm.z * norm.z);
                        norm.x /= normalLength;
                        norm.y /= normalLength;
                        norm.z /= normalLength;

                        double angleCos = Math.abs(norm.z);

                        v1.x = v1.x / (-v1.z) * fov;
                        v1.y = v1.y / (-v1.z) * fov;
                        v2.x = v2.x / (-v2.z) * fov;
                        v2.y = v2.y / (-v2.z) * fov;
                        v3.x = v3.x / (-v3.z) * fov;
                        v3.y = v3.y / (-v3.z) * fov;

                        v1.x += viewportWidth / 2;
                        v1.y += viewportHeight / 2;
                        v2.x += viewportWidth / 2;
                        v2.y += viewportHeight / 2;
                        v3.x += viewportWidth / 2;
                        v3.y += viewportHeight / 2;

                        int minX = (int) Math.max(0, Math.ceil(Math.min(v1.x, Math.min(v2.x, v3.x))));
                        int maxX = (int) Math.min(img.getWidth() - 1, Math.floor(Math.max(v1.x, Math.max(v2.x, v3.x))));
                        int minY = (int) Math.max(0, Math.ceil(Math.min(v1.y, Math.min(v2.y, v3.y))));
                        int maxY = (int) Math.min(img.getHeight() - 1, Math.floor(Math.max(v1.y, Math.max(v2.y, v3.y))));

                        double triangleArea = (v1.y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - v1.x);

                        for (int y = minY; y <= maxY; y++) {
                            for (int x = minX; x <= maxX; x++) {
                                double b1 = ((y - v3.y) * (v2.x - v3.x) + (v2.y - v3.y) * (v3.x - x)) / triangleArea;
                                double b2 = ((y - v1.y) * (v3.x - v1.x) + (v3.y - v1.y) * (v1.x - x)) / triangleArea;
                                double b3 = ((y - v2.y) * (v1.x - v2.x) + (v1.y - v2.y) * (v2.x - x)) / triangleArea;
                                if (b1 >= 0 && b1 <= 1 && b2 >= 0 && b2 <= 1 && b3 >= 0 && b3 <= 1) {
                                    double depth = b1 * v1.z + b2 * v2.z + b3 * v3.z;
                                    int zIndex = y * img.getWidth() + x;
                                    if (zBuffer[zIndex] < depth) {
                                        img.setRGB(x, y, getShade(t.color, angleCos).getRGB());
                                        zBuffer[zIndex] = depth;
                                    }
                                }
                            }
                        }

                    }

                    g2.drawImage(img, 0, 0, null);
                }
            };
          MouseData ml = new MouseData();
        renderPanel.addMouseMotionListener(ml);
        renderPanel.addMouseListener(ml);
        pane.add(renderPanel, BorderLayout.CENTER);
        
        button = new JToggleButton("Square");
        button.addItemListener(ml);
        pane.add(button, BorderLayout.SOUTH);

        FoVSlider.addChangeListener(e -> renderPanel.repaint());

        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
       

    public Color getShade(Color color, double shade) {
        double redLinear = Math.pow(color.getRed(), 2.4) * shade;
        double greenLinear = Math.pow(color.getGreen(), 2.4) * shade;
        double blueLinear = Math.pow(color.getBlue(), 2.4) * shade;

        int red = (int) Math.pow(redLinear, 1/2.4);
        int green = (int) Math.pow(greenLinear, 1/2.4);
        int blue = (int) Math.pow(blueLinear, 1/2.4);

        return new Color(red, green, blue);
    }
    
    private List<Triangle> getGeo()
	{
		if(button.isSelected()) {
			return Geomentry.getTriangle();
		}
		else {
			return Geomentry.getSquare();
		}
	}
    
    class MouseData implements MouseMotionListener, MouseListener, ItemListener
    {
    	@Override
    	public void mouseDragged(MouseEvent e)
    	{
    	}

    	@Override
    	public void mouseMoved(MouseEvent e)
    	{
    		if(rotateFlag) {
	    		headingSlider = (lastX - e.getX()) % 360;
	    		pitchSlider = (lastY - e.getY()) % 360;
	    		renderPanel.repaint();
	    	}
    	}

    	@Override
    	public void mouseClicked(MouseEvent e)
    	{
    		rotateFlag = !rotateFlag;
    		lastX = headingSlider;
    		lastY = pitchSlider;
    	}

    	@Override
    	public void mousePressed(MouseEvent e)
    	{
    		
    	}

    	@Override
    	public void mouseReleased(MouseEvent e)
    	{
    	}

    	@Override
    	public void mouseEntered(MouseEvent e)
    	{	
    	}

    	@Override
    	public void mouseExited(MouseEvent e)
    	{	
    	}

		@Override
		public void itemStateChanged(ItemEvent e)
		{
		  if (button.isSelected())  
	            button.setText("Triangle"); 
	      else  
	           button.setText("Square");
		  renderPanel.repaint();
		}

    }
}
