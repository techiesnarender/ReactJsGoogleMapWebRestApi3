package com.misha.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import com.misha.model.User;
import com.misha.payload.response.UploadFileResponse;
import com.misha.repository.UserRepository;
import com.misha.services.FileStorageService;

@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false" )
@RestController
public class FileController {
	
	/**
	 * Auther: Narender Singh Resource file https://www.callicoder.com/spring-boot-file-upload-download-rest-api-example/
	 */
	
	 private static final Logger logger = LoggerFactory.getLogger(FileController.class);
	 
	 @Autowired
	 private FileStorageService fileStorageService;
	 
	 @Autowired
	 private UserRepository userRepository;
	 
		@PostMapping("/api/users/uploadFile")
	    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("email") String email, HttpServletRequest request) {		  
 	        String fileName = fileStorageService.storeFile(file);

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentServletMapping()
//	                .path("/downloadFile/")
//	                .path(fileName)
//	                .toUriString();  
        
        String url = request.getScheme() + "://" + request.getServerName();
        String fileDownloadUri = url +"/downloadFile/" + fileName;       
        System.out.println(fileDownloadUri);
       
	    	User user =  userRepository.getUserByEmail(email);
	    	
	    	Optional<User> existUser = userRepository.findByEmail(email);
	    	if(existUser.isPresent()) {
	    		if(user != null) {
					user.setLogo(fileDownloadUri);				
					userRepository.save(user);
			 	}
		        return new UploadFileResponse(fileName, fileDownloadUri);
	    	}else {
	    		throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Email Address Not Found!");
	    	}
	    	
		 
	    }
	 
		
	 	@GetMapping("/downloadFile/{fileName:.+}")
	    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
	        // Load file as Resource
	        Resource resource = fileStorageService.loadFileAsResource(fileName);

	        // Try to determine file's content type
	        String contentType = null;
	        try {
	            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
	        } catch (IOException ex) {
	            logger.info("Could not determine file type.");
	        }

	        // Fallback to the default content type if type could not be determined
	        if(contentType == null) {
	            contentType = "application/octet-stream";
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(contentType))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
	                .body(resource);
	    }
	}

