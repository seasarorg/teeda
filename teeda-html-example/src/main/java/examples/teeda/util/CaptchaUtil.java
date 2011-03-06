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
package examples.teeda.util;

import javax.servlet.http.HttpServletRequest;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * @author Asuka Ito
 * 
 */
public class CaptchaUtil {

	/**
	 * ユーティリティクラスのため、何もしません
	 */
	private CaptchaUtil() {
		// 何もしない
	}

	/**
	 * CAPTCHA 認証を行います。
	 * 
	 * @param request
	 *            HTTP サーブレットリクエスト
	 * @param value
	 *            ユーザからの入力
	 * @param id
	 *            複数の CAPTCHA を識別するための ID
	 * @return 認証が通れば <code>true</code>
	 */
	public static boolean validate(HttpServletRequest request, String value,
			String id) {
		String captchaId = getCaptchaId(request, id);

		boolean result;

		try {
			final ImageCaptchaService imageCaptchaService = CaptchaServiceSingleton
					.getImageCaptchaService();
			final Boolean b = imageCaptchaService.validateResponseForID(
					captchaId, value);
			result = b.booleanValue();
		} catch (CaptchaServiceException e) {
			result = false;
		}

		return result;
	}

	/**
	 * CAPTCHA 認証を行います。
	 * 
	 * @param request
	 *            HTTP サーブレットリクエスト
	 * @param value
	 *            ユーザからの入力
	 * @return 認証が通れば <code>true</code>
	 */
	public static boolean validate(HttpServletRequest request, String paramName) {
		return validate(request, paramName, null);
	}

	/**
	 * CAPTCHA ID を取得します。
	 * 
	 * @param request
	 *            HTTP サーブレットリクエスト
	 * @param id
	 *            複数の CAPTCHA を識別するための ID
	 * @return CAPTCHA ID
	 */
	public static String getCaptchaId(HttpServletRequest request, String id) {
		String sessionId = request.getSession().getId();

		if (id == null) {
			return sessionId;
		}

		id = id.trim();

		if (id.length() == 0) {
			return sessionId;
		}

		return sessionId + "_" + id;
	}
}
