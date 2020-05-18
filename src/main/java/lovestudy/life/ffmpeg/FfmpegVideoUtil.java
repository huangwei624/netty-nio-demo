package lovestudy.life.ffmpeg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FfmpegVideoUtil {
	
	public static void main(String[] ars ) throws IOException {

		final String BASE_PATH = "D:\\ffmpeg\\ffmpeg-20200420-cacdac8-win64-static\\bin\\";
		final String FFMPEG_PATH = BASE_PATH + "ffmpeg.exe";
		final String CONVERTFILE = BASE_PATH + "1.mp4";
		final String TARGETIMGPATH = BASE_PATH;
		
		// 截取图片
		videoCatchImg(FFMPEG_PATH, CONVERTFILE, TARGETIMGPATH, 30.5d);
		
		// 截取视频
		// cutVideo(FFMPEG_PATH, CONVERTFILE, TARGETIMGPATH, "00:01:00", "00:01:00");
		
		// 合并视频
//		ArrayList<String> fileNames = new ArrayList<>();
//		fileNames.add("D:\\ffmpeg\\ffmpeg-20200420-cacdac8-win64-static\\bin\\capture-000.mp4");
//		fileNames.add("D:\\ffmpeg\\ffmpeg-20200420-cacdac8-win64-static\\bin\\capture-001.mp4");
//		fileNames.add("D:\\ffmpeg\\ffmpeg-20200420-cacdac8-win64-static\\bin\\capture-002.mp4");
//		fileNames.add("D:\\ffmpeg\\ffmpeg-20200420-cacdac8-win64-static\\bin\\capture-003.mp4");
//
//		String fileName = BASE_PATH + System.currentTimeMillis() + ".txt" ;
//		File file = new File(fileName);
//		if(!file.exists()){
//			file.createNewFile();
//		}
//		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
//		fileNames.forEach(item -> {
//			try {
//				bufferedWriter.write("file '" + item + "'");
//				bufferedWriter.newLine();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
//		bufferedWriter.close();
//		List<String> commands = new java.util.ArrayList<>();
//		// ffmpeg -f concat -i filelist.txt -c copy output.mkv
//		commands.add(FFMPEG_PATH);
//		commands.add("-f");
//		commands.add("concat");
//		commands.add("-safe");
//		commands.add("0");
//		commands.add("-i");
//		commands.add("D:\\ffmpeg\\ffmpeg-20200420-cacdac8-win64-static\\bin\\1587715529886.txt");
//		commands.add("-c");
//		commands.add("copy");
//		commands.add("D:\\ffmpeg\\ffmpeg-20200420-cacdac8-win64-static\\bin\\output.mp4");
//		runCommand(commands);
	}
	
	/**
	 * 截取视频 第 positonSec 秒 的图片
	 * @param ffmpegPath
	 * @param videoPath
	 * @param targetImgPath
	 * @param positonSec
	 */
	public static void videoCatchImg(String ffmpegPath, String videoPath, String targetImgPath, double positonSec) {
		File file = new File(videoPath);
		if (!file.exists()) {
			System.err.println("路径[" + videoPath + "]对应的视频文件不存在!");
			return ;
		}
		List<String> commands = new java.util.ArrayList<>();
		commands.add(ffmpegPath);
		//输入文件
		commands.add("-i");
		commands.add(videoPath);
		//输出文件若存在可以覆盖
		commands.add("-y");
		//指定图片编码格式
		commands.add("-f");
		commands.add("image2");
		//设置截取视频第3秒时的画面
		commands.add("-ss");
		commands.add(String.valueOf(positonSec));
		//截取的图片路径
		commands.add(targetImgPath + "video_target_img"+positonSec+".jpg");
		System.out.println(commands);
		runCommand(commands);
	}
	
	/**
	 *
	 * @param ffmpegPath
	 * @param sourcePath
	 * @param targetPath
	 * @param beginTime
	 * @param longtime
	 */
	public static void cutVideo(String ffmpegPath, String sourcePath, String targetPath, String beginTime, String longtime){
		File file = new File(sourcePath);
		if (!file.exists()) {
			System.err.println("路径[" + sourcePath + "]对应的视频文件不存在!");
			return;
		}
		
		List<String> commands = new ArrayList<>();
		commands.add(ffmpegPath);
		commands.add("-i");
		commands.add(sourcePath);
		commands.add("-y");
		commands.add("-f");
		commands.add("mp4");
		commands.add("-vcodec");
		commands.add("copy");
		commands.add("-acodec");
		commands.add("copy");
		commands.add("-ss");
		commands.add(beginTime);
		commands.add("-t");
		commands.add(longtime);
		commands.add(targetPath+"target_video"+longtime.replace(":", "")+".mp4");
		runCommand(commands);
	}
	
	private static void runCommand(List<String> commands){
		System.out.println("commands:"+commands);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			builder.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
