# Arden Syntax Editor Update Site

## Building the Update Site

I usually built this Update Site with the Eclipse 
PDE (Plugin Development Tools) having xText installed.  
The problem with that approach is that older builds 
of the feature and bundles are not deleted.
Instead, the features and bundles aggregate until 
the repository is very large and takes lots of time 
to update from.  
Therefore, I used the `p2.mirror` Ant task provided
by Eclipse to mirror the `ArdenSyntaxEditor` feature
into another repository named 
`ardensyntax-eclipse-plugin-update-site` which is in 
turn included in the Arden2ByteCode homepage 
repository.

The Eclipse project in this directory includes an
external builder to launch the Ant task described 
above.

Note that the Ant `p2.mirror` task seems not to
support relative pathnames. So I had to use absolute
paths preceded by a `file:` protocol handler.  
You should adjust these paths to suit your system
configuration before building the update site.
