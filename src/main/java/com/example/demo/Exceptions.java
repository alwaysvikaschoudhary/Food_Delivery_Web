package com.example.demo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class Exceptions{

	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	
	@ExceptionHandler(value = org.springframework.web.multipart.MaxUploadSizeExceededException.class)
	@org.springframework.web.bind.annotation.ResponseBody
	public String handleMaxSizeException(org.springframework.web.multipart.MaxUploadSizeExceededException exc) {
		return "<h1>Upload Error</h1><p>The image you selected is too large! Please select an image smaller than 50MB.</p><a href='/admin/services'>Go Back</a>";
	}

	@ExceptionHandler(value = org.springframework.web.multipart.MultipartException.class)
	@org.springframework.web.bind.annotation.ResponseBody
	public String handleMultipartException(org.springframework.web.multipart.MultipartException exc) {
		return "<h1>Upload Error</h1><p>Failed to process the uploaded image. Please try a different file.</p><a href='/admin/services'>Go Back</a>";
	}

	@ExceptionHandler(value=Exception.class) 
	public String handler(Exception e) {
		System.out.println("Exception Handled....!!!!");
		e.printStackTrace();
		return "exception"; 
	}

}