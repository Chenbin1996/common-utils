package com.ruxuanwo.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.UUID;

/**
 * 二维码工具类
 *
 * @author 如漩涡
 */
public class QrcodeUtil {

    public static String createQrcode(String dir, String content) {
        return createQrcode(dir, null, content, 300, 300);
    }

    public static String createQrcode(String dir, String name, String content) {
        return createQrcode(dir, name, content, 300, 300);
    }

    public static String createQrcode(String dir, String name, String content, int width, int height) {
        try {
            String qrcodeFormat = "png";
            String qrcodeName;
            HashMap<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            if (name == null) {
                qrcodeName = UUID.randomUUID().toString();
            } else {
                qrcodeName = name;
            }
            File file = new File(dir + "/" + qrcodeName + "." + qrcodeFormat);
            MatrixToImageWriter.writeToPath(bitMatrix, qrcodeFormat, file.toPath());
            return file.getAbsolutePath();
        } catch (Exception e) {
            new RuntimeException("生成二维码失败", e);
        }
        return "";
    }

    public static String decodeQr(String filePath) {
        String retStr = "";
        if ("".equalsIgnoreCase(filePath) && filePath.length() == 0) {
            return "图片路径为空!";
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(new FileInputStream(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            Binarizer binarizer = new HybridBinarizer(source);
            BinaryBitmap bitmap = new BinaryBitmap(binarizer);
            HashMap<DecodeHintType, Object> hintTypeObjectHashMap = new HashMap<>();
            hintTypeObjectHashMap.put(DecodeHintType.CHARACTER_SET, "UTF-8");
            Result result = new MultiFormatReader().decode(bitmap, hintTypeObjectHashMap);
            retStr = result.getText();
        } catch (Exception e) {
            new RuntimeException("解析二维码失败", e);
        }
        return retStr;
    }

    public static void main(String[] args) {
        String qrUrl = createQrcode("D:", "测试");
        System.out.println(decodeQr(qrUrl));
    }
}
