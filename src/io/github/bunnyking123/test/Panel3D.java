package io.github.bunnyking123.test;

import java.awt.Graphics;

import javax.swing.JPanel;

import io.github.bunnyking123.engine3d.Model3D;

public class Panel3D extends JPanel {
	
	Model3D model;
	Model3D model2;
	Model3D translatedAndDilated;
	
	public Panel3D() {
		double[] eye = {400, 400, -400, 800};
		double[] light = {400, 400, 200, 150};
		/*
		model = Model3D.createModel(400, 400, 0, eye, light)
			.addNode("top-left-front", -100, -100, 100)
			.addNode("top-right-front", 100, -100, 100)
			.addNode("top-right-back", 100, -100, -100)
			.addNode("top-left-back", -100, -100, -100)
			.addNode("bot-left-front", -100, 100, 100)
			.addNode("bot-right-front", 100, 100, 100)
			.addNode("bot-right-back", 100, 100, -100)
			.addNode("bot-left-back", -100, 100, -100)
			.addNode("tip", 0, -250, 0)
			.addNode("tip2", 0, 250, 0)
			.createFace("top-left-front", "top-right-front", "top-right-back", "top-left-back")
			.createFace("bot-left-front", "bot-right-front", "bot-right-back", "bot-left-back")
			.createFace("top-left-front", "bot-left-front", "bot-right-front", "top-right-front")
			.createFace("top-left-back", "bot-left-back", "bot-right-back", "top-right-back")
			.createFace("top-left-front", "bot-left-front", "bot-left-back", "top-left-back")
			.createFace("top-right-front", "bot-right-front", "bot-right-back", "top-right-back")
			.createFace("top-left-front", "tip", "top-right-front")
			.createFace("top-left-back", "tip", "top-right-back")
			.createFace("top-left-front", "tip", "top-left-back")
			.createFace("top-right-front", "tip", "top-right-back")
			.createFace("bot-left-front", "tip2", "bot-right-front")
			.createFace("bot-left-back", "tip2", "bot-right-back")
			.createFace("bot-left-front", "tip2", "bot-left-back")
			.createFace("bot-right-front", "tip2", "bot-right-back");
		*/
		
		model = Model3D.createModel(400, 400, -200, eye, light)
				.addNode("main-tip", 0, -250, 0)
				.addNode("main-top-left-front", -90, -100, 90)
				.addNode("main-top-right-front", 90, -100, 90)
				.addNode("main-top-left-back", -90, -100, -90)
				.addNode("main-top-right-back", 90, -100, -90)
				.addNode("main-bot-left-front", -100, 100, 100)
				.addNode("main-bot-right-front", 100, 100, 100)
				.addNode("main-bot-right-back", 100, 100, -100)
				.addNode("main-bot-left-back", -100, 100, -100)
				.addNode("fdt-top-left-front", -120, 100, 120)
				.addNode("fdt-top-right-front", 120, 100, 120)
				.addNode("fdt-top-left-back", -120, 100, -120)
				.addNode("fdt-top-right-back", 120, 100, -120)
				.addNode("fdt-bot-left-front", -130, 120, 130)
				.addNode("fdt-bot-right-front", 130, 120, 130)
				.addNode("fdt-bot-left-back", -130, 120, -130)
				.addNode("fdt-bot-right-back", 130, 120, -130)
				.createFace("main-top-left-front", "main-tip", "main-top-right-front")
				.createFace("main-top-left-back", "main-tip", "main-top-right-back")
				.createFace("main-top-left-front", "main-tip", "main-top-left-back")
				.createFace("main-top-right-front", "main-tip", "main-top-right-back")
				.createFace("main-top-left-front", "main-top-right-front", "main-bot-right-front", "main-bot-left-front")
				.createFace("main-top-left-back", "main-top-right-back", "main-bot-right-back", "main-bot-left-back")
				.createFace("main-top-right-front", "main-top-right-back", "main-bot-right-back", "main-bot-right-front")
				.createFace("main-top-left-front", "main-top-left-back", "main-bot-left-back", "main-bot-left-front")
				.createFace("fdt-top-left-front", "fdt-top-right-front", "fdt-bot-right-front", "fdt-bot-left-front")
				.createFace("fdt-top-left-back", "fdt-top-right-back", "fdt-bot-right-back", "fdt-bot-left-back")
				.createFace("fdt-top-right-front", "fdt-top-right-back", "fdt-bot-right-back", "fdt-bot-right-front")
				.createFace("fdt-top-left-front", "fdt-top-left-back", "fdt-bot-left-back", "fdt-bot-left-front")
				.createFace("fdt-top-left-front", "fdt-top-right-front", "fdt-top-right-back", "fdt-top-left-back")
				.createFace("fdt-bot-left-front", "fdt-bot-right-front", "fdt-bot-right-back", "fdt-bot-left-back")
				.finish()
				.translate(-200, 0, 0);
		//model2 = model.clone().finish().translate(400, 0, -300);
		//translatedAndDilated = model.clone().translate(-300, 0, 0).dilate(1.5);
		//model.dilate(0.01);
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, 800, 800);
		model.rotateY(Math.toRadians(1));
		model.rotateX(Math.toRadians(1));
		//translatedAndDilated.rotateY(Math.toRadians(1));
		//translatedAndDilated.rotateX(Math.toRadians(1));
		//model.dilate(1.01);
		//model.translate(-1, 0, -1);
		model.render(false, false, true, g);
		//model2.render(false, false, true, g);
		//translatedAndDilated.render(false, false, true, g);
	}
	
}
