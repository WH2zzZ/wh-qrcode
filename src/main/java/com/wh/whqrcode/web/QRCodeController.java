package com.wh.whqrcode.web;

import com.google.zxing.Result;
import com.wh.whqrcode.utils.QRCodeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/QRCode")
public class QRCodeController {

    /**
     * "生成二维码(测试)"
     */
    @GetMapping("/productCode")
    public void productCode() {

        QRCodeUtil.zxingCodeCreate("http://www.runoob.com/html/html-images.html", "L:\\cc.jpg",500,"C:\\Users\\LMS\\Desktop\\img\\0101.jpg");
    }

    /**
     * 解析二维码
     */
    @PostMapping("/analysisCode")
    public void analysisCode() {

        Result result = QRCodeUtil.zxingCodeAnalyze("C:\\Users\\DELL\\Desktop\\onlineCode.jpg");
        System.err.println("二维码解析内容："+result.toString());
    }

    /**
     * 生成二维码
     * @param content
     * @param size
     * @param resp
     * @throws IOException
     */
    @GetMapping("/onlineCode")
    public void generateCode(@PathParam("content")String content,
                             @PathParam("size") Integer size,
                             HttpServletResponse resp) throws IOException {

        resp.setCharacterEncoding("utf-8");
        //生成二维码图片  =========== 文字内容 ， 二维码长度，logo的存放位置
        BufferedImage bufImage = QRCodeUtil.getBufferedImage(content, size,null);

        //直接打开图片
        resp.setContentType("image/jpg");
        //直接下载图片===下载后图片存放的位置：Windows系统有一个默认的位置，只需要一个文件名即可
        //resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("qq.png", "UTF-8"));
        //将图片写出到浏览器
        OutputStream out = resp.getOutputStream();
        ImageIO.write(bufImage, "jpg", out);
        out.close();
    }
}
