package com.cyn.service;

import com.cyn.utils.SampleListImageCaptchaEngine;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.service.captchastore.CaptchaStore;
import com.octo.captcha.service.captchastore.FastHashMapCaptchaStore;
import com.octo.captcha.service.image.AbstractManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

public class SampleImageCaptchaService extends
		AbstractManageableImageCaptchaService implements ImageCaptchaService {

	private static SampleImageCaptchaService instance;

	public static SampleImageCaptchaService getInstance() {
		if (instance == null) {

			ListImageCaptchaEngine engine = new SampleListImageCaptchaEngine();
			instance = new SampleImageCaptchaService(
					new FastHashMapCaptchaStore(), engine, 180, 100000, 75000);
		} 
		return instance;
	}
     

	public SampleImageCaptchaService(CaptchaStore captchaStore,
			CaptchaEngine captchaEngine, int minGuarantedStorageDelayInSeconds,
			int maxCaptchaStoreSize, int captchaStoreLoadBeforeGarbageCollection) {
		super(captchaStore, captchaEngine, minGuarantedStorageDelayInSeconds,
				maxCaptchaStoreSize, captchaStoreLoadBeforeGarbageCollection);
	}
}