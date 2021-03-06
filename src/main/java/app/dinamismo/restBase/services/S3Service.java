/**
 * @author Jeferson Luis dos Passos Silva
 *
 * @since May 19, 2021 2:04:00 AM
 */
package app.dinamismo.restBase.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import app.dinamismo.restBase.services.exceptions.FileException;

@Service
public class S3Service {


	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client; 


	@Value("${s3.bucket}")
	private String bucketName;


	public URI uploadFile(MultipartFile multipartFile) {	
		String fileName = multipartFile.getOriginalFilename();
		InputStream inputStream = null;
		try {
			inputStream = multipartFile.getInputStream();		
			String contentType = multipartFile.getContentType();
			return uploadFile(inputStream, fileName, contentType);
		}catch (IOException e) {
			throw new FileException("Erro de IO"+e.getStackTrace());
		}
		  
	}	

	public URI uploadFile(InputStream inputStream, String fileName, String contentType) {
		try {
			ObjectMetadata meta = new ObjectMetadata();
			meta.setContentDisposition(contentType);				
			LOG.info("Iniciando o downlload ");
			s3client.putObject( new PutObjectRequest(bucketName, fileName, inputStream, meta));
			LOG.info("Upload finalizado");
			return s3client.getUrl(bucketName, fileName).toURI();
		} catch (URISyntaxException e) {
			throw new FileException("Erro ao converter 	URL para URI");
		}
	}	
}
