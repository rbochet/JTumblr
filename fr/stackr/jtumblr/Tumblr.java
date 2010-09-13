package fr.stackr.jtumblr;

/**
 * Abstract class
 * 
 */

public abstract class Tumblr {
	/**
	 * Encodaging : UTF-8
	 */
	protected static final String ENCODING = "UTF-8";

	/**
	 * API home
	 */
	protected static final String TUMBLR_WRITE_URL = "http://www.tumblr.com/api/write";

	protected static final String POST_REGULAR_TEXT = "regular";
	protected static final String POST_QUOTE = "quote";
	protected static final String POST_LINK = "link";
	protected static final String POST_CONVERSATION = "conversation";
	protected static final String POST_VIDEO = "video";
	protected static final String POST_AUDIO = "audio";

}