package org.github.xubo245.video;

public class GenerateVideoFromVideosTest {
  public static void main(String[] args) {
    System.out.println("start...");
    String saveMp4name = "/Users/xubo/Desktop/xubo/git/VideoTools/src/test/resources/video/test3.mp4"; //保存的视频名称
    String videoPath = "/Volumes/xubo/xubo/video/mp4/2020-03-07/";

    GenerateVideoFromVideos generateVideoFromVideos = new GenerateVideoFromVideos();
    generateVideoFromVideos.generateVideo(videoPath, saveMp4name);
    System.out.println("end...");
  }
}
