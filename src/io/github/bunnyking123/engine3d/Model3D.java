package io.github.bunnyking123.engine3d;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Model3D {
	
	public static final double[] ZERO = {0, 0, 0};
	
	private double[] center = new double[3];
	private HashMap<String, double[]> nodes = new HashMap<String, double[]>();
	private ArrayList<String[]> faces = new ArrayList<String[]>();
	private double[] eye = new double[3];
	private double[] light = new double[3];
	private double lightRadius;
	private double renderDistance;
	private boolean renderable = false;
	
	private Model3D() {}
	
	/**
	 * This method is used to build your 3D model
	 * 
	 * @param centerX - X-Value of the model's center point
	 * @param centerY - Y-Value of the model's center point
	 * @param centerZ - Z-Value of the model's center point
	 * @param eye - An array composed of 4 values: x, y, z and render distance. Represents the camera/pov
	 * @param light - An array composed of 4 values: x, y, z and radius. Represents a main light source
	 * @return a blank Model3D template
	 */
	public static Model3D createModel(double centerX, double centerY, double centerZ, double[] eye, double[] light) {
		Model3D model = new Model3D();
		model.center[0] = centerX;
		model.center[1] = centerY;
		model.center[2] = centerZ;
		model.eye[0] = eye[0];
		model.eye[1] = eye[1];
		model.eye[2] = eye[2];
		model.light[0] = light[0];
		model.light[1] = light[1];
		model.light[2] = light[2];
		model.lightRadius = light[3];
		model.renderDistance = eye[3];
		return model;
	}
	
	/**
	 * Adds a node to the model, nodes can be connected to form the faces of
	 * a 3D figure
	 * 
	 * @param name - Name used to identify the node
	 * @param x - X-Coordinate of the node
	 * @param y - Y-Coordinate of the node
	 * @param z - Z-Coordinate of the node
	 * @return the modified 3D model (with the added node)
	 */
	public Model3D addNode(String name, double x, double y, double z) {
		if (!renderable) {
			double[] node = {x, y, z};
			nodes.put(name, node);
		}
		return this;
	}

	/**
	 * Connect nodes by identifying them, connections between nodes are made in order 
	 * of the nodes added to the face when rendered. If you
	 * 
	 * @param nodes - Array of node names used to identify the nodes being connected
	 * @return the modified 3D model (with the added node)
	 */
	public Model3D createFace(String... nodes) {
		if (!renderable) {
			String[] nodeKeys = new String[nodes.length];
			for (int i = 0; i < nodes.length; i++) {
				nodeKeys[i] = nodes[i];
			}
			faces.add(nodeKeys);
		}
		return this;
	}
	
	public Model3D finish() {
		renderable = true;
		return this;
	}
	
	/**
	 * Rotate the 3D model about the Z axis
	 * 
	 * @param angle - Angle of rotation
	 */
	public void rotateZ(double angle) {
		double sinAngle = Math.sin(angle);
		double cosAngle = Math.cos(angle);
		nodes.forEach((s, i) -> {
			double orgX = i[0];
			double orgY = i[1];
			i[0] = (orgX * cosAngle - orgY * sinAngle);
			i[1] = (orgY * cosAngle + orgX * sinAngle);
		});
	}
	
	/**
	 * Rotate the 3D model about the X axis
	 * 
	 * @param angle - Angle of rotation
	 */
	public void rotateX(double angle) {
		double sinAngle = Math.sin(angle);
		double cosAngle = Math.cos(angle);
		nodes.forEach((s, i) -> {
			double orgY = i[1];
			double orgZ = i[2];
			i[1] = (orgY * cosAngle - orgZ * sinAngle);
			i[2] = (orgZ * cosAngle + orgY * sinAngle);
		});
	}
	
	/**
	 * Rotate the 3D model about the Y axis
	 * 
	 * @param angle - Angle of rotation
	 */
	public void rotateY(double angle) {
		double sinAngle = Math.sin(angle);
		double cosAngle = Math.cos(angle);
		nodes.forEach((s, i) -> {
			double orgX = i[0];
			double orgZ = i[2];
			i[0] = (orgX * cosAngle - orgZ * sinAngle);
			i[2] = (orgZ * cosAngle + orgX * sinAngle);
		});
	}
	
	/**
	 * Translate the 3D figure
	 * 
	 * @param x - Amount of change in x
	 * @param y - Amount of change in y
	 * @param z - Amount of change in z
	 * @return the modified 3D figure
	 */
	public Model3D translate(double x, double y, double z) {
		this.center[0] += x;
		this.center[1] += y;
		this.center[2] += z;
		return this;
	}
	
	/**
	 * Dilate the 3D figure
	 * 
	 * @param factor - Dilation factor
	 * @return the modified 3D figure
	 */
	public Model3D dilate(double factor) {
		this.nodes.forEach((tag, node) -> {
			node[0] *= factor;
			node[1] *= factor;
			node[2] *= factor;
		});
		return this;
	}
	
	/**
	 * Renders the 3D figure
	 * 
	 * @param nodes - Determines whether you want to render the nodes or not
	 * @param edges - Determines whether you want to render the edges or not
	 * @param faces - Determines whether you want to render the faces or not
	 * @param g - A Graphics object from java.awt
	 */
	public void render(boolean nodes, boolean edges, boolean faces, Graphics g) {
		if (renderable) {
			if (nodes) {
				this.nodes.forEach((s, i) -> {
					g.setColor(Color.GREEN);
					g.fillOval((int) Math.round(i[0] + center[0]) - 5, (int) Math.round(i[1] + center[1]) - 5, 10, 10);
				});
			}
			
			if (faces) {
				HashMap<String, double[]> modNodes = new HashMap<String, double[]>();
				double[] renderEndPoint = {this.eye[0] - this.center[0], this.eye[1] - this.center[1], this.eye[2] - this.renderDistance - this.center[2]};
				this.nodes.forEach((tag, node) ->  {
					double[] dist = {renderEndPoint[0] - node[0], renderEndPoint[1] - node[1], renderEndPoint[2] - node[2]};
					double distZ = dist[2];
					double mag = distance(dist, Model3D.ZERO);
					for (int i = 0; i < 3; i++) {
						dist[i] /= mag;
					}
					double[] newNode = {node[0] + (dist[0] * (distZ - renderEndPoint[2])),
										node[1] + (dist[1] * (distZ - renderEndPoint[2])),
										node[2]};
					System.out.println(tag);
					System.out.println((dist[0] * (distZ - renderEndPoint[2])));
					System.out.println(newNode[0]);
					System.out.println(newNode[1]);
					System.out.println(node[0]);
					System.out.println(node[1]);
					System.out.println();
					modNodes.put(tag, newNode);
				});
				System.out.println();
	
				
				ArrayList<String[]> sortedFaces = sortByAvg(this.faces, this.eye);
				ArrayList<String[]> lightSort = sortByDist(this.faces, this.light);
				
				for(String[] face:sortedFaces) {
					Polygon fill = new Polygon();
					Polygon outline = new Polygon();
					for(String node:face) {
						double[] selectedNode = modNodes.get(node);
						fill.addPoint((int) Math.round(selectedNode[0] + this.center[0]), (int) Math.round(selectedNode[1] + this.center[1]));
						outline.addPoint((int) Math.round(selectedNode[0] + this.center[0]), (int) Math.round(selectedNode[1] + this.center[1]));
					}
					
					g.setColor(Color.CYAN);
					g.fillPolygon(fill);
					
					double[] relLight = {this.light[0] - this.center[0], this.light[1] - this.center[1], this.light[2] - this.center[2]};
					int light = (int) Math.round((distance(calcCenter(lightSort.get(lightSort.indexOf(face))), relLight)/lightRadius) * 50);
					if (light > 255) {
						light = 255;
					}
					
					Color col = new Color(100, 100, 100, light);
					g.setColor(col);
					g.fillPolygon(fill);
					
					if (edges) {
						g.setColor(Color.BLACK);
						g.drawPolygon(outline);
					}
				}
				System.out.println();
			}
		}
	}
	
	/**
	 * Creates a clone of the original object, rotation, translations and dilations are kept
	 * 
	 * @return an exact copy of the model
	 */
	public Model3D clone() {
		double[] newEye = {eye[0], eye[1], eye[2], this.renderDistance};
		double[] newLight = {light[0], light[1], light[2], this.lightRadius};
		Model3D clone = Model3D.createModel(this.center[0], this.center[1], this.center[2], newEye, newLight);
		this.nodes.forEach((tag, node) -> {
			clone.addNode(tag, node[0], node[1], node[2]);
		});
		
		for(String[] face:this.faces) {
			clone.createFace(face);
		}
		return clone;
	}
	
	private double[] calcCenter(String[] face) {
		double[] center = new double[3];
		for (String node:face) {
			double[] selectedNode = this.nodes.get(node);
			center[0] += selectedNode[0];
			center[1] += selectedNode[1];
			center[2] += selectedNode[2];
		}
		
		for (int i = 0; i < 3; i++) {
			center[i] /= face.length;
		}
		
		return center;
	}
	
	private double distance(double[] node1, double[] node2) {
		double dist = Math.sqrt(Math.pow(node2[0] - node1[0], 2) + Math.pow(node2[1] - node1[1], 2));
		dist = Math.sqrt(Math.pow(dist, 2) + Math.pow(node2[2] - node1[2], 2));
		return dist;
	}
	
	private double avgZ(String[] face){
		double z = 0;
		for (String node:face) {
			double[] selectedNode = this.nodes.get(node);
			z += selectedNode[2];
		}
		z /= face.length;
		
		return z;
	}
	
	private ArrayList<String[]> sortByAvg(ArrayList<String[]> faces, double[] compareNode) {
		ArrayList<String[]> sorted = new ArrayList<String[]>();
		ArrayList<String[]> original = new ArrayList<String[]>();
		double[] comparable = {compareNode[0] - this.center[0],
							   compareNode[1] - this.center[1],
							   compareNode[2] - this.center[2]};
		for (String[] face:faces) {
			original.add(face);
		}
		
		for (int i = 0; i < faces.size(); i++) {
			int index = 0;
			for (String[] face:original) {
				if (comparable[2] - avgZ(face) < comparable[2] - avgZ(original.get(index))) {
					index = original.indexOf(face);
				}
			}
			sorted.add(0, original.get(index));
			original.remove(index);
		}
		
		return sorted;
	}
	
	private ArrayList<String[]> sortByDist(ArrayList<String[]> faces, double[] compareNode) {
		ArrayList<String[]> sorted = new ArrayList<String[]>();
		ArrayList<String[]> original = new ArrayList<String[]>();
		double[] comparable = {compareNode[0] - this.center[0],
							   compareNode[1] - this.center[1],
							   compareNode[2] - this.center[2]};
		for (String[] face:faces) {
			original.add(face);
		}
		
		for (int i = 0; i < faces.size(); i++) {
			int index = 0;
			for (String[] face:original) {
				if (distance(calcCenter(face), comparable) < distance(calcCenter(original.get(index)), comparable)) {
					index = original.indexOf(face);
				}
			}
			sorted.add(0, original.get(index));
			original.remove(index);
		}
		
		return sorted;
	}
	
	private Image getRss(String rss) {
		Image img = null;
		try {
			File file = new File(this.getClass().getResource(rss).getPath());
			img = ImageIO.read(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return img;
	}
}
