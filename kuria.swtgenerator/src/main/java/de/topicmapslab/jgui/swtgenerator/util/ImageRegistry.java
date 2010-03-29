/**
 * 
 */
package de.topicmapslab.jgui.swtgenerator.util;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

/**
 * @author Hannes Niederhausen
 *
 */
public class ImageRegistry {

	private static Map<String, Image> imageMap;
	private static Image defaultImage;
	
	
	public static Image getImage(String path) {
		Image img = getImageMap().get(path);
		
		if (img!=null)
			return img;
		
		img = loadImage(path);
		
		if (img==null)
			return getDefaultImage();
		
		return img;
	}
	
	private static Image getDefaultImage() {
	    if (defaultImage==null) {
	    	InputStream is = getImageMap().getClass().getResourceAsStream("/empty.gif");		
	    	defaultImage = new Image(Display.getCurrent(), is);
	    }
		
		return defaultImage;
    }

	private static Image loadImage(String path) {
		String newpath;
		if (!path.startsWith("/"))
			newpath="/"+path;
		else
			newpath = path;
        	
		InputStream is = path.getClass().getResourceAsStream(newpath);		
		if (is==null)
			return null;
		Image img = new Image(Display.getCurrent(), is);
	    
	    if (img!=null)
	    	putImage(path, img);
	    
	    return img;
    }

	private static void putImage(String path, Image img) {
		if (imageMap==null)
			imageMap = new HashMap<String, Image>();
		
		imageMap.put(path, img);
    }

	private static Map<String, Image> getImageMap() {
		if (imageMap==null)
			return Collections.emptyMap();
		return imageMap;
    }
	
}
