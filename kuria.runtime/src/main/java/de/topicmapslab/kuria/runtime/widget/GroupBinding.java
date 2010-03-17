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
public class GroupBinding extends PropertyBinding {
}
