package org.github.xubo245.video;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class AnalysisVideo {

  public void analysisVideo(String path, String picPath) {
    long startTime = System.nanoTime();
    long grabTime = 0;
    long convertTime = 0;
    long writeTime = 0;
    FFmpegFrameGrabber ff = new FFmpegFrameGrabber(path);
    try {
      ff.start();
      int length = ff.getLengthInFrames();
      ff.setFormat("h264");
      double fps = ff.getFrameRate();
      Frame frame = null;
      int i = 0;
      int j = 0;
      String fileName = path.substring(path.lastIndexOf("/"), path.lastIndexOf("."));
      while (i < length) {
        long startGrab = System.nanoTime();
        if (i % fps < 1) {
          i++;
          frame = ff.grabImage();
        } else {
          ff.grabFrame(false, true, false, false);
          i++;
          continue;
        }
        grabTime += System.nanoTime() - startGrab;
        if (frame != null && (frame.image != null)) {
          long startConvert = System.nanoTime();
          String outputPath = picPath + fileName + "_" + j + ".jpg";
          j++;
          Java2DFrameConverter converter = new Java2DFrameConverter();
          BufferedImage srcImage = converter.getBufferedImage(frame);
          int srcImageWidth = srcImage.getWidth();
          int srcImageHeight = srcImage.getHeight();
          int width = srcImageWidth;
          int height = (int) ((double) width / srcImageWidth) * srcImageHeight;
          BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
          bufferedImage.getGraphics().drawImage(srcImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
          File picFile = new File(outputPath);
          convertTime += System.nanoTime() - startConvert;
          long startWrite = System.nanoTime();
          ImageIO.write(bufferedImage, "jpg", picFile);
          writeTime += System.nanoTime() - startWrite;
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
    long endTime = System.nanoTime();
//    System.out.println(String.format("The time "));
  }

  public static void main(String[] args) {
//    String path = "/Users/xubo/Desktop/xubo/personal/video/0-032526.mp4";
    String path = "/Users/xubo/Desktop/xubo/personal/video/";
//    String picPath = "/Users/xubo/Desktop/xubo/git/MP4ParserMergeAudioVideo/VideoTools/src/test/resources/data/";
    String picPath = "/Volumes/My Passport/xubo/video/mp4/videoimg/";
    File dir = new File(path);
    File[] files = dir.listFiles();
    for (File file : files) {
      AnalysisVideo analysisVideo = new AnalysisVideo();
      analysisVideo.analysisVideo(file.getPath(), picPath);
    }

  }
}
