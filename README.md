# ReqSystem
my work for Requality project

DOMStructBuilder extracts JDOM2 Document structure from .xhtml file.

Tree and Node classes are used to transform JDOM2 Document into better representation.

Node has ability to remember, if function sectionTextExtract was used to it.

Requality and Location classes are used for requirement extraction from Tree. Actual extraction code is written in ReqExtractor class.

InterfaceOps contains operations which this project is supposed to do.

TextOps contains operations for extracting plain text from Locations, headers and sections.

ActualLocation class contains text of Locations, going together, and extracted path of Nodes to the ActualLocation place.

TransferManager contains:

	Function findSimilarPath which allows to search similar path in the second document Tree.

	Function extractLowestSectionText, which gets texts of sections, in which we need to transfer ActualLocation.

ToDo: use GoogleDiff for sections to search if transfer can be easily done.