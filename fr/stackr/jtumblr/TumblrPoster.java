package fr.stackr.jtumblr;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * TumblrPoster
 */

public class TumblrPoster extends Tumblr {

	// connection to Tumblr
	private URLConnection connection = null;

	private DataOutputStream printout = null;

	// credentials
	private String username = "";

	private String password = "";

	/**
	 * This constructor sets the username and password for writing posts.
	 * 
	 * @param username
	 *            email used as username
	 * @param password
	 *            password
	 */

	public TumblrPoster(String username, String password) {
		this.username = username;
		this.password = password;
	}

	/**
	 * Get the request status.
	 * 
	 * @return the status.
	 * @throws Exception
	 *             if the status cannot be acquired.
	 */
	private String getStatus() throws Exception {
		BufferedReader input;

		// get the response
		input = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		String status = null;

		status = input.readLine();

		input.close();

		return status;

	}

	/**
	 * Open the connection to tumblr.
	 * 
	 * @param tumblrUrl
	 *            The tumblr URL
	 * @throws Exception
	 *             If the connection fails.
	 */
	private void openConnection(String tumblrUrl) throws Exception {
		URL url;

		// URL of tumblr.com
		url = new URL(tumblrUrl);

		connection = url.openConnection();

		// to get the status of the write operation
		connection.setDoInput(true);

		// to write the post to the URL
		connection.setDoOutput(true);

		// instruction not to use cache but the fresh connection
		connection.setUseCaches(false);

		// encode the contents of the form
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");

	}

	/**
	 * This method writes posts to the Tumblr tumblog.
	 * 
	 * @param type
	 *            presently set to "regular" for text posts
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 * @param specificParams
	 *            array containing parameters for different type of posts
	 */
	private void post(String type, String tags, String date,
			String[] specificParams) throws Exception {

		openConnection(TUMBLR_WRITE_URL);

		printout = new DataOutputStream(connection.getOutputStream());

		StringBuffer content = new StringBuffer();

		content = setInitialParameters(content, type);

		content = setPostSpecificParameters(content, specificParams);

		content = setOptionalParameters(content, tags, date);

		System.out.println("HTTP Request:\n\n" + content.toString());

		printout.writeBytes(content.toString());
		printout.flush();
		printout.close();

		System.out.println("HTTP Response:\n\n" + getStatus());

	}

	/**
	 * This method writes audio posts to the Tumblr tumblog. Since file uploads
	 * are not yet supported, it embeds audio URLs as video posts.
	 * 
	 * @param audioFileURL
	 *            Web-site URL of the audio file
	 * @param caption
	 *            caption for the audio file
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 */
	public void postAudio(String audioFileURL, String caption, String tags,
			String date) throws Exception {
		postVideo(audioFileURL, caption, tags, date);
	}

	/**
	 * This method writes conversation posts to the Tumblr tumblog.
	 * 
	 * @param title
	 *            title of conversation
	 * @param conversation
	 *            conversation to post
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 */
	public void postConversation(String title, String conversation,
			String tags, String date) throws Exception {
		String[] conversationPostParams = new String[4];

		conversationPostParams[0] = "&title=";
		conversationPostParams[1] = title;
		conversationPostParams[2] = "&conversation=";
		conversationPostParams[3] = conversation;

		post(POST_CONVERSATION, tags, date, conversationPostParams);
	}

	/**
	 * This method writes link posts to the Tumblr tumblog. Link description is
	 * not yet supported.
	 * 
	 * @param name
	 *            name of URL
	 * @param url
	 *            link to post
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 */
	public void postLink(String name, String url, String tags, String date)
			throws Exception {
		String[] linkPostParams = new String[4];

		linkPostParams[0] = "&name=";
		linkPostParams[1] = name;
		linkPostParams[2] = "&url=";
		linkPostParams[3] = url;

		post(POST_LINK, tags, date, linkPostParams);
	}

	/**
	 * This method writes quote posts to the Tumblr tumblog.
	 * 
	 * @param quote
	 *            quote to post
	 * @param source
	 *            source of quote
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 */

	public void postQuote(String quote, String source, String tags, String date)
			throws Exception {
		String[] quotePostParams = new String[4];

		quotePostParams[0] = "&quote=";
		quotePostParams[1] = quote;
		quotePostParams[2] = "&source=";
		quotePostParams[3] = source;

		post(POST_QUOTE, tags, date, quotePostParams);
	}

	/**
	 * This method writes text posts to the Tumblr tumblog.
	 * 
	 * @param title
	 *            Title of the post
	 * @param body
	 *            Body of the post
	 * @param tags
	 *            Tags for the post
	 * @param date
	 *            Date of the post
	 */

	public void postText(String title, String body, String tags, String date)
			throws Exception {
		String[] textPostParams = new String[4];

		textPostParams[0] = "&title=";
		textPostParams[1] = title;
		textPostParams[2] = "&body=";
		textPostParams[3] = body;

		post(POST_REGULAR_TEXT, tags, date, textPostParams);
	}

	/**
	 * This method writes video posts to the Tumblr tumblog. File uploads are
	 * not yet supported.
	 * 
	 * @param videoFileURL
	 *            Web-site URL of the video file or HTML code for embedding
	 *            video
	 * @param caption
	 *            caption for the video file
	 * @param tags
	 *            tags for the post
	 * @param date
	 *            date of the post
	 */

	public void postVideo(String videoFileURL, String caption, String tags,
			String date) throws Exception {
		String[] videoPostParams = new String[4];

		videoPostParams[0] = "&embed=";
		videoPostParams[1] = videoFileURL;
		videoPostParams[2] = "&caption=";
		videoPostParams[3] = caption;

		// call post method
		post(POST_VIDEO, tags, date, videoPostParams);
	}

	/**
	 * Set up parameters for a tumblr post. All the characters are encoded if
	 * needed with {@link URLEncoder} A Java librairy for the Tumblr platform
	 * 
	 * @param content
	 *            The content which has to be sent. Should be empty (but not
	 *            null).
	 * @param type
	 *            The type of post
	 * @return The final URL
	 * @throws Exception
	 *             If the encoding fails.
	 */
	private StringBuffer setInitialParameters(StringBuffer content, String type)
			throws Exception {

		content.append("email=");
		content.append(URLEncoder.encode(username, ENCODING));

		content.append("&password=");
		content.append(URLEncoder.encode(password, ENCODING));

		content.append("&type=");
		content.append(URLEncoder.encode(type, ENCODING));

		return content;

	}

	/**
	 * Add the optional parameters.
	 * 
	 * @param content
	 *            The content. Should be initialized with connection and
	 *            mandatory parameters.
	 * @param tags
	 *            The associated tags.
	 * @param date
	 *            The publication date. In a litteral formaat.
	 * @return The decorated string.
	 * @throws Exception
	 *             If encodings fails.
	 */
	private StringBuffer setOptionalParameters(StringBuffer content,
			String tags, String date) throws Exception {
		content.append("&tags=");
		content.append(URLEncoder.encode(tags, ENCODING));

		content.append("&date=");
		content.append(URLEncoder.encode(date, ENCODING));

		return content;

	}

	/**
	 * Add the parameters which depends on the type of post.
	 * 
	 * @param content
	 *            The content. Should be initialized with the connection
	 *            parameters.
	 * @param specificParams
	 *            The parameters to add.
	 * @return A String made with connection and post settings.
	 * @throws Exception
	 *             If the encoding fails.
	 */
	private StringBuffer setPostSpecificParameters(StringBuffer content,
			String[] specificParams) throws Exception {
		content.append(specificParams[0]);
		content.append(URLEncoder.encode(specificParams[1], ENCODING));

		content.append(specificParams[2]);
		content.append(URLEncoder.encode(specificParams[3], ENCODING));

		return content;

	}

}