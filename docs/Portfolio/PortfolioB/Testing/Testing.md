# Testing

## Continuous Integration
For our project, we decided to use CircleCI as our continuous integration platform to iteratively and automatically implement development and release testing thus allowing us to evalutate each potential commit/push/release against previous releases before deployment; greatly increasing efficiency and the ability to push releases much faster. It also provided the security that new features won't break any previous features. CircleCI also lets the developer define specific workflows for tests and dependences between separate tests and certain criteria that must be satisfied before a release can be deployed. The overall workflow can be seen in the diagram below. It also allowed us to automatically run instrumented tests through Google's Firebase Test Lab and generate test reports that were also automatically stored in our Firebase Storage.

<img src="CI_Workflow.png" align="right" alt="drawing" width="300"/>

The automatically generated report coverage was also uploaded to Codacy, a tool that evaluates and reviews code against 'good' coding standards such as camel case naming conventions, DRY principles, code structure, simplicity and readability. Using this tool allowed us to keep a consistent code base and help remove ambiguity between each developers code whilst removing possibilities of code that was prone to errors later down the line/that might impact new implementations.

## Development Testing
Our development testing strategy follows the principle that testing is used to show the presence of bugs and not the absence of bugs. We adhered to this principal by following the protocol of first writing the tests before writing the code, making sure these tests revolve around identifying if the code is conforming to the specification. Using continuous integration we could re-run, refactor and optimize the tests automatically. The correctness of these tests will be ensured by using synthetic data that is chosen using methods such as equivalence partitioning to provide complete coverage of test cases without
needing to try all possibilities. All automated tests were implemented using JUnit.

Core components of the application that needed to be tested include the ability for the users to find, create, delete, update and register for events that are organised by the university Health and Wellbeing Department and to have different levels of user privileges and that users who had the administrator privilege had the ability to create, delete, update and view the data pertaining these events. These were identified through the client communicating their vision of a ‘minimum viable product’.
