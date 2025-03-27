
#!/bin/bash
rm -rf out theburningage.jar manifest.txt

mkdir -p out
javac -cp gson-2.10.1.jar -d out $(find . -name "*.java")
echo "Main-Class: main.Main" > manifest.txt
jar cfm theburningage.jar manifest.txt -C out . -C . gson-2.10.1.jar

echo "Build Complete.."

echo "Running the .jar file.."
java -cp "theburningage.jar:gson-2.10.1.jar" main.Main
