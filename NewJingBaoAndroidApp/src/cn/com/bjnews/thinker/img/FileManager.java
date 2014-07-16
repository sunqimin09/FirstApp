package cn.com.bjnews.thinker.img;


public class FileManager {

	public static String getSaveFilePath() {
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "bjnews/imagecache/";
		} else {
			return CommonUtil.getRootFilePath() + "imagecache/";
		}
	}
	
	public static String getVideoPath(){
		if (CommonUtil.hasSDCard()) {
			return CommonUtil.getRootFilePath() + "bjnews/video/";
		} else {
			return CommonUtil.getRootFilePath() + "/video/";
		}
	}
	
}
