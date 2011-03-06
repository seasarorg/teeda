/*
 * Copyright 2004-2011 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
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
