/*******************************************************************************
 * Copyright 2010, Topic Maps Lab
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
/**
 * 
 */
package de.topicmapslab.kuria.swtgenerator.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
	
	private static List<IImageCallback> callbackList;
	
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
        
		Image img = null;
		
		InputStream is = path.getClass().getResourceAsStream(newpath);
		
		if (is!=null) {
			img = new Image(Display.getCurrent(), is);
		}

		// no classpath entry - try callbacks
		for (IImageCallback imgCallback : getCallbackList()) {
			img = imgCallback.loadImage(path);
			if (img!=null)
				break;
		}
	    
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
	
    private static List<IImageCallback> getCallbackList() {
    	if (callbackList==null)
    		return Collections.emptyList();
    	return callbackList;
    }
    
    /**
     * @param callbackList the callbackList to set
     */
    public static void addImageCallback(IImageCallback callback) {
	    if (callbackList==null)
	    	callbackList = new ArrayList<IImageCallback>();
	    callbackList.add(callback);
    }
    
    /**
     * @param callbackList the callbackList to set
     */
    public static void removeImageCallback(IImageCallback callback) {
	    if (callbackList!=null) {
	    	callbackList.add(callback);
	    	if (callbackList.size()==0)
	    		callbackList = null;
	    }
    }
}
