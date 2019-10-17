package kr.co.itcen.jblog.exception;

public class FileuploadException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public FileuploadException() {
		super("FileUpload Exception");
	}

	public FileuploadException(String message) {
		super(message);
	}
	
}
