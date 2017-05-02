Scenario: A Gedcom file is provided and an xml output is extracted from it.

Given a Gedcom data file
When it is parsed
Then a correct xml output is generated
And is stored in a file in the file-system