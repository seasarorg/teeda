package examples.teeda.web.selecttestimg;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

public class ImagePage {
	
	public String text;
	
	public HttpServletResponse httpServletResponse;
	
	public void writeResponse(RenderedImage image) {
		httpServletResponse.setContentType("image/png");
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(httpServletResponse.getOutputStream());
			ImageIO.write(image, "png", bos);
			bos.flush();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public void prerender() {
		BufferedImage image = new BufferedImage(30, 30, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g2d = image.createGraphics();
		g2d.setBackground(Color.WHITE);
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("monospace", Font.PLAIN, 24));
		g2d.drawString(text, 8, 24);
		
		g2d.dispose();
		writeResponse(image);
	}
}
