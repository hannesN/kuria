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
package de.topicmapslab.kuria.swtgenerator.edit.widgets;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

import java.util.Calendar;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;

import de.topicmapslab.kuria.runtime.IPropertyBinding;
import de.topicmapslab.kuria.runtime.widget.IDateBinding;

/**
 * @author Hannes Niederhausen
 * @version 1.0.0
 */
public class DateWidget extends LabeledWidget {

	private DateTime dateTimeWidget;

	public DateWidget(IPropertyBinding propertyBinding) {
	    super(propertyBinding);
    }

	public void createControl(Composite parent) {
	    createLabel(parent);
	    dateTimeWidget = new DateTime(parent, SWT.DROP_DOWN);
	    GridData gd = new GridData(GridData.FILL_HORIZONTAL);
	    gd.horizontalSpan = ((GridLayout)parent.getLayout()).numColumns-1;
	    dateTimeWidget.setLayoutData(gd);
	    dateTimeWidget.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent arg0) {
				if (getModel()==null)
					return;
				try {
	                Calendar cal = getCalender();
	                Date val = (Date) getPropertyBinding().getValue(getModel());
	                if (val == null)
		                notifyStateListener(true);
	                else
		                notifyStateListener(!isEqual(val, cal));
                } catch (Exception e) {
                	throw new RuntimeException(e);
                }
			}
		});
	    createDecoration(dateTimeWidget);
    }

	public void persist() {
		if (!isDirty())
			return;
		try {
	        Calendar cal = getCalender();
	        

	        getPropertyBinding().setValue(getModel(), cal.getTime());
	        notifyStateListener(false);
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
    }

	private Calendar getCalender() {
	    Calendar cal = Calendar.getInstance();
	    cal.set(YEAR, dateTimeWidget.getYear());
	    cal.set(MONTH, dateTimeWidget.getMonth());
	    cal.set(DAY_OF_MONTH, dateTimeWidget.getDay());
	    cal.set(HOUR_OF_DAY, dateTimeWidget.getHours());
	    cal.set(MINUTE, dateTimeWidget.getMinutes());
	    cal.set(SECOND, dateTimeWidget.getSeconds());
	    return cal;
    }

	public void refresh() {
		try {
			if (getModel() != null) {
				Calendar cal = Calendar.getInstance();
				Date val = (Date) getPropertyBinding().getValue(getModel());
				if (val == null)
					val = new Date();

				cal.setTime(val);

				dateTimeWidget.setYear(cal.get(YEAR));
				dateTimeWidget.setMonth(cal.get(MONTH));
				dateTimeWidget.setDay(cal.get(DAY_OF_MONTH));
				dateTimeWidget.setTime(cal.get(HOUR_OF_DAY), cal.get(MINUTE), cal.get(SECOND));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public boolean isEqual(Date date, Calendar cal) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		
		return (cal.get(YEAR)==calender.get(YEAR))
			&& (cal.get(MONTH)==calender.get(MONTH))
			&& (cal.get(DAY_OF_MONTH)==calender.get(DAY_OF_MONTH))
			&& (cal.get(HOUR_OF_DAY)==calender.get(HOUR_OF_DAY))
			&& (cal.get(MINUTE)==calender.get(MINUTE))
			&& (cal.get(SECOND)==calender.get(SECOND));
	}

	public void setEnabled(boolean enabled) {
	    dateTimeWidget.setEnabled(enabled);
    }

	@Override
	public IDateBinding getPropertyBinding() {
	    return (IDateBinding) super.getPropertyBinding();
	}
}