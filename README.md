# Power Set Generator
Powerset generation program.

#Description
 * Powerset program that outputs the power set of a given input file and writes the result in a given output file.
 * Suppots csv and tsv file formats.

##Dependencies
 * Built using Apache Maven 3.2.5, OpenJDK 1.8.0 and JUnit 4.11

##Building
 * Compiling with tests: mvn package
 * Compile without tests: mvn package -DskipTest
 * Running tests: mvn test
 * Cleaning: mvn clean

##Usage
 * java -cp target/powerset-tool-1.0-SNAPSHOT.jar me.cabernal.PowerSetApp \<input-file> \<output-file> \<file-format>
 * alternative (if bash is available): sh powerset.sh \<input-file> \<output-file> \<file-format>
 * input-file: csv or tsv file representing a set
 * output-file: power set file, each line containing subsets in csv or tsv format
 * file-format: csv or tsc (csv by default)
