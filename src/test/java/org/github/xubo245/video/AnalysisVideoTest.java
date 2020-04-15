package org.github.xubo245.video;

import java.io.File;

public class AnalysisVideoTest {

  public static void main(String[] args) {
//    String path = "/Users/xubo/Desktop/xubo/personal/video/0-032526.mp4";
//    String path = "/Users/xubo/Desktop/xubo/personal/video/";
    String path = "/Users/xubo/Desktop/xubo/git/MP4ParserMergeAudioVideo/VideoTools/src/test/resources/flower/0-030151.mp4";
    String picPath = "/Users/xubo/Desktop/xubo/git/MP4ParserMergeAudioVideo/VideoTools/src/test/resources/data3/";
//    String picPath = "/Volumes/My Passport/xubo/video/mp4/videoimg/";
    File dir = new File(path);
    if (dir.isDirectory()) {
      File[] files = dir.listFiles();
      for (int i = 0; i < files.length; i++) {
        File file = files[i];
        System.out.println(String.format("Start to handle %s video of %s", i + 1, files.length));
        AnalysisVideo analysisVideo = new AnalysisVideo();
        analysisVideo.analysisVideo(file.getPath(), picPath);
      }
    } else {
      AnalysisVideo analysisVideo = new AnalysisVideo();
      analysisVideo.analysisVideo(dir.getPath(), picPath);
    }
  }
}
