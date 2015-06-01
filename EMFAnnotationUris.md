# EMF Annotation URIs #

This page lists all URIs usable as eAnnotation.
The suffix of the URI is names after the binding it creates.

Sublistelements indicating the key of an annotation detail entry.

# General #

  * http://code.google.com/p/kuria/annotations/text use it on fields

# Table #

  * http://code.google.com/p/kuria/annotations/tableelement
  * http://code.google.com/p/kuria/annotations/column
    * image
    * imageMethod
    * textMethod

# Tree #

  * http://code.google.com/p/kuria/annotations/treenode use it on classes
    * image
    * imageMethod
  * http://code.google.com/p/kuria/annotations/children
    * title
    * image

# Input Mask #

Every annotation supports the following details entry keys:

  * label
  * readOnly
  * optional
  * weight
  * description

The field annotation URIs and additional entry keys are:

  * http://code.google.com/p/kuria/annotations/editable
  * http://code.google.com/p/kuria/annotations/hidden
  * http://code.google.com/p/kuria/annotations/textfield
    * password
    * regexp
    * rows
    * grabVerticalSpace
  * http://code.google.com/p/kuria/annotations/check
  * http://code.google.com/p/kuria/annotations/combo
    * createNew
  * http://code.google.com/p/kuria/annotations/date
    * format
    * showTime
  * http://code.google.com/p/kuria/annotations/directory
  * http://code.google.com/p/kuria/annotations/file
    * fileExtensions (comma separated strings e.g. "`*`.xtm;`*`.ctm","`*`.ltm" )
    * load
  * http://code.google.com/p/kuria/annotations/group
  * http://code.google.com/p/kuria/annotations/list
    * createNew
    * style (String: "List" or "Table" )

