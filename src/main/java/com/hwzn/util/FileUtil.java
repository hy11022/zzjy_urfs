package com.hwzn.util;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.Objects;
import cn.hutool.core.util.StrUtil;
import com.hwzn.pojo.entity.CourseExperimentEntity;
import com.hwzn.pojo.entity.CourseExperimentResultEntity;
import com.hwzn.pojo.entity.UserEntity;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.GrayColor;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;

/**
 * @Author： Du Rongjun
 * @Date： 2023/8/25 16:11
 * @Desc： 文件工具类
 */
@Component
public class FileUtil {

	private static Environment env;

	@Resource
	public void set(Environment env) {
		FileUtil.env = env;
	}
	/**
	 * 存储文件到本地
	 * @param file 文件流
	 * @return 文件地址
	 */
	public static String saveFile(MultipartFile file) throws IOException {
		String type=getFileTypeByUrl(Objects.requireNonNull(file.getOriginalFilename()));
		String filePath = setFilePath(type,null) + CommonUtil.randomUUID() + "." + type;
		file.transferTo(new File(filePath));
		return filePath;
	}

	//存储文件（Base64）
	public static String saveFileByBase64(String base64, String type) {
		String[] splitstr = base64.split(",");
		String s;
		if (base64.contains("base64,")) {
			s = splitstr[1];
		} else {
			s = splitstr[0];
		}
		//定义文件夹路径
		StringBuilder dirPath = new StringBuilder();
		dirPath.append(System.getProperty("catalina.home"), 0, System.getProperty("catalina.home").indexOf("\\"))
				.append("/source/zzjy_urfs/files/").append(type);
		//创建文件夹
		File dir = new File(String.valueOf(dirPath));
		//判断是否存在，不存在创建
		if (!dir.exists()) {
			dir.mkdirs();
		}
		//定义文件路径
		StringBuilder filePath = new StringBuilder();
		filePath.append(dirPath).append("/").append(CommonUtil.randomUUID()).append(".").append(type);
		//将String 转成byte数组
		byte[] file = new Base64().decode(s);
		//写入本地
		File files = new File(filePath.toString());
		try (FileOutputStream writefile = new FileOutputStream(files)) {
			writefile.write(file, 0, file.length);
			writefile.flush();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return filePath.toString();
	}

	/**
	 * 获取文件类型(网络路径）
	 * @param url 网络路径
	 * @return 文件类型
	 */
	public static String getFileTypeByUrl(String url){
		int index = url.lastIndexOf(".");
		return index<0?null:url.substring(index+1);
	}

	/**
	 * 设置文件路径
	 * @param type 文件类型
	 * @param parPath 父路径
	 * @return 文件路径
	 */
	public static String setFilePath(String type,String parPath){
		//定义文件夹路径
		StringBuilder dirPath = new StringBuilder();
		if(type!=null){
			dirPath.append(System.getProperty("catalina.home"), 0,
							Objects.equals(CommonUtil.getOperationSystemType(), "linux")?System.getProperty("catalina.home").lastIndexOf("/"):System.getProperty("catalina.home").indexOf("\\"))
					.append("/source/zzjy_urfs").append("/files/").append(type).append("/");
		}else {
			dirPath.append(System.getProperty("catalina.home"), 0,
							Objects.equals(CommonUtil.getOperationSystemType(), "linux")?System.getProperty("catalina.home").lastIndexOf("/"):System.getProperty("catalina.home").indexOf("\\"))
					.append("/source/zzjy_urfs/").append(parPath);
		}
		//创建文件夹
		File dir = new File(String.valueOf(dirPath));
		//判断是否存在，不存在创建
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dirPath.toString();
	}

	/**
	 * 生成本地路径
	 * @param type 文件类型
	 * @return 本地路径
	 */
	public static String generatePath(String type){
		if (type == null || type.isEmpty()) {
			return null;
		}
		//定义文件夹路径
		StringBuilder dirPath = new StringBuilder();
		dirPath.append(System.getProperty("catalina.home"), 0,
						Objects.equals(CommonUtil.getOperationSystemType(), "linux") ? System.getProperty("catalina.home").lastIndexOf("/") : System.getProperty("catalina.home").indexOf("\\"))
				.append("/source/").append(env.getProperty("server.Project")).append("/files/").append(type).append("/");
		//创建文件夹
		File dir = new File(String.valueOf(dirPath));
		//判断是否存在，不存在创建
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dirPath + CommonUtil.randomUUID() + "." + type;
	}

	/**
	 * 本地路径转化为网络路径
	 * @param localPath 本地路径
	 * @return 网络路径
	 */
	public static String localPathToUrl(String localPath){
		String path=localPath.replace("\\","/");
		int index =path.indexOf("source")+6;
		return index<0?null:env.getProperty("server.Server")+path.substring(index);
	}

	/**
	 * 递归逐层删除文件夹信息
	 * @param directoryPath 文件夹路径
	 * @return 无
	 */
	public static void deleteDirectory(String directoryPath){
		File directory = new File(directoryPath);
		File[] list = directory.listFiles();
		if (list != null) {
			for (File temp : list) {
				deleteDirectory(temp.getAbsolutePath());
			}
		}
		directory.delete();
	}

	public static String generateReport(CourseExperimentEntity courseExperimentEntity, UserEntity userEntity, CourseExperimentResultEntity courseExperimentResultEntity) throws IOException, DocumentException {

		String titleContent = "虚拟仿真实训报告";
		String path = FileUtil.generatePath("pdf");
		File file = new File(path);
		Document document = new Document(PageSize.A4,50,50,30,20);
		PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(file.toPath()));

		document.open();
		document.addTitle(titleContent);// 标题
		document.addAuthor(userEntity.getName()+" "+userEntity.getAccount());// 作者

		Font waterFont = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 30, Font.BOLD, new GrayColor(0.95f));
		Font titleFont = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 24, Font.BOLD);
		Font labelFont = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 16, Font.NORMAL);
		Font valueFont = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 15, Font.NORMAL, new GrayColor(0.4f));
		Font textFont = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 13, Font.NORMAL, new GrayColor(0.4f));
		PdfUtil.setWaterMark(writer,"虚拟仿真实训平台",waterFont);
		//标题
		Paragraph title = new Paragraph(titleContent, titleFont);
		title.setAlignment(1); //设置文字居中 0靠左   1，居中     2，靠右
		title.setLeading(20f); //行间距
		title.setSpacingBefore(5f); //设置段落上空白

		// 表格
		PdfPTable table = PdfUtil.createTable(6,1);
		table.setSpacingBefore(20f);
		table.addCell(PdfUtil.createCell("实训名称", labelFont, 0,1));
		table.addCell(PdfUtil.createCell(courseExperimentEntity.getName(), valueFont, 0,5));
		table.addCell(PdfUtil.createCell("班级名称", labelFont, 0,1));
		table.addCell(PdfUtil.createCell(userEntity.getClassName(), valueFont, 0,2));
		table.addCell(PdfUtil.createCell("实训人员", labelFont, 0,1));
		table.addCell(PdfUtil.createCell(userEntity.getName()+userEntity.getAccount(), valueFont, 0,2));
		table.addCell(PdfUtil.createCell("实训时间", labelFont, 0,1));
		table.addCell(PdfUtil.createCell(courseExperimentResultEntity.getEndTime(), valueFont, 0,2));
		table.addCell(PdfUtil.createCell("实训时长", labelFont, 0,1));
		table.addCell(PdfUtil.createCell(courseExperimentResultEntity.getDuration(), valueFont, 0,2));
		table.addCell(PdfUtil.createCell("来源", labelFont, 0,1));
		table.addCell(PdfUtil.createCell(courseExperimentResultEntity.getSource(), valueFont, 0,2));
		table.addCell(PdfUtil.createCell("得分", labelFont, 0,1));
		try{
			if(courseExperimentResultEntity.getTotalScore() != null){
				table.addCell(PdfUtil.createCell(String.valueOf(courseExperimentResultEntity.getTotalScore()), valueFont, 0,2));
			}else{
				table.addCell(PdfUtil.createCell("待批阅", valueFont, 0,2));
			}
		}catch (Exception e){
			table.addCell(PdfUtil.createCell("待批阅", valueFont, 0,2));
		}

		table.addCell(PdfUtil.createCell("一、实训介绍", labelFont, 0,6));
		table.addCell(PdfUtil.createCell(StrUtil.isNotBlank(courseExperimentEntity.getDes())?courseExperimentEntity.getDes():"暂无实训介绍", textFont, 0,6));
		table.addCell(PdfUtil.createCell("二、实训过程", labelFont, 0,6));
		table.addCell(PdfUtil.createCell(courseExperimentResultEntity.getSteps()!=null && !courseExperimentResultEntity.getSteps().isEmpty()?courseExperimentResultEntity.getSteps():"无", textFont, 0,6));
		table.addCell(PdfUtil.createCell("三、分析评估", labelFont, 0,6));
		table.addCell(PdfUtil.createCell(courseExperimentResultEntity.getAnalysis(), valueFont, 0,6));
		table.addCell(PdfUtil.createCell("四、实训结果", labelFont, 0,6));
		table.addCell(PdfUtil.createCell(courseExperimentResultEntity.getResult(), textFont, 0,6));
		document.add(title);
		document.add(table);
		document.close();
		return FileUtil.localPathToUrl(path);
	}

	public static String setAnalysis(Double score){
		String result = "";
		if(score<60){
			result="很遗憾，你的实验报告未达到及格标准。实验操作存在较多问题，这反映出你在实验准备、操作或数据处理方面存在较大的不足。建议你重新审视实验过程，加强对实验原理和方法的学习，提高实验操作的严谨性。希望你在接下来的学习中，能够认真改进，取得进步。";
		}
		if(score<70 && score>=60){
			result="你的实验报告达到了及格标准，但仍有较大的提升空间。报告内容较为简单，数据记录和分析不够全面，部分结论缺乏充分的证据支持。建议你在今后的学习中，加强对实验原理的学习和理解，提高实验操作的规范性，以及数据分析的能力。同时，注意报告书写的规范性，包括格式、语言表达等方面，这些都将有助于提升你的实验报告质量。";
		}
		if(score<80 && score>=70){
			result="你的实验报告基本符合要求，完成了实验的基本任务。报告结构较为清晰，但在数据记录和分析上存在一些小错误或遗漏，影响了结果的准确性。建议你在今后的实验中，更加注重实验细节，提高数据处理的准确性，并加强对实验原理的深入理解。同时，增强报告的逻辑性和条理性，会使你的报告更加出色。";
		}
		if(score<90 && score>=80){
			result="你的实验报告完成得很好，体现了良好的实验技能和理论素养。报告内容完整，数据记录和分析都比较到位，能够较好地反映实验过程和结果。建议在未来实验中，进一步加强数据处理的细致性和理论联系实际的深度，相信你会有更大的进步。";
		}
		if(score<=100 && score>=90){
			result="你的实验报告非常出色，展现了深厚的理论基础和卓越的实验操作能力。报告结构清晰，逻辑严密，数据记录准确无误，分析深入透彻。你对实验结果的讨论富有洞察力，能够很好地将理论知识与实验现象相结合。特别是你的创新思考，为实验带来了新的视角。继续保持这种高水平的学术态度，未来可期！";
		}
		return result;
	};

	public static String setResult(Double score){
		String result = "";
		if(score<60){
			result="不及格";
		}
		if(score<70 && score>=60){
			result="及格";
		}
		if(score<80 && score>=70){
			result="中等";
		}
		if(score<90 && score>=80){
			result="良好";
		}
		if(score<=100 && score>=90){
			result="优秀";
		}
		return result;
	};
}