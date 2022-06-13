package platform.framework.render;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Model extends Rectangle {

	private Color texture;

	public Model(int w, int h) {
		super(w, h);
		this.texture=Color.black;
	}
	public Model(int w, int h, Color texture) {
		super(w, h);
		this.texture = texture;
	}


	public Color getTexture() {
		return texture;
	}
}
