# ReqSystem
my work for Requality project

DOMStructBuilder extracts JDOM2 Document structure from .xhtml file.

Tree and Node classes are used to transform JDOM2 Document into better representation.

Requality and Location classes are used for requirement extraction from Tree. Actual extraction code is written in ReqExtractor class.

InterfaceOps contains operations which this project is supposed to do.

TextOps contains operations for extracting plain text from Locations, headers and sections.

ActualLocation contains text of Locations, going together, and extracted path of Nodes to the ActualLocation place.

ToDo: add ability to remember if section text was extracted, add search of path in the second document Tree

