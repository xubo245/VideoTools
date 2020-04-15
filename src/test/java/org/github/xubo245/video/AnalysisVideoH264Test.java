package org.github.xubo245.video;

import java.io.File;

public class AnalysisVideoH264Test {

    public static void main(String[] args) {
//    String path = "/Users/xubo/Desktop/xubo/personal/video/0-032526.mp4";
//    String path = "/Users/xubo/Desktop/xubo/git/MP4ParserMergeAudioVideo/VideoTools/src/test/resources/h264/0-030151.v264";
//    String path = "/Users/xubo/Desktop/xubo/personal/video/";
//    String path = "/Users/xubo/Desktop/xubo/personal/video/";
        for (int ii = 7; ii < 8; ii++) {


            String date = "2020-03-31";
//    String date = "2020-04-0"+ii;
            String path = "/Volumes/xubo/xubo/video/honorcamera/originaldata/" + date + "/";
//    String picPath = "/Users/xubo/Desktop/xubo/git/MP4ParserMergeAudioVideo/VideoTools/src/test/resources/data21/";
            String picPath = "/Volumes/xubo/xubo/video/honorcamera/image/" + date + "/";
            File picDir = new File(picPath);
            if (!picDir.exists()) {
                picDir.mkdir();
            }
            File dir = new File(path);
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    System.out.println(String.format("Start to handle %s video of %s", i + 1, files.length));
                    AnalysisVideoH264 analysisVideo = new AnalysisVideoH264();
                    analysisVideo.analysisVideo(file.getPath(), picPath);
                }
            } else {
                AnalysisVideoH264 analysisVideo = new AnalysisVideoH264();
                analysisVideo.analysisVideo(dir.getPath(), picPath);
            }
        }
    }
}
