package com.cyn.utils;

import java.awt.Color;

import com.octo.captcha.component.image.backgroundgenerator.FunkyBackgroundGenerator;
import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import com.octo.captcha.component.image.fontgenerator.TwistedRandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.LineTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.ComposedWordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.engine.image.ListImageCaptchaEngine;
import com.octo.captcha.image.ImageCaptchaFactory;
import com.octo.captcha.image.gimpy.GimpyFactory;

public class SampleListImageCaptchaEngine  extends ListImageCaptchaEngine{
     
	
	@Override
	 protected void buildInitialFactories() {
	//第一个参数设置最少字符，第二个最多字符，第三个字体颜色
	 TextPaster randomPaster = new DecoratedRandomTextPaster(4,4, new SingleColorGenerator(Color.BLACK), 
	 new TextDecorator[] { new BaffleTextDecorator(0, Color.WHITE) ,new LineTextDecorator(0, Color.black) });  
	 
	      //  TextDecorator 2种款式：1)干扰点1个 new BaffleTextDecorator(0, Color.WHITE) 
         //                                      2)横线          new LineTextDecorator(1, Color.black)
	 //验证码建立背景 
	 ColorGenerator cg = new ColorGenerator() {
		public Color getNextColor() {
			return Color.WHITE;
		}};
	 FunkyBackgroundGenerator fbg = new FunkyBackgroundGenerator(160, 50, cg);
	 ComposedWordToImage composedWordToImage = new ComposedWordToImage(
			                 new TwistedRandomFontGenerator(23,23),fbg, randomPaster);
	 
	 ImageCaptchaFactory factory = new GimpyFactory( new RandomWordGenerator("abcdefghijkmnopqrstuvwxyz"), composedWordToImage);
	 ImageCaptchaFactory characterFactory[] = { factory}; 
	 this.addFactories(characterFactory);
	 } 
}
