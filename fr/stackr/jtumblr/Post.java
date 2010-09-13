package fr.stackr.jtumblr;

/**

 * 
 */

public class Post {
	/**
	 * Post title
	 */
	private String title;

	/**
	 * Post content
	 */
	private String body;

	/**
	 * Create a post with empty title and body
	 */
	public Post() {
		body = new String();
		title = new String();
	}

	/**
	 * Create a post with title and body
	 * 
	 * @param title
	 *            title of the post
	 * @param body
	 *            body of the post
	 */
	public Post(String title, String body) {
		this.title = title;
		this.body = body;

	}

	/**
	 * This method sets the title of the post
	 * 
	 * @param title
	 *            title of the post
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * This method sets the body of the post
	 * 
	 * @param body
	 *            body of the post
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * This method gets the title of the post
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This method gets the body of the post
	 */
	public String getBody() {
		return body;
	}
}