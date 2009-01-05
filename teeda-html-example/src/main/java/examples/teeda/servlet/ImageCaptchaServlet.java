/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package examples.teeda.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import examples.teeda.util.CaptchaServiceSingleton;
import examples.teeda.util.CaptchaUtil;

/**
 * CAPTCHA 用の画像を生成するサーブレットです。
 * 
 * <code>web.xml</code> に以下のように記述します。
 * 
 * <pre>
 *           &lt;servlet&gt;
 *             &lt;servlet-name&gt;captcha&lt;/servlet-name&gt;
 *             &lt;display-name&gt;captcha&lt;/display-name&gt;
 *             &lt;servlet-class&gt;net.of_my.s2captcha.servlet.ImageCaptchaServlet&lt;/servlet-class&gt;
 *             &lt;load-on-startup&gt;3&lt;/load-on-startup&gt;
 *           &lt;/servlet&gt;
 * 
 *           &lt;servlet-mapping&gt;
 *             &lt;servlet-name&gt;captcha&lt;/servlet-name&gt;
 *             &lt;url-pattern&gt;/captcha.jpg&lt;/url-pattern&gt;
 *           &lt;/servlet-mapping&gt;
 * </pre>
 * 
 * @author Asuka Ito
 */
public class ImageCaptchaServlet extends HttpServlet {
	/**
	 * Serial version UID
	 */
	private static final long serialVersionUID = 9170756984250149481L;

	private static final String ID_PARAM_NAME = "id";

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest,
	 *      javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ImageCaptchaService service = CaptchaServiceSingleton
				.getImageCaptchaService();
		String captchaId = getCaptchaId(request);
		BufferedImage challenge;

		try {
			challenge = service.getImageChallengeForID(captchaId, request
					.getLocale());
		} catch (CaptchaServiceException e) {
			throw new ServletException(e);
		}

		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		JPEGImageEncoder jpegEncoder = JPEGCodec
				.createJPEGEncoder(jpegOutputStream);
		jpegEncoder.encode(challenge);

		byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	/**
	 * CAPTCHA 生成用の ID を取得します。
	 * 
	 * @param request
	 *            HTTP サーブレットリクエスト
	 * @return CAPTCHA 生成用の ID
	 */
	private String getCaptchaId(HttpServletRequest request) {
		String id = request.getParameter(ID_PARAM_NAME);

		if (id != null) {
			id = id.trim();

			if (id.length() != 0) {
				try {
					id = new String(id.getBytes("ISO-8859-1"), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					throw new Error(e);
				}
			}
		}

		return CaptchaUtil.getCaptchaId(request, id);
	}
}
