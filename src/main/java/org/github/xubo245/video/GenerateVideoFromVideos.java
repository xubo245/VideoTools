package org.github.xubo245.video;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GenerateVideoFromVideos {

  public void generateVideo(String path, String saveMp4name) {

    FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(saveMp4name, 1920, 1080);
    //		recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // 28
    recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4); // 28
    //		recorder.setVideoCodec(avcodec.AV_CODEC_ID_MPEG4); // 13
    recorder.setFormat("mp4");
    //	recorder.setFormat("mov,mp4,m4a,3gp,3g2,mj2,h264,ogg,MPEG4");
    recorder.setFrameRate(60);
    recorder.setPixelFormat(0); // yuv420p

    try {

      recorder.start();

      // 列出目录中所有的图片，都是jpg的，以1.jpg,2.jpg的方式，方便操作
      File file = new File(path);
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

      for (int index = 0; index < fileList.size(); index++) {
//        if("/Volumes/xubo/xubo/video/mp4/2020-03-07/0-055246.v264".equalsIgnoreCase(((File)fileList.get(index)).getPath())){
//          continue;
//        }

        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(((File)fileList.get(index)).getPath());

        if (index % 100 == 0) {
          System.out.println(String.format("start to handle %s image of %s", index + 1, fileList.size()));
        }

        ff.start();
        ff.setFormat("h264");

        int length = ff.getLengthInFrames();

        double fps = ff.getFrameRate();
        Frame frame = null;
        int i = 0;
        int j = 0;
        boolean flag = true;
        while (flag) {
          if (i % fps < 1) {
            i++;
            frame = ff.grabImage();
          } else {
            ff.grabFrame(false, true, false, false);
            i++;
            continue;
          }

          if (frame != null && (frame.image != null)) {
            recorder.record(frame);
          } else {
            flag = false;
          }
          if (i >= length - 1) {
            flag = false;
          }
        }
        ff.stop();
        ff.release();
      }
      recorder.stop();
      recorder.release();


    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
