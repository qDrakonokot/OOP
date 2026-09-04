mkdir -p build/classes build/libs build/docs
javac -d build/classes $(find src/main/java -name "*.java")
javadoc -d build/docs -sourcepath src/main/java -subpackages ru.nsu.oop.drakonokot
jar cfe build/libs/HeapSort.jar ru.nsu.oop.drakonokot.Main -C build/classes .
java -jar build/libs/HeapSort.jar

