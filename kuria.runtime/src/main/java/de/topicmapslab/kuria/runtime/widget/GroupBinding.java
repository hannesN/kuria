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
package de.topicmapslab.kuria.runtime.widget;

import de.topicmapslab.kuria.runtime.PropertyBinding;

/**
 * A {@link GroupBinding} indicates that the bound property has an own input mask (bound by 
 * another {@link EditableBinding}.
 * <p>This input mask will be embedded in a Group widget which is mostly a widget container with a
 * border and a label at the upper left corner</p>
 * <p>How this group is realized depends on the UI generator. The important thing is that the
 * input mask if the property is shown.</p> 
 *  
 * 
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class GroupBinding extends PropertyBinding implements IGroupBinding {
}
