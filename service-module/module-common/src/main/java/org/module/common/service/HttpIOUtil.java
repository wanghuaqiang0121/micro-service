package org.module.common.service;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class HttpIOUtil {
	private  Logger log = LoggerFactory.getLogger(HttpIOUtil.class);
	private final  String BOUNDARY = UUID.randomUUID().toString()
			.toLowerCase().replaceAll("-", "");// 边界标识
	private final  String PREFIX = "--";// 必须存在
	private final  String LINE_END = "\r\n";
 
	/**
	 *  POST Multipart Request
	 *  @Description: 
	 *  @param requestUrl 请求url
	 *  @param requestText 请求参数
	 *  @param requestFile 请求上传的文件
	 *  @param plainCredentials 证件 （对应postman 的 Authorization）
	 *  @return
	 *  @throws Exception
	 */
	public  String sendPostRequest(String requestUrl,
			Map<String, String> requestText ) throws Exception{
		HttpURLConnection conn = null;
		InputStream input = null;
		OutputStream os = null;
		BufferedReader br = null;
		StringBuffer buffer = null;
		try {
			URL url = new URL(requestUrl);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setConnectTimeout(1000 * 10);
			conn.setReadTimeout(1000 * 10);
			conn.setRequestMethod("POST");
			//String plainCredentials = "cy:cy";//对应post 里面的 Authorization 的  Username:Password
			//String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
			//conn.setRequestProperty("Authorization", "Basic " + base64Credentials);// 对应postman中的Authorization
			conn.setRequestProperty("Accept", "*/*");
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			//conn.setRequestProperty("Content-Type", "application/octet-stream; boundary=" + BOUNDARY);
			conn.connect();
			
			// 往服务器端写内容 也就是发起http请求需要带的参数
			os = new DataOutputStream(conn.getOutputStream());
			// 请求参数部分
			writeParams(requestText, os);
			// 请求上传文件部分
			//writeFile(requestFile, os);
			// 请求结束标志
			String endTarget = PREFIX + BOUNDARY + PREFIX + LINE_END;
			os.write(endTarget.getBytes());
			os.flush();
			
			// 读取服务器端返回的内容
			//System.out.println("======================响应体=========================");
			//System.out.println("ResponseCode:" + conn.getResponseCode() + ",ResponseMessage:" + conn.getResponseMessage());
			if(conn.getResponseCode()==200){
				input = conn.getInputStream();
			}else{
				input = conn.getErrorStream();
			}
			br = new BufferedReader(new InputStreamReader( input, "UTF-8"));		
			buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}
			//......
			//System.out.println("返回报文:" + buffer.toString());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new Exception(e);
		} finally {
			try {
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}
				if (os != null) {
					os.close();
					os = null;
				}
				if (br != null) {
					br.close();
					br = null;
				}
			} catch (IOException ex) {
				log.error(ex.getMessage(), ex);
				throw new Exception(ex);
			}
		}
		return buffer.toString();
	}
	
	
    /** 
     * @author: WangHuaQiang
     * @date: 2019年3月8日
     * @param url
     * @param plainCredentials 证件 （对应postman 的 Authorization）
     * @return
     * @description: get请求
     */
    public String sendGetRequest(String url,String plainCredentials){
        HttpURLConnection conn = null;
        BufferedReader rd = null ;
        StringBuilder sb = new StringBuilder ();
        String line = null ;
        String response = null;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setReadTimeout(20000);
            conn.setConnectTimeout(20000);
            conn.setUseCaches(false);
            //String plainCredentials = "cy:cy";//用户名：密码
	        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
	        conn.setRequestProperty("Authorization", "Basic " + base64Credentials);// 对应postman中的Authorization
            conn.connect();
            rd  = new BufferedReader( new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while ((line = rd.readLine()) != null ) {
                sb.append(line);
            }
            response = sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{      
            try {
                if(rd != null){
                    rd.close();
                }
                if(conn != null){
                    conn.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response;
    }
    
	/**
	 * 对post参数进行编码处理并写入数据流中
	 * @throws Exception 
	 * 
	 * @throws IOException
	 * 
	 * */
	private void writeParams(Map<String, String> requestText,
			OutputStream os) throws Exception {
		try{
			@SuppressWarnings("unused")
			String msg = "请求参数部分:\n";
			if (requestText == null || requestText.isEmpty()) {
				msg += "空";
			} else {
				StringBuilder requestParams = new StringBuilder();
				Set<Map.Entry<String, String>> set = requestText.entrySet();
				Iterator<Entry<String, String>> it = set.iterator();
				while (it.hasNext()) {
					Entry<String, String> entry = it.next();
					requestParams.append(PREFIX).append(BOUNDARY).append(LINE_END);
					requestParams.append("Content-Disposition: form-data; name=\"")
							.append(entry.getKey()).append("\"").append(LINE_END);
					requestParams.append("Content-Type: text/plain; charset=utf-8")
							.append(LINE_END);
					requestParams.append("Content-Transfer-Encoding: 8bit").append(
							LINE_END);
					requestParams.append(LINE_END);// 参数头设置完以后需要两个换行，然后才是参数内容
					requestParams.append(entry.getValue());
					requestParams.append(LINE_END);
				}
				os.write(requestParams.toString().getBytes());
				os.flush();
				
				msg += requestParams.toString();
			}
			//System.out.println(msg);
		}catch(Exception e){
			log.error("writeParams failed", e);
			throw new Exception(e);
		}
	}
 

	 
	/**
	 * ContentType(这部分可忽略，MultipartFile有对应的方法获取)
	 * 
	 * @Description:
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public  String getContentType(MultipartFile file) throws Exception{
		String streamContentType = "application/octet-stream";
		String imageContentType = "";
		ImageInputStream image = null;
		try {
			image = ImageIO.createImageInputStream(file);
			if (image == null) {
				return streamContentType;
			}
			Iterator<ImageReader> it = ImageIO.getImageReaders(image);
			if (it.hasNext()) {
				imageContentType = "image/" + it.next().getFormatName();
				return imageContentType;
			}
		} catch (IOException e) {
			log.error("method getContentType failed", e);
			throw new Exception(e);
		} finally {
			try{
				if (image != null) {
					image.close();
				}
			}catch(IOException e){
				log.error("ImageInputStream close failed", e);;
				throw new Exception(e);
			}
			
		}
		return streamContentType;
	}
 
}
