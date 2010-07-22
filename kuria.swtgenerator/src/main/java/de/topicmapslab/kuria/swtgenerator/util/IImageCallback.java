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
package de.topicmapslab.kuria.swtgenerator.util;

import org.eclipse.swt.graphics.Image;

/**
 * Kuria tries to load images from the classpath using the path of the binding.
 * 
 * It may happen that the image is an external image and Kuria cannot find it. Is this the case 
 * the application may register an implementation of this interface and load the image on its own.
 * 
 * @author Hannes Niederhausen
 *
 */
public interface IImageCallback {

	/**
	 * This method is called if loading the image of the given path failed. 
	 * The implementation may use different approaches to load the image.
	 * 
	 * @param path the path to the image
	 * @return the loaded image or <code>null</code>
	 */
	public Image loadImage(String path);
}
