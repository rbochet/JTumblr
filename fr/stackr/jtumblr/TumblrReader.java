package fr.stackr.jtumblr;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 *
 */

public class TumblrReader extends Tumblr {

	/**
	 * The tumblog URL
	 */
	private String tumblogUrl = "";

	/**
	 * This constructor should be used for reading posts from the tumblog URL.
	 * 
	 * @param tumblogUrl
	 *            tumblelog URL
	 */

	public TumblrReader(String tumblogUrl) throws Exception {
		this.tumblogUrl = tumblogUrl;

	}

	/**
	 * This method gets the posts from the tumblelog and returns an array of
	 * Posts
	 */
	public Post[] readPosts() throws Exception {

		// setup parser
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// get posts at URL
		Document document = builder.parse(tumblogUrl + "/api/read");
		NodeList postNodes = document.getElementsByTagName("post");

		// create a post array to store posts
		Post[] posts = new Post[postNodes.getLength()];

		// get each post
		for (int i = 0; i < postNodes.getLength(); i++) {

			// parse post nodes to get contents
			Node node = postNodes.item(i);
			NodeList nodes = node.getChildNodes();

			posts[i] = new Post();

			// get post contents
			for (int k = 0; k < nodes.getLength(); k++) {
				Node topNode = nodes.item(k);

				if (topNode.getNodeName().equals("regular-title")) {
					posts[i].setTitle(topNode.getFirstChild().getNodeValue());

				} else if (topNode.getNodeName().equals("regular-body")) {
					// set post body
					posts[i].setBody(topNode.getFirstChild().getNodeValue());

				}

			}
		}

		return posts;

	}

	/**
	 * This method sets the tumblelog URL for reading posts
	 * 
	 * @param tumblogUrl
	 *            tumblelog URL
	 */
	public void setTumblogUrl(String tumblogUrl) {
		this.tumblogUrl = tumblogUrl;
	}

}