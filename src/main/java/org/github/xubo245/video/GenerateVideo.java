package org.github.xubo245.video;


import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class GenerateVideo {
  //  refer https://blog.csdn.net/leeking888/article/details/73928714?utm_source=blogkpcl8
  public static void main(String[] args) throws Exception {
    System.out.println("start...");
    String date = "2020-03-25";
    String saveMp4name = "/Volumes/xubo/xubo/video/honorcamera/video/"+date+"_2.mp4"; //保存的视频名称
    // 目录中所有的图片，都是jpg的，以1.jpg,2.jpg的方式，方便操作
    //    String imagesPath = "/Users/xubo/Desktop/xubo/git/MP4ParserMergeAudioVideo/VideoTools/src/test/resources/data2/"; // 图片集合的目录
    String imagesPath = "/Volumes/xubo/xubo/video/honorcamera/image/"+date;
    ; // 图片集合的目录
    test(saveMp4name, imagesPath,date);
    System.out.println("end...");
  }

  public static void test(String saveMp4name, String imagesPath,String date) throws Exception {
    FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(saveMp4name, 1920, 1080);
    recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
//    recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4); // 28
    //		recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4); // 13
    recorder.setFormat("mp4");
    //	recorder.setFormat("mov,mp4,m4a,3gp,3g2,mj2,h264,ogg,MPEG4");
    recorder.setFrameRate(200);
    recorder.setVideoBitrate(100000000);
//    recorder.setPixelFormat(2); // yuv420p
    recorder.start();


    Java2DFrameConverter converter = new Java2DFrameConverter();

    // 列出目录中所有的图片，都是jpg的，以1.jpg,2.jpg的方式，方便操作
    File file = new File(imagesPath);
    File[] flist = file.listFiles();
    // 循环所有图片
    List fileList = Arrays.asList(flist);
    Collections.sort(fileList, new Comparator<File>() {
      @Override
      public int compare(File o1, File o2) {
        if (o1.isDirectory() && o2.isFile())
          return -1;
        if (o1.isFile() && o2.isDirectory())
          return 1;
        return o1.getName().compareTo(o2.getName());
      }
    });

    for (int i = 0; i < fileList.size(); i++) {
      int num = 50;
      String fileName = ((File) fileList.get(i)).getPath();
      String start = "/Volumes/xubo/xubo/video/honorcamera/image/" + date + "/0-030000_1000000.jpg";
      String end = "/Volumes/xubo/xubo/video/honorcamera/image/" + date + "/0-080000_1000000.jpg";
      if (fileName.compareTo(start) < 0 || fileName.compareTo(end) > 0 ) {
        continue;
      }

      if (fileName.compareTo("/Volumes/xubo/xubo/video/honorcamera/image/" + date + "/0-2100000_1000000.jpg") > 0) {
        num = 100;
      } else if (fileName.compareTo("/Volumes/xubo/xubo/video/honorcamera/image/" + date + "/0-180000_1000000.jpg") > 0) {
        num = 5;
      } else if (fileName.compareTo("/Volumes/xubo/xubo/video/honorcamera/image/" + date + "/0-110000_1000000.jpg") > 0) {
        num = 100;
      } else if (fileName.compareTo("/Volumes/xubo/xubo/video/honorcamera/image/" + date + "/0-080000_1000000.jpg") > 0) {
        num = 5;
      } else if (fileName.compareTo(start) > 0) {
        num = 5;
      }
      if (i % num != 0) {
        continue;
      }
      System.out.println(String.format("\t%s:%s", i + 1, ((File) fileList.get(i)).getPath()));
      if (i % 100 == 0) {
        System.out.println(String.format("start to handle %s image of %s", i + 1, fileList.size()));
      }
      BufferedImage image = ImageIO.read(new FileInputStream((File) fileList.get(i)));
      Frame frame = converter.getFrame(image);
      recorder.record(frame);
    }

    recorder.stop();
    recorder.release();
  }

}
