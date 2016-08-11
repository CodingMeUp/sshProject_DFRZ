package com.cyn.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.cyn.service.SampleImageCaptchaService;
import com.octo.captcha.service.CaptchaServiceException;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
   
@SuppressWarnings("serial") 
public class JcaptchaServlet extends HttpServlet { 
    public static final String CAPTCHA_IMAGE_FORMAT = "jpeg"; 
    //private static final Logger log = Logger.getLogger(JcaptchaServlet.class); 
   
    @Override 
    public void init() throws ServletException { 
    } 
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	} 
    /**
     * @step1 本方法使用Jcaptcha工具生成img图片，并输出到客户端
     * @step2 将来在用户提交的action中插入下面语句进行验证码的校验：Boolean isResponseCorrect =
     *        captchaService.validateResponseForID( captchaId, "");
     **/ 
    
    @Override 
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException { 
        byte[] captchaChallengeAsJpeg = null; 
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream(); 
        try { 
            String captchaId = request.getSession().getId();
            BufferedImage challenge = SampleImageCaptchaService.getInstance().getImageChallengeForID(captchaId, request.getLocale()); 
            // 输出JPEG格式 
            JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream); 
            jpegEncoder.encode(challenge); 
        } catch (IllegalArgumentException e) { 
          //.error(e); 
            response.sendError(HttpServletResponse.SC_NOT_FOUND); 
            return; 
        } catch (CaptchaServiceException e) { 
           // log.error(e); 
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); 
            return; 
        } 
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray(); 
        response.setHeader("Cache-Control", "no-store"); 
        response.setHeader("Pragma", "no-cache"); 
        response.setDateHeader("Expires", 0); 
        // 输出JPEG图片 
        response.setContentType("image/jpeg"); 
        ServletOutputStream responseOutputStream = response.getOutputStream(); 
        responseOutputStream.write(captchaChallengeAsJpeg); 
        responseOutputStream.flush(); 
        responseOutputStream.close(); 
    }

}