package de.unihamburg.zbh.fishoracle_db_api.driver;

public class AdaptorException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public AdaptorException() {
    super();
  }

  public AdaptorException(String message) {
    super(message);
  }

  public AdaptorException(String message, Exception parentException) {
    super(message, parentException);
  }

  public AdaptorException(Exception parentException) {
    super(parentException);
  }

}
