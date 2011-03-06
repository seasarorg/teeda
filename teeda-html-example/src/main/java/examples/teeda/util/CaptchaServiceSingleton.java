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

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import com.octo.captcha.service.image.ImageCaptchaService;

/**
 * シングルトンの CAPTCHA サービスオブジェクトを保持するクラスです。
 * 
 * @author Asuka Ito
 */
public class CaptchaServiceSingleton {
	private static ImageCaptchaService imageCaptchaService = null;

	private static S2Container container = SingletonS2ContainerFactory
			.getContainer();

	/**
	 * ユーティリティクラスであるため何もしません
	 */
	private CaptchaServiceSingleton() {
		// 何もしない
	}

	/**
	 * シングルトンのイメージ CAPTCHA サービスオブジェクトを取得します。
	 * 
	 * @return シングルトンのイメージ CAPTCHA サービスオブジェクト
	 */
	public static ImageCaptchaService getImageCaptchaService() {
		if (imageCaptchaService == null) {
			imageCaptchaService = (ImageCaptchaService) container
					.getComponent(ImageCaptchaService.class);
		}

		return imageCaptchaService;
	}
}
